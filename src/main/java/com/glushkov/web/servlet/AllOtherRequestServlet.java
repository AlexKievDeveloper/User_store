package com.glushkov.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

    InputStream readContent(String uri) throws IOException {

        if (uri.equals("/")) {
            uri = "/home.ftl";
        }

        URL fileUrl = ClassLoader.getSystemResource("static" + uri);

        if (fileUrl != null) {
            return fileUrl.openStream();
        }
        throw new RuntimeException("Error while reading file content. Url was null)");
    }
}