package pl.pumbakos.japwebservice.authormodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pumbakos.japwebservice.authormodule.models.Author;
import pl.pumbakos.japwebservice.authormodule.services.AuthorService;

import javax.validation.Valid;
import java.util.List;

import static pl.pumbakos.japwebservice.japresources.EndPoint.ALL;
import static pl.pumbakos.japwebservice.japresources.EndPoint.AUTHOR;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.ID;

@RestController
@RequestMapping(AUTHOR)
public class AuthorController {
    private final AuthorService service;

    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping(path = ALL, produces = "application/json")
    public List<Author> getAll(){
        return service.getAll();
    }

    @GetMapping(path = ID, produces = "application/json")
    public Author get(@PathVariable(name = "id") Long id){
        return service.get(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Author save(@Valid @RequestBody Author author){
        return service.save(author);
    }

    @PutMapping(path = ID, consumes = "application/json", produces = "application/json")
    public Author update(@Valid @RequestBody Author author, @PathVariable(name = "id") Long id){
        return service.update(author, id);
    }

    @DeleteMapping(path = ID,
            produces = "application/json")
    public Author delete(@PathVariable(name = "id") Long id){
        return service.delete(id);
    }
}
