package com.example.demo_book_management.controller;

import com.example.demo_book_management.database.ResponseObject;
import com.example.demo_book_management.entity.Admin;
import com.example.demo_book_management.entity.Genre;
import com.example.demo_book_management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Admins")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("show-all")
    List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Admin> foundAdmin= adminRepository.findById(id);
        if (foundAdmin.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query admin successfully", foundAdmin)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Cannot find admin with id = " + id, "")
            );
        }
    }

    @PostMapping("/create-admin")
    ResponseEntity<ResponseObject> insertAdmin(@RequestBody Admin newAdmin){
        List<Admin> foundAdmin = adminRepository.findByName(newAdmin.getName().trim());

        if(!foundAdmin.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed", "Admin name already taken", "")
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert admin successfully", adminRepository.save(newAdmin))
            );
        }
    }

    @PutMapping("/edit-admin/{id}")
    ResponseEntity<ResponseObject> updateAdmin(@RequestBody Admin newAdmin, @PathVariable Long id) {
        Admin updateAdmin = adminRepository.findById(id)
                .map(Admin -> {
                    Admin.setName(newAdmin.getName());
                    Admin.setEmail(newAdmin.getEmail());
                    Admin.setPassword(newAdmin.getPassword());
                    return adminRepository.save(Admin);
                }).orElseGet(() -> {
                    newAdmin.setId(id);
                    return adminRepository.save(newAdmin);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Updated admin successfully", adminRepository.save(newAdmin))
        );
    }

    @DeleteMapping("/delete-admin/{id}")
    ResponseEntity<ResponseObject> deleteAdmin(@PathVariable Long id){
        boolean exists = adminRepository.existsById(id);
        if (exists){
            adminRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete admin successful", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot admin genre to delete", "")
        );
    }

}
