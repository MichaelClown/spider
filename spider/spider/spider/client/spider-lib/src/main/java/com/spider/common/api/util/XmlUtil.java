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

//    public static String stringValue(Document document) {
//        try {
//            Document var1 = document;
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer transformer = factory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//            DOMSource domSource = new DOMSource(var1);
//            StringWriter stringWriter = new StringWriter();
//            StreamResult streamResult = new StreamResult(stringWriter);
//            transformer.transform(domSource, streamResult);
//            return stringWriter.toString();
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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
