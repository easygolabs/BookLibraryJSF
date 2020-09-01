package com.lab1.database.servlets;

import com.lab1.database.model.Book;
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

@WebServlet("/manager_pages/AddBookServlet")
@MultipartConfig(maxRequestSize = 209715200)
public class AddBookServlet extends HttpServlet {

	private String newBookName;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String fileName, nameWithoutFormat;
		BookService bookService =new BookService();
		String uploadPath = "d:/uploads/";

		//Получаем данные с форм добавления книжки
		String newBookAuthor = request.getParameter("newBookAuthor");
		int newBookYear = Integer.parseInt(request.getParameter("newBookYear"));
		String newBookLanguage = request.getParameter("newBookLanguage");
		String newBookGenre = request.getParameter("newBookGenre");
		double newBookPrice = Double.parseDouble(request.getParameter("newBookPrice"));
		String newBookDescription = request.getParameter("newBookDescription");
        Part partFile = request.getPart("fileUpload");
        Part partImage = request.getPart("imageUpload");

		//Создаём папку, если она не создана
        File uploadDirectory = new File(uploadPath);
        if (!uploadDirectory.exists()) {
            if(uploadDirectory.mkdirs()) {
                System.out.println("upload directory have been created, upload path: " + uploadPath);
            }else {
                System.out.println("upload directory haven`t been created");
            }
        }

        //Добавляем файл в папку
        fileName = extractFileName(partFile);
        try {
            partFile.write(uploadPath + fileName);
        } catch (IOException ex) {
            System.out.println("Это не файл");
        }

        //Убираем формат с названия файла
        nameWithoutFormat = newBookName.substring(0, newBookName.indexOf("."));

        //Добавляем книгу в БД
        Book newBook = new Book(nameWithoutFormat, newBookAuthor, newBookYear,
                newBookLanguage, newBookGenre, newBookPrice,
                newBookDescription,uploadPath+nameWithoutFormat, encodeImage(partImage));
        bookService.addData(newBook);
        response.sendRedirect("http://localhost:8080/manager_pages/manager_hello.xhtml");
	}

	//Извлекаем имя файлов
	private String extractFileName(Part part) {

		String fileName = "";
		String contentDisposition = part.getHeader("content-disposition");
		String[] items = contentDisposition.split(";");
		for (String item : items) {
			if (item.trim().startsWith("filename")) {
				fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
				newBookName=fileName;
			}
		}
		return fileName;
	}

    private BufferedImage resizeImage(BufferedImage bufferedImage, int newWidth, int newHeight) {

        int resizedWidth = bufferedImage.getWidth();
        int resizedHeight = bufferedImage.getHeight();
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, bufferedImage.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(bufferedImage, 0, 0, newWidth, newHeight, 0, 0, resizedWidth, resizedHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    String encodeImage(Part partImage) throws IOException {

        //Кодируем image и переопределяем в удобный нам размер
        InputStream inputStream =partImage.getInputStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        BufferedImage resizedImg = resizeImage(bufferedImage, 180, 280);
        ByteArrayOutputStream imageByteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImg, "jpg", imageByteArrayOutputStream);
        return Base64.getEncoder().encodeToString(imageByteArrayOutputStream.toByteArray());
    }
}