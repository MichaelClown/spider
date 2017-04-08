package com.spider.common.api.util;

import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * xml工具类
 * Created by jian.Michael on 2017/3/18.
 */
public class XmlUtil {

    public static <T> T fromXml(Class targetClass, Document document) {
        try {
            Unmarshaller e = JAXBContext.newInstance(targetClass).createUnmarshaller();
            JAXBElement<T> element = e.unmarshal(document, targetClass);
            return element.getValue();
        } catch (JAXBException e1) {
            e1.printStackTrace();
            return null;
        }
    }

}
