package pl.pumbakos.japwebservice.japresources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.pumbakos.japwebservice.japresources.exception.ObjectHasWrongIdException;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUtils<T extends DBModel> {
    private static boolean isColumnUpdatable(Field field) {
        if (field.getAnnotation(Column.class) != null
                && field.getAnnotation(Column.class).nullable()
                && field.getAnnotation(Column.class).insertable()
                && field.getAnnotation(Column.class).updatable()) {
            return true;
        }
        return false;
    }

    /**
     * Updates object under given ID with params from given object in given repository
     *
     * @param repository repository that extends JpaRepository
     * @param object     object were data come from
     * @param id         id of object in repository
     * @return updated object
     * @see JpaRepository
     */
    public T update(JpaRepository<T, Long> repository, T object, Long id) {
        Optional<T> optionalObject = repository.findById(id);

        if (optionalObject.isPresent()) {
            T updatableObject = optionalObject.get();

            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id"))
                        continue;

                    Field updatableObjectField = updatableObject.getClass().getDeclaredField(field.getName());
                    Field objectField = object.getClass().getDeclaredField(field.getName());

                    updatableObjectField.setAccessible(true);
                    objectField.setAccessible(true);

                    if (field.getAnnotation(Basic.class) != null && objectField.get(object) == null || isColumnUpdatable(field)) {
                        continue;
                    }

                    updatableObjectField.set(updatableObject, objectField.get(object));

                    updatableObjectField.setAccessible(false);
                    objectField.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return null;
                }
            }

            return repository.save(updatableObject);
        }
        return null;
    }

    public <S extends DBModel> void checkIfPresent(JpaRepository<S, Long> repository, S object) throws ObjectHasWrongIdException {
        if (object == null)
            throw new NullPointerException("Object is null");

        if (object.getId() == null)
            repository.save(object);

        Optional<S> optionalObject = repository.findById(object.getId());
        if (optionalObject.isEmpty())
            throw new ObjectHasWrongIdException("Object " + object + "has wrong ID\nIt cannot be identified");
    }

    public <S extends DBModel> void checkIfPresents(JpaRepository<S, Long> repository, List<S> objects, Class<S> clazz) throws ObjectHasWrongIdException {
        if (objects == null || objects.isEmpty())
            throw new NullPointerException("List of " + clazz + " is blank or empty");

        for (S object : objects) {
            if (object == null)
                throw new NullPointerException("Object is null");

            if (object.getId() == null)
                repository.save(object);

            Optional<S> optionalObject = repository.findById(object.getId());
            if (optionalObject.isEmpty())
                throw new ObjectHasWrongIdException("Object " + object + " at " + object.getClass().getPackageName() + "has wrong ID\nIt cannot be identified");
        }
    }
}
