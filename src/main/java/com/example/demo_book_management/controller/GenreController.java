package com.example.demo_book_management.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo_book_management.database.ResponseObject;
import com.example.demo_book_management.entity.Genre;
import com.example.demo_book_management.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Genre")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("show-all")
    List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Genre> foundGenre= genreRepository.findById(id);
        if (foundGenre.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query genre successfully", foundGenre)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Cannot find genre with id = " + id, "")
            );
        }
    }

    @PostMapping("/create-genre")
    ResponseEntity<ResponseObject> insertGenre(@RequestBody Genre newGenre){
        List<Genre> foundGenre = genreRepository.findByName(newGenre.getName().trim());

        if(!foundGenre.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed", "Genre name already taken", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert genre successfully", genreRepository.save(newGenre))
            );
        }
    }

    @PutMapping("/edit-genre/{id}")
    ResponseEntity<ResponseObject> updateGenre(@RequestBody Genre newGenre, @PathVariable Long id) {
        Genre updateGenre = genreRepository.findById(id)
                .map(Genre -> {
                    Genre.setName(newGenre.getName());
                    return genreRepository.save(Genre);
                }).orElseGet(() -> {
                    newGenre.setId(id);
                    return genreRepository.save(newGenre);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Updated genre successfully", genreRepository.save(newGenre))
        );
    }

    @DeleteMapping("/delete-genre/{id}")
    ResponseEntity<ResponseObject> deleteGenre(@PathVariable Long id){
        boolean exists = genreRepository.existsById(id);
        if (exists){
            genreRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete genre successful", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find genre to delete", "")
        );
    }
}
