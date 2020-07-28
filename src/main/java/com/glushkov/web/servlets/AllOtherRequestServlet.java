package com.glushkov.web.servlets;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;

public class AllOtherRequestServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
             BufferedInputStream bufferedInputStream = new BufferedInputStream(readContent(request.getRequestURI()));) {
//TODO переделать буфер
            byte[] buffer = new byte[1024];
            int count;
            while ((count = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException ioException) {
            throw new RuntimeException("Error while response was writing", ioException);
        }
    }


    InputStream readContent(String uri) {

        String refactorUri = uri.contains("/users") ? uri.replaceAll("/users", "") : uri;

        Path path = Path.of("templates/webapp", refactorUri);

        File file = path.toFile();

        try {
            if (file.exists()) {
                return new FileInputStream(file);
            }
            throw new RuntimeException("File not found");
        } catch (FileNotFoundException fileNotFoundException) {
            throw new RuntimeException("File not found", fileNotFoundException);
        }
    }
}

