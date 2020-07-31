package com.glushkov.web.servlet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;

public class AllOtherRequestServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
             InputStream inputStream = readContent(request.getRequestURI())) {

            byte[] buffer = new byte[8192];
            int count;
            while ((count = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, count);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    InputStream readContent(String uri) throws FileNotFoundException {

        String validUri = uri.contains("/users") ? uri.replaceAll("/users", "") : uri;

        Path path = Path.of("templates/webapp", validUri);

        File file = path.toFile();


        if (file.exists()) {
            return new FileInputStream(file);
        }
        throw new RuntimeException("File not found");
    }
}

