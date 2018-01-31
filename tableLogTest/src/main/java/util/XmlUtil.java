package util;

import core.TableLogConfig.*;
import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {

    public static Map<String, TableConfig> parseXml(String path){
        SAXBuilder builder = new SAXBuilder();
        Field[] fields = TableConfig.class.getDeclaredFields();
        Map<String, TableConfig> configMap = new HashMap<>();
        try {
            //利用classpath读取xml文件
            InputStream in = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(path);
            String xml = IOUtils.toString(in, "UTF-8");
            Document doc = builder.build(new StringReader(xml));
            Element root = doc.getRootElement();
            List<Element> children = root.getChildren("log");
            for(Element e : children){
                TableConfig tableConfig = new TableConfig();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if(field.getType().isAssignableFrom(List.class)){  //list
                        List<Element> elements = e.getChildren();
                        List<String> columns = new ArrayList<>();
                        elements.forEach(s->columns.add(s.getTextTrim()));
                        field.set(tableConfig, columns);
                    }else{   //基本类型
                        field.set(tableConfig, e.getAttribute(field.getName()).getValue());
                    }
                }
                configMap.put(e.getAttribute("cause").getValue(), tableConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configMap;
    }


}
