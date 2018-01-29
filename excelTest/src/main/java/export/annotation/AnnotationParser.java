package export.annotation;

import export.core.ExportExcel;
import export.model.CellModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationParser {

    public AnnotationParser() {
    }

    public static ExportExcel parseAnnotations(Class clazz) {
        ExportExcel excel = new ExportExcel();
        Map<String, Integer> methodIndexMap = new HashMap();
        Map<Integer, CellModel> cellMap = new HashMap();

        //遍历类的方法
        for(Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Export.class)) {
                Export e = method.getAnnotation(Export.class);
                //获取所在列号
                int index = Integer.parseInt(e.index());
                CellModel cell = new CellModel();
                methodIndexMap.put(method.getName(), index);
                cell.setMethodName(method.getName());
                //设置description
                String description = "";
                if (null != e.description() && !e.description().equals("")) {
                    description = e.description();
                } else if (method.getName().startsWith("get")) {
                    description = method.getName().replaceFirst("get", "");
                }
                cell.setDescription(description);
                //设置width
                Integer width = Integer.parseInt(e.cellWidth());
                cell.setCellWidth(width);

                cellMap.put(index, cell);
            }
        }

        excel.setCellMap(cellMap);
        excel.setMethodIndexMap(methodIndexMap);
        return excel;
    }

    public static Map<Integer, String> parseMethod(Class clazz) {
        Map<Integer, String> map = new HashMap();
        Method[] ms = clazz.getDeclaredMethods();
        Method[] var3 = ms;
        int var4 = ms.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (m.isAnnotationPresent(Export.class)) {
                Annotation an = m.getAnnotation(Export.class);
                Export e = (Export)an;
                int index = checkIndex(clazz, m, e);
                map.put(index, m.getName());
            }
        }

        return map;
    }

    public static Map<Integer, String> parseDescription(Class clazz) {
        Map<Integer, String> map = new HashMap();
        Method[] ms = clazz.getDeclaredMethods();
        Method[] var3 = ms;
        int var4 = ms.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            if (m.isAnnotationPresent(Export.class)) {
                Annotation an = m.getAnnotation(Export.class);
                Export e = (Export)an;
                String methodName = "";
                if (null != e.description() && !e.description().equals("")) {
                    methodName = e.description();
                } else if (m.getName().startsWith("get")) {
                    methodName = m.getName().replaceFirst("get", "");
                }

                int index = checkIndex(clazz, m, e);
                map.put(index, methodName);
            }
        }

        return map;
    }

    private static int checkIndex(Class clazz, Method m, Export e) {
        int index;
        if (null != e.index()) {
            try {
                index = Integer.parseInt(e.index());
                if (index < 0) {
                    throw new RuntimeException("[" + clazz.toString() + "." + m.getName() + "]Export.index属性值必须是正整数");
                } else {
                    return index;
                }
            } catch (Exception var5) {
                throw new RuntimeException("[" + clazz.toString() + "." + m.getName() + "]Export.index属性值必须是正整数");
            }
        } else {
            throw new RuntimeException("[" + clazz.toString() + "." + m.getName() + "]Export.index属性未赋值");
        }
    }

}
