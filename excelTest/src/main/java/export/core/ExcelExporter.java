package export.core;

import export.annotation.AnnotationParser;
import export.model.CellModel;
import export.model.ExportModel;
import export.util.WorkbookUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExporter {

    public static HSSFWorkbook export(ExportModel model) {
        return export(model.getDataList(), model.getClazz());
    }

    public static HSSFWorkbook export(List values, Class clazz) {
        ExportExcel ee = parseBean(values, clazz);
        HSSFWorkbook hwb = WorkbookUtil.createWorkBook(ee);
        return hwb;
    }

    private static ExportExcel parseBean(List values, Class clazz) {
        if (null == clazz && null != values && values.size() > 0) {
            clazz = values.get(0).getClass();
        }

        if (null == clazz) {
            return null;
        } else {
            ExportExcel ee = AnnotationParser.parseAnnotations(clazz);
            for(Object o : values){
                //解析每个对象
                parseValueByAnnotation(o, ee);
            }
            return ee;
        }
    }

    //从obj中获取每一列对应的属性值，返回<列号，值>的map
    private static void parseValueByAnnotation(Object obj, ExportExcel ee) {
        Map<Integer, CellModel> cellMap = ee.getCellMap();
        if (null != cellMap && cellMap.size() > 0) {
            Map<Integer, Object> valueMap = new HashMap();
            Class clazz = obj.getClass();
            for(Map.Entry entry : cellMap.entrySet()){
                try {
                    //反射获取属性值
                    Method m = clazz.getMethod(((CellModel)entry.getValue()).getMethodName());
                    Object value = m.invoke(obj);
                    //列号与对应的值
                    valueMap.put((Integer) entry.getKey(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ee.getValueMapList().add(valueMap);
        }

    }

}
