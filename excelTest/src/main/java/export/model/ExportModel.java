package export.model;

import java.util.List;

public class ExportModel<T> {
    private String sheetName;   //excel名
    private Class clazz;        //数据对应的类
    private List<T> dataList;   //数据

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class getClazz() {
        return this.clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
