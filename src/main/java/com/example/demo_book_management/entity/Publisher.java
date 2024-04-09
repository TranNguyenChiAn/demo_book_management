package com.example.demo_book_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false, unique = true, length = 300)
    public String name;

    //constructor
    public Publisher(){}


    public Publisher(String name){
        this.name = name;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id" + id +
                ", name: " + name +
                '}';
    }
}
