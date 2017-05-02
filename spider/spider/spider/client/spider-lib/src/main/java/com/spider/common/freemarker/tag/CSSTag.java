package com.spider.common.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by jian.Michael on 2017/4/22.
 */
public class CSSTag implements TemplateDirectiveModel {

    public static final String TEMPLATE_CSS_TAG = "<link type=\"text/css\" rel=\"stylesheet\" href=\"%s\"%s/>";

    private static final String TEMPLATE_CSS_DIR = "http://static.spider.com/static/spider/";

    private static final String TEMPLATE_CSS_NULL = "";

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String finalScript = this.buildCssTag(map);
        Writer output = environment.getOut();
        output.write(finalScript);
    }

    private String buildCssTag(Map map) {
        String srcParam = map.get("href").toString();
        if (StringUtils.hasText(srcParam)) {
            StringBuilder sb = new StringBuilder();
            String[] srcs = srcParam.split(",");
            for (String src : srcs) {
                String script = String.format(TEMPLATE_CSS_TAG, TEMPLATE_CSS_DIR + srcParam, TEMPLATE_CSS_NULL);
                sb.append(script).append("\n");
            }
            return sb.toString();
        } else {
            return TEMPLATE_CSS_NULL;
        }
    }

}
