package com.glushkov.web.handler;


import com.glushkov.web.templater.PageGenerator;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ErrorHandler extends ErrorPageErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void generateAcceptableResponse(Request baseRequest, HttpServletRequest request, HttpServletResponse response, int code, String message, String mimeType)
            throws IOException {

        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        logger.error("Error in servlet: {}", servletName, throwable);

        baseRequest.setHandled(true);

        message = "Internal server error. Please try again later. We are working to fix the problem. Sorry for the inconvenience.";

        Map<String, Object> input = new HashMap<>();
        input.put("code", code);
        input.put("message", message);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println(PageGenerator.instance().getPage("error.ftl", input));
    }
}
