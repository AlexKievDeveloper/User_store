package com.glushkov.web.templater;

import com.glushkov.template.Configuration;
import com.glushkov.template.Template;

import java.util.Map;

public class PageGenerator {

    private static PageGenerator pageGenerator;

    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> pageVariables) {

        Template template = cfg.getTemplate(filename);
        return template.process(pageVariables);
    }

    private PageGenerator() {
        cfg = new Configuration("/templates");
    }
}