package ru.velkomfood.reports.mrp.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class PageGenerator {

    private static final String HTML_DIR = "public_html";
    private static PageGenerator pageGenerator;
    private Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_20);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.getDefault());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

//    public PageGenerator configure() {
//
//        TemplateLoader loader1 = new ClassTemplateLoader(getClass(), "./templates");
//        MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] {loader1});
//        cfg.setTemplateLoader(mtl);
//
//        return this;
//    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
            template.setOutputEncoding("UTF-8");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}
