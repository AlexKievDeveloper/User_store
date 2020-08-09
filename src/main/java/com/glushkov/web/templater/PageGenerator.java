package com.glushkov.web.templater;


import freemarker.core.AliasTemplateNumberFormatFactory;
import freemarker.core.TemplateNumberFormatFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class PageGenerator {

    private static PageGenerator pageGenerator;

    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> dataInMap) {

        Writer stream = new StringWriter();

        try {
            Template template = cfg.getTemplate(filename);
            template.process(dataInMap, stream);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException("Error while getting template", e);
        }

        return stream.toString();
    }

    private PageGenerator() {

        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates");
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
