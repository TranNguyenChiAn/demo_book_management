package com.example.demo_book_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //auto increment
    private Long id;

    @Column(nullable = false, unique = true,length = 300)
    private String title;

    @Column(nullable = false,length = 300)
    private String author;

    @Column(nullable = false,length = 10)
    private Double price;


    @Column(nullable = false,length = 10)
    private int genre_id;

    @Column(nullable = false,length = 10)
    private int publisher_id;

    @Column(nullable = false,length = 10)
    private int publication_year;

    @Column(nullable = false,length = 10)
    private String summary;

    @Column(nullable = false,length = 10)
    private String description;

    @Column(nullable = false,length = 10)
    private int page_number;

    @Column(nullable = false,length = 10)
    private int quantity;

    //default constructor
    public Book() {}

    //calculated field = transient

    public Book(String title, String author, Double price, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.genre_id = genre_id;
        this.publisher_id = publisher_id;
        this.description = description;
        this.publication_year = publication_year;
        this.summary = summary;
        this.page_number = page_number;
        this.price = price;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id" + id +
                ", title: " + title + '\'' +
                ", author: " + author +
                ", price: " + price +
                ", genre_id: " + genre_id +
                ", publisher_id: " + publisher_id +
                ", publication_year: " + publication_year +
                ", description: " + description +
                ", summary: " + summary +
                ", page_number: " + page_number +
                ", quantity: " + quantity + '\'' +
                '}';
    }
}
