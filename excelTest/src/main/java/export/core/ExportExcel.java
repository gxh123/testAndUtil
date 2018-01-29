package export.core;

import export.model.CellModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportExcel implements Serializable {
    private static final long serialVersionUID = 5997336622105916301L;
    private String sheetName;
    private List<Map<Integer, Object>> valueMapList;  //每个map都是一行，map的key为列号，value为值
    private Map<String, Integer> methodIndexMap;
    private Map<Integer, CellModel> cellMap;

    public List<Map<Integer, Object>> getValueMapList() {
        if (null == this.valueMapList) {
            this.valueMapList = new ArrayList();
        }

        return this.valueMapList;
    }

    public void setValueMapList(List<Map<Integer, Object>> valueMapList) {
        this.valueMapList = valueMapList;
    }

    public Map<Integer, CellModel> getCellMap() {
        return this.cellMap;
    }

    public void setCellMap(Map<Integer, CellModel> cellMap) {
        this.cellMap = cellMap;
    }

    public Map<String, Integer> getMethodIndexMap() {
        return this.methodIndexMap;
    }

    public void setMethodIndexMap(Map<String, Integer> methodIndexMap) {
        this.methodIndexMap = methodIndexMap;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
