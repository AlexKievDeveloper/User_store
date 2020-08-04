package com.glushkov.web.handler;


import com.glushkov.web.templater.PageGenerator;
import freemarker.template.TemplateException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;


public class ErrorHandler extends ErrorPageErrorHandler {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void generateAcceptableResponse(Request baseRequest, HttpServletRequest request, HttpServletResponse response, int code, String message, String mimeType)
            throws IOException {

        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        logger.error("Error in servlet: {}", servletName, throwable);

        baseRequest.setHandled(true);

        message = "Internal server error. Please try again later. We are working to fix the problem. Sorry for the inconvenience.";

        if (throwable.getCause().getClass() == NumberFormatException.class) {
            message = "Not valid salary format: ' " + request.getParameter("salary") +
                    " '. Please use point as decimal separator: ' . '. Example: ' 1000.10 '. " +
                    "Go back to the previous page and try again.";
        }

        if (throwable.getCause().getClass() == DateTimeParseException.class) {
            message = "Not valid date of birth format: ' " + request.getParameter("dateOfBirth") +
                    " '. Please enter your date of birth use separator: ' - '. Example: ' 1993-08-24 '. " +
                    "Go back to the previous page and try again.";
        }

        PageGenerator pageGenerator = PageGenerator.instance();

        Map<String, Object> input = new HashMap<>();
        input.put("code", code);
        input.put("message", message);

        String page = null;
        try {
            page = pageGenerator.getPage("error.ftl", input);
        } catch (TemplateException e) {
            logger.error("Error while getting template for error.ftl", e);
        }

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().println(page);

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
