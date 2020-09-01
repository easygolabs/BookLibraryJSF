package com.lab1.database.model;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(generator = "increment")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private int year;
    @Column(name = "language")
    private String language;
    @Column(name="genre")
    private String genre;
    @Column(name="price")
    private double price;
    @Column(name="description")
    private String description;
    @Column(name="link")
    private String link;
    @Column(name="encryptedImage")
    private String encryptedImage;

    public Book() {
    }

    public Book(String name, String author, int year, String language, String genre, double price, String description, String link, String encryptedImage) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.language = language;
        this.genre = genre;
        this.price = price;
        this.description=description;
        this.link = link;
        this.encryptedImage = encryptedImage;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String downloadLink) {
        this.link = downloadLink;
    }
    public String getEncryptedImage() {
        return encryptedImage;
    }
    public void setEncryptedImage(String encryptedImage) {
        this.encryptedImage = encryptedImage;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+" ["+id+" "+name+" "+author+" "+year+" "+language+" "+genre+" "+price+" "+description+"]";
    }
}