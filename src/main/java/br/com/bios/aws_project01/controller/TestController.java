package br.com.bios.aws_project01.controller;

import br.com.bios.aws_project01.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private DogRepository dogRepository;

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/dog/{name}")
    public ResponseEntity<?> dogTest(@PathVariable String name){
        LOG.info("Test controller - name: {}", name);

        return ResponseEntity.ok("Name: " + name);
    }

    @GetMapping("/dog")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(dogRepository.list());
    }

    @PostMapping("/dog")
    public ResponseEntity<?> insert(@RequestBody String name){
        dogRepository.insert(name);
        return ResponseEntity.ok(name + " inserted");
    }

    @DeleteMapping("/dog")
    public ResponseEntity<?> delete(@RequestBody String name){
        dogRepository.remove(name);
        return ResponseEntity.ok(name + " removed");
    }
}
