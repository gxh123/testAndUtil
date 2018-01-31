package core;

import org.springframework.stereotype.Component;
import util.XmlUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TableLogConfig {

    private Map<String, TableConfig> configMap;
    private String path;

    @PostConstruct
    public void init(){
        configMap = XmlUtil.parseXml(path);
    }

    public Map<String, TableConfig> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, TableConfig> configMap) {
        this.configMap = configMap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TableConfig get(String cause) {
        return configMap.get(cause);
    }

    public static class TableConfig{
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
