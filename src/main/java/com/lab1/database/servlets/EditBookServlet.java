package com.lab1.database.servlets;

import com.lab1.database.model.Book;
import com.lab1.database.model.Format;
import com.lab1.database.service.BookService;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@WebServlet("/manager_pages/EditBookServlet")
@MultipartConfig(maxRequestSize = 209715200)
public class EditBookServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Book book;
        String uploadPath = "d:/uploads/";
        BookService bookService = new BookService();
        AddBookServlet addBookServlet = new AddBookServlet();

        //Получаем данные с форм редактированя книжки
        int id = Integer.parseInt(request.getParameter("id"));
        String editBookName = request.getParameter("editBookName");
        String editBookAuthor = request.getParameter("editBookAuthor");
        int editBookYear = Integer.parseInt(request.getParameter("editBookYear"));
        String editBookLanguage = request.getParameter("editBookLanguage");
        String editBookGenre = request.getParameter("editBookGenre");
        double editBookPrice = Double.parseDouble(request.getParameter("editBookPrice"));
        String editBookDescription = request.getParameter("editBookDescription");
        Part partImage = request.getPart("imageUpload");

        book = bookService.findBookById(id); //Получаем книгу по id

        if(book == null) { //Проверяем есть ли книга по такому id
            response.sendRedirect(request.getContextPath() + "/manager_pages/editBookError.xhtml");
        } else {
            //Обновляем книгу в БД
            Book editBook = new Book(editBookName, editBookAuthor, editBookYear,
                    editBookLanguage, editBookGenre, editBookPrice,
                    editBookDescription, uploadPath + editBookName, addBookServlet.encodeImage(partImage));
            editBook(editBook,id);
            response.sendRedirect("http://localhost:8080/manager_pages/manager_hello.xhtml");
        }
    }

    private void editBook(Book editBook, int id){

        Book book;
        String oldLink;
        BookService bookService=new BookService();

        book=bookService.findBookById(id);
        oldLink=book.getLink();

        bookService.updateData(editBook,id);

        for(Format format : Format.values()) {
            if (new File(oldLink + format.getFormat()).renameTo(new File(editBook.getLink() + format.getFormat()))) {
                System.out.println("Файл "+oldLink+ format.getFormat()+" переименован в "+editBook.getLink() + format.getFormat());
            }else {
                System.out.println("Переименовать не получилось...");
            }
        }
    }
}