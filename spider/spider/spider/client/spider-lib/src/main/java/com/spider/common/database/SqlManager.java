package com.spider.common.database;

import com.spider.common.api.util.XmlUtil;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jian.Michael on 2017/4/5.
 */
public final class SqlManager {

    private static final SqlManager INSTANCE = new SqlManager();
    private final SqlRegistry sqlRegistry = new SqlRegistry();

    public static SqlManager getInstance() {
        return INSTANCE;
    }

    private Map<String, Map<String, String>> values = new ConcurrentHashMap<>();

    public SqlRegistry getSqlRegistry() {
        return sqlRegistry;
    }

    public Map<String, Map<String, String>> getValues() {
        return Collections.unmodifiableMap(values);
    }

    public void initialize() {
        if (this.values.isEmpty()) {
            Set<String> sqlMappingPath = sqlRegistry.getSqlMapping();
            if (!CollectionUtils.isEmpty(sqlMappingPath)) {
                for (String path : sqlMappingPath) {
                    try {
                        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                        builderFactory.setValidating(false);
                        builderFactory.setNamespaceAware(false);
                        Document document = builderFactory.newDocumentBuilder().parse(
                                Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
                        SqlConfigs sqlConfigs = XmlUtil.fromXml(SqlConfigs.class, document);
                        buildValues(sqlConfigs);
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void buildValues(SqlConfigs sqlConfigs) {
        List<SqlConfig> sqlConfigList = sqlConfigs.getSqlConfigs();
        String nameSpace = sqlConfigs.getNamespace();
        if (!CollectionUtils.isEmpty(sqlConfigList)) {
            Map<String, String> sqlMap = new HashMap();
            Iterator iterator = sqlConfigList.iterator();
            while (iterator.hasNext()) {
                SqlConfig sqlConfig = (SqlConfig) iterator.next();
                sqlMap.put(sqlConfig.getName(), sqlConfig.getValue());
            }
            this.values.put(nameSpace, sqlMap);
        }
    }

}
