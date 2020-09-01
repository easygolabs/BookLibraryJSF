package com.lab1.database.controller;

import com.lab1.database.model.Book;
import com.lab1.database.service.BookService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class ManagerController implements Serializable {

    private List<Book> books;
    private List<Book> filteredBooks;
    private List<Book> sortedBooks;
    private BookService bookService = new BookService();

    public List<Book> getSortedBooks(){
        return books;
    }
    public List<Book> getFilteredBooks() {
        return filteredBooks;
    }
    public void setFilteredBooks(List<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }
    public void setSortedBooks(List<Book> sortedBooks) {
        this.sortedBooks = sortedBooks;
    }
    public List<Book> getBooks(){
        books=bookService.getAllData();
        return books;
    }

    public void deleteBook(int id,String name) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        bookService.deleteData(id,name);
        context.getExternalContext().redirect("http://localhost:8080/manager_pages/manager_hello.xhtml");
    }

    public void beforeLoadingPage(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            getBooks();
        }
    }
}