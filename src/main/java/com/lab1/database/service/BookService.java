package com.lab1.database.service;

import com.lab1.database.dao.BookDAO;
import com.lab1.database.model.Book;
import com.lab1.database.model.Format;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class BookService {

    private BookDAO bookDAO =new BookDAO();

    public List<Book> getAllData() {
        return bookDAO.getAllBooks();
    }

    public void findData(int id) {
        bookDAO.findData(id);
    }

    public void addData(Book book) {
        bookDAO.addData(book);
    }

    public void updateData(Book book, int id) {
        bookDAO.updateData(book,id);
    }

    public void deleteData(int id,String name) {

        String pathname="d:/uploads/";
        bookDAO.deleteData(id);

        for(Format format : Format.values()) {
            if (new File(pathname+name + format.getFormat()).delete()) {
                System.out.println("Все файлы с именем "+name+" удалены");
            }
        }
    }

    public String findDataById(int id) {
        return bookDAO.findDataById(id);
    }

    public Book findBookById(int id) {
        return bookDAO.findBookById(id);
    }

    public boolean bookIsExist(String bookName){
        for (Book book:getAllData()){
            if (bookName.equals(book.getName())){
                return true;
            }
        }
        return false;
    }
}
