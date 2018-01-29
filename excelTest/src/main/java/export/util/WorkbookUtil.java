package export.util;

import export.core.ExportExcel;
import export.model.CellModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WorkbookUtil {

    public static int MAX_SIZE = 60000;

    public static HSSFWorkbook createWorkBook(ExportExcel ee) {
        if (null == ee) {
            return null;
        } else {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFCellStyle cellStyle = wb.createCellStyle();
            return createWorkBookSheet(wb, cellStyle, ee);
        }
    }

    public static HSSFWorkbook createWorkBookSheet(HSSFWorkbook wb, HSSFCellStyle cellStyle, ExportExcel ee) {
        if (ee == null) return null;

        cellStyle.setWrapText(true);
        if (CollectionUtils.isNotEmpty(ee.getValueMapList())) {
            //最多MAX_SIZE（60000）行，大于MAX_SIZE的要再创建sheet
            for (int i = 0; i * MAX_SIZE < ee.getValueMapList().size(); ++i) {
                String sheetName = null;
                if (StringUtils.isNotBlank(ee.getSheetName())) {
                    sheetName = ee.getSheetName();
                } else {
                    sheetName = "sheet" + (i + 1);
                }
                HSSFSheet sheet = wb.createSheet(sheetName);
                createHead(sheet, ee, wb, 0);               //创建标题栏
                createRows(sheet, ee, wb, i, cellStyle, 1); //创建具体的记录
            }
        } else {
            HSSFSheet sheet = wb.createSheet("sheet");
            createHead(sheet, ee, wb, 0);
        }
        return wb;
    }

    //创建标题栏
    private static void createHead(HSSFSheet sheet, ExportExcel ee, HSSFWorkbook wb, int marginTop) {
        if (ee.getCellMap().size() > 0) {
            HSSFRow row = sheet.createRow(marginTop);
            for (int i = 0; i < ee.getCellMap().size(); ++i) {
                CellModel c = ee.getCellMap().get(i);
                if (c != null) {
                    sheet.setColumnWidth(i, c.getCellWidth());
                    HSSFCell cell = row.createCell(i);       //第i列cell
                    cell.setCellValue(c.getDescription());   //第i列内容
                }
            }
        }
    }

    private static void createRows(HSSFSheet sheet, ExportExcel ee, HSSFWorkbook wb,
                                   int count, HSSFCellStyle cellStyle, int marginTop) {
        int toIndex = ee.getValueMapList().size() < MAX_SIZE * (count + 1) ? ee.getValueMapList().size() : MAX_SIZE * (count + 1);
        List<Map<Integer, Object>> valueMapList = ee.getValueMapList().subList(count * MAX_SIZE, toIndex);
        int i = marginTop;

        for (int valueIndex = 1; i <= valueMapList.size() + marginTop - 1; ) {
            HSSFRow row = sheet.createRow(i);   //创建第i行表格
            createRow(row, (Map) valueMapList.get(valueIndex - 1), cellStyle);  //填充数据
            ++i;            //excel里的行号
            ++valueIndex;   //输入的数据的编号
        }
    }

    private static void createRow(HSSFRow row, Map<Integer, Object> valueMap, HSSFCellStyle cellStyle) {
        for(int i = 0; i < valueMap.size(); ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            if (valueMap.get(i) != null) {
                Object value = valueMap.get(i).toString();
                cell.setCellValue(value.toString());
            }
        }
    }

}
