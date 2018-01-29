package export;

import export.core.ExcelExporter;
import export.core.ExcelImporter;
import export.model.ExportModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class ExcelUtils {

    public static HSSFWorkbook export(ExportModel model) {
        return ExcelExporter.export(model);
    }

    public static <T> List<T> parseExcelToList(Workbook workbook, Class<T> clazz, String proName) {
        return ExcelImporter.parseExcelToList(workbook, clazz, proName);
    }
}
