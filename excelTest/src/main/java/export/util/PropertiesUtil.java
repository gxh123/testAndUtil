package export.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropertiesUtil {

    private static final String EXCEL_CONFIG = "excel_map.properties";
    private static final String SEPARATOR = ",";
    private static Properties properties = new Properties();


    public static Properties getPropertiesFile(String propFile) {
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFile);
        try {
            if (in == null) {
                return properties;
            }
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    //excel_map.properties存储的是excel的列对应类的属性的编号
    //例如UserData=0,1,2,3表明excel里的列对应UserData的编号分别为0，1，2，3的属性
    public static List<Integer> getInPropertyAsList(String proName) {
        Properties properties = getPropertiesFile(EXCEL_CONFIG);
        if (null == properties) {
            return null;
        } else {
            Object obj = properties.get(proName);
            String[] values = obj.toString().split(SEPARATOR);

            List<Integer> list = Arrays.stream(values).map(
                    s -> Integer.parseInt(s)).collect(Collectors.toList());
            return list;
        }
    }


}
