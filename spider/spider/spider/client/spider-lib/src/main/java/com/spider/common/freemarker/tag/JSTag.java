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
public class JSTag implements TemplateDirectiveModel {

    public static final String TEMPLATE_JS_TAG = "<script type=\"text/javascript\" src=\"%s\"%s></script>";

    private static final String TEMPLATE_JS_DIR = "http://static.spider.com/static/spider/";

    private static final String TEMPLATE_JS_NULL = "";

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String finalScript = this.buildJsScriptTag(map);
        Writer output = environment.getOut();
        output.write(finalScript);
    }

    private String buildJsScriptTag(Map map) {
        String srcParam = map.get("src").toString();
        if (StringUtils.hasText(srcParam)) {
            StringBuilder sb = new StringBuilder();
            String[] srcs = srcParam.split(",");
            for (String src : srcs) {
                String script = String.format(TEMPLATE_JS_TAG, TEMPLATE_JS_DIR + srcParam, TEMPLATE_JS_NULL);
                sb.append(script).append("\n");
            }
            return sb.toString();
        } else {
            return TEMPLATE_JS_NULL;
        }
    }

}
