import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TableLogConfig {

    private Map<String, TableConfig> configMap;

    public TableLogConfig(){
        String path = "/Users/gxh/IdeaProjects2/testAndUtil/xmlTest/src/main/resources/";
        path = path + "tableLogConfig.xml";
        this.configMap = parseXml(path);
    }

    public Map<String, TableConfig> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, TableConfig> configMap) {
        this.configMap = configMap;
    }

    public TableConfig get(String cause) {
        return configMap.get(cause);
    }

    public static Map<String, TableConfig> parseXml(String path){
//        SAXBuilder builder = new SAXBuilder();
//        Field[] fields = TableConfig.class.getDeclaredFields();
//        Map<String, TableConfig> configMap = new HashMap<>();
//        try {
//            String xml = new String(Files.readAllBytes(Paths.get(path)));
//            Document doc = builder.build(new StringReader(xml));
//            Element root = doc.getRootElement();
//            List<Element> children = root.getChildren("log");
//            for(Element e : children){
//                TableConfig tableConfig = new TableConfig();
//                for (Field field : fields) {
//                    field.setAccessible(true);
//                    if(field.getType().isAssignableFrom(List.class)){  //list
//                        List<Element> elements = e.getChildren();
//                        List<String> columns = new ArrayList<>();
//                        elements.forEach(s->columns.add(s.getTextTrim()));
//                        field.set(tableConfig, columns);
//                    }else{   //基本类型
//                        field.set(tableConfig, e.getAttribute(field.getName()).getValue());
//                    }
//
//                }
//                configMap.put(e.getAttribute("cause").getValue(), tableConfig);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return configMap;
        return null;
    }

    static class TableConfig{
        private String tableName;
        private String idColumn;
        private List<String> columns;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getIdColumn() {
            return idColumn;
        }

        public void setIdColumn(String idColumn) {
            this.idColumn = idColumn;
        }

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }
    }

}
