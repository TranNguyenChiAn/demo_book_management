package com.example.demo_book_management.controller;

import com.example.demo_book_management.database.ResponseObject;
import com.example.demo_book_management.entity.Publisher;
import com.example.demo_book_management.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Publisher")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping("")
    List<Publisher> getAllPublishers(){
        return publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Publisher> foundPublisher= publisherRepository.findById(id);
        if (foundPublisher.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query publisher successfully", foundPublisher)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Cannot find publisher with id = " + id, "")
            );
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertPublisher(@RequestBody Publisher newPublisher){
        List<Publisher> foundPublisher = publisherRepository.findByName(newPublisher.getName().trim());

        if(!foundPublisher.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed", "Publisher name already taken", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert publisher successfully", publisherRepository.save(newPublisher))
            );
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updatePublisher(@RequestBody Publisher newPublisher, @PathVariable Long id) {
        Publisher updatePublisher = publisherRepository.findById(id)
                .map(Publisher -> {
                    Publisher.setName(newPublisher.getName());
                    return publisherRepository.save(Publisher);
                }).orElseGet(() -> {
                    newPublisher.setId(id);
                    return publisherRepository.save(newPublisher);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Updated publisher successfully", publisherRepository.save(newPublisher))
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deletePublisher(@PathVariable Long id){
        boolean exists = publisherRepository.existsById(id);
        if (exists){
            publisherRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete publisher successful", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find publisher to delete", "")
        );
    }
}
