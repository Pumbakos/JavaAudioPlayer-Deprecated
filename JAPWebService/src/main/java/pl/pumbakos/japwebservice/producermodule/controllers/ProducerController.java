package pl.pumbakos.japwebservice.producermodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pumbakos.japwebservice.producermodule.models.Producer;
import pl.pumbakos.japwebservice.producermodule.services.ProducerService;

import javax.validation.Valid;
import java.util.List;

import static pl.pumbakos.japwebservice.japresources.EndPoint.*;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.ID;

@RestController
@RequestMapping(path = PRODUCER)
public class ProducerController {
    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }

    @GetMapping(path = ALL,
            produces = "application/json")
    public List<Producer> getAll(){
        return service.getAll();
    }

    @GetMapping(path = ID,
            consumes = "application/json", produces = "application/json")
    public Producer get(@Valid @PathVariable(name = "id") Long id){
        return service.get(id);
    }

    @PostMapping(
            consumes = "application/json")
    public Producer save(@Valid @RequestBody Producer producer){
        return service.save(producer);
    }

    @PutMapping(path = ID,
            consumes = "application/json", produces = "application/json")
    public Producer update(@RequestBody Producer producer, @PathVariable(name = "id") Long id){
        return service.update(producer, id);
    }

    @DeleteMapping(path = ID,
            produces = "application/json")
    public Producer delete(@PathVariable(name = "id") Long id){
        return service.delete(id);
    }
}
