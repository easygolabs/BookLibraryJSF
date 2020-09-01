package com.lab1.database.dao;

import com.lab1.database.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() {

        BookEntityMF bookEntityMF = new BookEntityMF();
        EntityManager entityManager = bookEntityMF.getEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM Book e");
        List<Book> book = query.getResultList();
        System.out.println("Данные записались в bookList.");
        entityManager.close();
        bookEntityMF.close();
        return book;
    }

    public void findData(int id) {

        try {
            BookEntityMF bookEntityMF = new BookEntityMF();
            EntityManager entityManager = bookEntityMF.getEntityManager();
            Book book = entityManager.find(Book.class, id);
            System.out.println(book.toString());
            entityManager.close();
            bookEntityMF.close();
            System.out.println("Выполнено.");
        }catch (NullPointerException e){
            throw new RuntimeException("Такого id не существует.");
        }
    }

    public void addData(Book book) {

        try {
            BookEntityMF bookEntityMF =new BookEntityMF();
            EntityManager entityManager = bookEntityMF.getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.getTransaction().commit();
            entityManager.close();
            bookEntityMF.close();
            System.out.println("Объект сохранён.");
        }catch (PersistenceException e){
            throw new RuntimeException("Проверте корректность вводимых данных объекта.");
        }
    }

    public void updateData(Book book, int id) {

        try {
            BookEntityMF bookEntityMF = new BookEntityMF();
            EntityManager entityManager = bookEntityMF.getEntityManager();

            Book findBook = entityManager.find(Book.class, id);
            System.out.println(findBook.toString());

            entityManager.getTransaction().begin();
            findBook.setName(book.getName());
            findBook.setAuthor(book.getAuthor());
            findBook.setYear(book.getYear());
            findBook.setLanguage(book.getLanguage());
            findBook.setGenre(book.getGenre());
            findBook.setPrice(book.getPrice());
            findBook.setDescription(book.getDescription());
            findBook.setLink(book.getLink());
            findBook.setEncryptedImage(book.getEncryptedImage());
            entityManager.getTransaction().commit();

            findBook = entityManager.find(Book.class, id);
            entityManager.close();
            bookEntityMF.close();
            System.out.println(findBook.toString());
            System.out.println("Объект обновлён.");
        } catch (NullPointerException e){
            throw new RuntimeException("Такого id не существует.");
        }
    }

    public void deleteData(int id) {

        try {
            BookEntityMF bookEntityMF = new BookEntityMF();
            EntityManager entityManager = bookEntityMF.getEntityManager();
            Book findBook = entityManager.find(Book.class, id);
            //удаление
            entityManager.getTransaction().begin();
            entityManager.remove(findBook);
            entityManager.getTransaction().commit();
            entityManager.close();
            bookEntityMF.close();
            System.out.println("Объект удалён.");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Такого id не существует.");
        }
    }

    public String findDataById(int id) {

        String bookName;
        try {
            BookEntityMF bookEntityMF = new BookEntityMF();
            EntityManager entityManager = bookEntityMF.getEntityManager();
            Book book = entityManager.find(Book.class, id);
            bookName=book.getName();
            entityManager.close();
            bookEntityMF.close();
            System.out.println("Выполнено.");
        }catch (NullPointerException e){
            throw new RuntimeException("Такого id не существует.");
        }
        return bookName;
    }

    public Book findBookById(int id) {

        Book findBook;
        try {
            BookEntityMF bookEntityMF = new BookEntityMF();
            EntityManager entityManager = bookEntityMF.getEntityManager();
            findBook = entityManager.find(Book.class, id);
            entityManager.close();
            bookEntityMF.close();
            System.out.println("Выполнено.");
        }catch (NullPointerException e){
            throw new RuntimeException("Такой книги не существует.");
        }
        return findBook;
    }
}