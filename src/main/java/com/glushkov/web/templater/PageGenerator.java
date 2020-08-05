package com.glushkov.web.templater;


import freemarker.core.AliasTemplateNumberFormatFactory;
import freemarker.core.TemplateNumberFormatFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class PageGenerator {

    private static final String HTML_DIR = "src/main/resources/templates";

    private static PageGenerator pageGenerator;

    public static PageGenerator instance() throws IOException {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    private final Configuration cfg;

    public String getPage(String filename, Map<String, Object> input) {

        Writer stream = new StringWriter();

        try {
        Template template = cfg.getTemplate(filename);
            template.process(input, stream);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }

        return stream.toString();
    }

    private PageGenerator() throws IOException {

        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDirectoryForTemplateLoading(new File(HTML_DIR));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setLocale(Locale.UK);

        Map<String, TemplateNumberFormatFactory> customNumberFormat = new HashMap<>();
        customNumberFormat.put("salary", new AliasTemplateNumberFormatFactory("0.##"));
        cfg.setCustomNumberFormats(customNumberFormat);
    }
}
