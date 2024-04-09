package com.example.demo_book_management.controller;

import com.example.demo_book_management.database.ResponseObject;
import com.example.demo_book_management.entity.Book;
import com.example.demo_book_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("show-all")
    List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
         Optional<Book> foundBook = bookRepository.findById(id);
         if (foundBook.isPresent()){
             return ResponseEntity.status(HttpStatus.OK).body(
                     new ResponseObject("ok", "Query product successfully", foundBook)
             );
         }else{
             return ResponseEntity.status(HttpStatus.OK).body(
                     new ResponseObject("false", "Cannot find book with id = " + id, "")
             );
         }
    }

    @PostMapping("/create-book")
    ResponseEntity<ResponseObject> insertBook(@RequestBody Book newBook){
        List<Book> foundBook = bookRepository.findByTitle(newBook.getTitle().trim());

        if(!foundBook.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed", "Title name already taken", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert successfully", bookRepository.save(newBook))
            );
        }
    }

    @PutMapping("/edit-book/{id}")
    ResponseEntity<ResponseObject> updateBook(@RequestBody Book newBook, @PathVariable Long id) {
        Book updateBook = bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    book.setQuantity(newBook.getQuantity());
                    book.setGenre_id(newBook.getGenre_id());
                    book.setPublisher_id(newBook.getPublisher_id());
                    book.setPublication_year(newBook.getPublication_year());
                    book.setDescription(newBook.getDescription());
                    book.setSummary(newBook.getSummary());
                    book.setPrice(newBook.getPrice());
                    book.setPage_number(newBook.getPage_number());
                    return bookRepository.save(book);
                }).orElseGet(() -> {
                    newBook.setId(id);
                    return bookRepository.save(newBook);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Updated book successfully", bookRepository.save(newBook))
        );
    }

    @DeleteMapping("/delete-book/{id}")
    ResponseEntity<ResponseObject> deleteBook(@PathVariable Long id){
        boolean exists = bookRepository.existsById(id);
        if (exists){
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete book susscessful", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          new ResponseObject("failed", "Cannot find product to delete", "")
        );
    }
}
