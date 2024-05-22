package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        // Escribir la página HTML con la imagen de fondo
        OutputStream os = resp.getOutputStream();
        os.write("<html><body style=\"background-image: url('/background.jpg'); background-size: cover; height: 100vh; display: flex; justify-content: center; align-items: center;\">".getBytes());
        os.write("</body></html>".getBytes());
    }
}
