package com.spider;

import com.spider.common.freemarker.tag.CSSTag;
import com.spider.common.freemarker.tag.JSTag;
import com.spider.common.inteceptor.SpiderLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jian.Michael on 2017/4/22.
 */
public abstract class BaseFacadeWebConfig extends WebMvcConfigurerAdapter{

    private static final String TAG_JS = "js";

    private static final String TAG_CSS = "css";

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setContentType("text/html;charset=utf-8");
        freeMarkerViewResolver.setPrefix("/WEB-INF/templates/");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
        freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
        freeMarkerViewResolver.setExposeRequestAttributes(true);
        return freeMarkerViewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/");
        Properties settings = new Properties();
        settings.setProperty("default_encoding", "UTF-8");
        settings.setProperty("url_escaping_charset", "UTF-8");
        settings.setProperty("number_format", "0.##");
        freeMarkerConfigurer.setFreemarkerSettings(settings);
        this.addFreemarkerVariable(freeMarkerConfigurer);
        return freeMarkerConfigurer;
    }

    private void addFreemarkerVariable(FreeMarkerConfigurer freeMarkerConfigurer) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(TAG_JS, new JSTag());
        variables.put(TAG_CSS, new CSSTag());
        freeMarkerConfigurer.setFreemarkerVariables(variables);
    }

    @Bean
    public SpiderLoginInterceptor spiderLoginInterceptor() {
        return new SpiderLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(spiderLoginInterceptor()).excludePathPatterns("/account/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index");
    }
}
