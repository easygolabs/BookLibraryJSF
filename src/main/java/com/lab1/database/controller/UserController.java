package com.lab1.database.controller;

import com.lab1.database.model.Book;
import com.lab1.database.model.Format;
import com.lab1.database.service.BookService;
import com.lab1.database.model.User;
import com.lab1.database.service.UserService;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

@ManagedBean
public class UserController {

    private double y_e;
    private int bookId;
    private UserService userService=new UserService();
    private BookService bookService=new BookService();
    private FacesContext context = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
    private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private String userName=request.getRemoteUser(); // Получаем имя текущего пользователя
    private User user=userService.findUserByName(userName); //Получаем пользователя по имени
    private String filePath="d:/uploads/";

    public double getY_e() {
        return y_e;
    }
    public void setY_e(double y_e) {
        this.y_e = y_e;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void refill(){

        User user = userService.findUserByName(userName);
        user.setY_e(user.getY_e()+getY_e());
        userService.updateData(user,user.getUsername());
    }

    public double getBalance(){

        AdminController adminController = new AdminController();
        String userName = adminController.getCurrentUserName();
        return userService.findUserByName(userName).getY_e();
    }

    public void checkForDownloadWithValue(String to,String value) throws IOException {

        Book book = bookService.findBookById(getBookId()); //Получаем книгу по id

        if(book == null) { //Проверяем есть ли книга по такому id
            response.sendRedirect(request.getContextPath() + "/user_pages/searchBookError.xhtml");
        } else {
            String PDFFile = book.getLink() + Format.PDF.getFormat();

            if (user.getY_e() - book.getPrice() < 0) {//Проверяем есть ли деньги у пользователя
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You need to top up your balance.");
            } else {

                user.setY_e(user.getY_e() - book.getPrice());
                userService.updateData(user, user.getUsername());

                File file = new File(PDFFile);

                if (!file.exists()) {

                    user.setY_e(user.getY_e() + book.getPrice());
                    userService.updateData(user, user.getUsername());
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    if (to.equals("convertPDFtoTXT")){
                        convertPDFtoTXT(file);
                    } else if(to.equals("convertPDFtoDOC")){
                        convertPDFtoDOC(file);
                    }else {
                        downloadWithValue(file,value);
                    }
                }
            }
        }
    }

    private void convertPDFtoTXT(File file) throws IOException {

        File txtFile = new File( filePath+file.getName().substring(0,file.getName().indexOf(".")) + Format.TXT.getFormat());

        if (!searchConvertedFile(filePath, txtFile)) {

            PDDocument newPdDocument = PDDocument.load(file);

            if (!newPdDocument.isEncrypted()) {

                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                String text = pdfTextStripper.getText(newPdDocument);
                FileWriter fileWriter = new FileWriter(txtFile);
                fileWriter.write(text);
                fileWriter.close();
            }
            newPdDocument.close();
        }
        downloadWithValue(txtFile,"attachment");
    }

    private void convertPDFtoDOC(File file) throws IOException {

        String link;

        link=filePath+file.getName().substring(0,file.getName().indexOf(".")) + Format.DOC.getFormat();

        File docFile = new File(link);

        if (!searchConvertedFile(filePath, docFile)) {
            PdfDocument docDocument = new PdfDocument();
            docDocument.loadFromFile(filePath+file.getName());
            docDocument.saveToFile(link, FileFormat.DOC);
            docDocument.close();
        }
        downloadWithValue(docFile,"attachment");
    }

    private void downloadWithValue(File file, String value) throws IOException {

        int readingData;
        final int myAppBuffSize = 209715200;//200 MB
        byte[] creatingBuffer;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        response.reset();
        response.setContentType("application/pdf");
        response.setContentLength((int) file.length());
        response.setHeader("Content-disposition", value+"; filename=" + file.getName());

        try{
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file), myAppBuffSize);
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream(), myAppBuffSize);
            creatingBuffer = new byte[myAppBuffSize];

            while ((readingData = bufferedInputStream.read(creatingBuffer)) > 0) {
                bufferedOutputStream.write(creatingBuffer, 0, readingData);
            }

            bufferedOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Objects.requireNonNull(bufferedInputStream).close();
            Objects.requireNonNull(bufferedOutputStream).close();
        }
        context.responseComplete();
    }

    private boolean searchConvertedFile(String filePath, File file){
        for (File searchConvertTXTFile : Objects.requireNonNull(new File(filePath).listFiles())){
            if (searchConvertTXTFile.equals(file)){
                return true;
            }
        }
        return false;
    }

}