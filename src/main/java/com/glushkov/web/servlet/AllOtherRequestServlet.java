package com.glushkov.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    InputStream readContent(String uri) {

        if (uri.equals("/")) {
            uri = "/home.ftl";
        }

        return getClass().getClassLoader().getResourceAsStream("static" + uri);
    }
}