package demo;

import export.model.ExportModel;
import export.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //export();

        input();
    }

    public static void export(){
        List<UserData> userDataList = getData();
        ExportModel<UserData> model = new ExportModel<>();
        model.setSheetName("异常记录");
        model.setDataList(userDataList);
        model.setClazz(UserData.class);

        Workbook wb = ExcelUtils.export(model);
        try {
            String fileName = "/Users/gxh/Desktop/测试excel2" + ".xls";
            OutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            wb.write(bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void input() throws IOException {
        String fileName = "/Users/gxh/Desktop/测试excel2" + ".xls";
        InputStream is = new FileInputStream(fileName);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<UserData> list = ExcelUtils.parseExcelToList(hssfWorkbook,UserData.class, "UserData");;
    }

    private static List<UserData> getData(){
        List<UserData> userDataList = new ArrayList<>();
        UserData userData1 = new UserData("郭小虎",26,"sspu",new Date());
        UserData userData2 = new UserData("郭小虎2",27,"sspu2",new Date());
        userDataList.add(userData1);
        userDataList.add(userData2);
        return userDataList;
    }


}
