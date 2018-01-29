package export.core;

import export.annotation.Import;
import export.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Method;
import java.util.*;

public class ExcelImporter {

    private static final Log log = LogFactory.getLog(ExcelImporter.class);

    public static <T> List<T> parseExcelToList(Workbook workbook, Class<T> clazz, String proName) {
        if (workbook != null && clazz != null) {
            new ArrayList();
            final List<SetMethodInfo> methods = getSetMethods(clazz, proName);
            if (methods != null && methods.size() != 0) {
                List<Row> excelRows = getExcelRows(workbook, methods.size(), 1);
                return generatorList(excelRows, methods, clazz);
            }
        }
        return null;
    }

    private static List<Row> getExcelRows(Workbook workbook, Integer columnNum, Integer startIndex) {
        List<Row> resultList = new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet != null) {
            int rowIndex = startIndex;
            while(sheet.getRow(rowIndex) != null) {
                Row row = sheet.getRow(rowIndex++);
                resultList.add(row);
            }
        }
        return resultList;
    }

    private static <T> List<T> generatorList(List<Row> rows, List<SetMethodInfo> methods, Class<T> clazz) {
        if (CollectionUtils.isNotEmpty(methods) && CollectionUtils.isNotEmpty(rows)) {
            List<T> resultList = new ArrayList();
            for(Integer i = 0; i < rows.size(); i = i + 1) {
                Row row = rows.get(i);
                try {
                    T instance = clazz.newInstance();
                    for(Integer j = 0; j < methods.size(); j = j + 1) {
                        SetMethodInfo setMethod = methods.get(j);
                        if (setMethod != null) {
                            try {
                                setMethod.setValue(instance, row.getCell(j));
                            } catch (Exception var10) {
                                log.error("行【" + (i + 1) + "】列【" + (j + 1) + "】设值失败！", var10);
                                throw new RuntimeException("行【" + (i + 1) + "】列【" + (j + 1) + "】数据格式错误!");
                            }
                        }
                    }
                    resultList.add(instance);
                } catch (Exception var11) {
                    log.error("初始化对象失败!", var11);
                    throw new RuntimeException("初始化对象失败!" + var11.getMessage(), var11);
                }
            }
            return resultList;
        } else {
            return null;
        }
    }

    public static List<SetMethodInfo> getSetMethods(Class clazz, String proName) {
        Method[] methods = clazz.getMethods();
        Map<Integer, SetMethodInfo> methodMap = new HashMap();

        //获取属性编号与set方法的map   <编号，set方法>
        for(int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (m.isAnnotationPresent(Import.class)) {
                Import in = (Import)m.getAnnotation(Import.class);
                if (StringUtils.isNotBlank(in.index())) {
                    Integer index = Integer.parseInt(in.index());
                    if (methodMap.containsKey(index)) {
                        throw new RuntimeException("index【" + index + "】重复");
                    }
                    SetMethodInfo methodInfo = new SetMethodInfo();
                    methodInfo.setMethod(m);
                    methodMap.put(index, methodInfo);
                }
            }
        }

        //按编号将map转为list
        List<SetMethodInfo> methodList = new ArrayList();
        List<Integer> configList = PropertiesUtil.getInPropertyAsList(proName);
        if (CollectionUtils.isNotEmpty(configList)) {
            for(Integer index : configList){
                SetMethodInfo m = methodMap.get(index);
                methodList.add(m);
            }
        }
        return methodList;
    }

}
