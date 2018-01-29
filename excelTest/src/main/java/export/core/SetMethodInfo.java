package export.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class SetMethodInfo {

    private static final Log log = LogFactory.getLog(SetMethodInfo.class);
    private Method method;
    private Class parameterType;

    public void setValue(Object obj, Cell cell) {
        Object value = this.getCellValue(cell);
        //空值
        if (value == null) {
            try {
                this.method.invoke(obj, this.getNullValue());
            } catch (Exception var8) {
                log.error("设值失败！", var8);
                throw new RuntimeException("设值失败！", var8);
            }
        //相同类型，即String
        } else if (value.getClass() == this.parameterType) {
            try {
                this.method.invoke(obj, value);
            } catch (Exception var7) {
                log.error("设值失败！", var7);
                throw new RuntimeException("设值失败！", var7);
            }
        //不同类型
        } else {
            try {
                this.method.invoke(obj, this.getValue(value.toString()));
            } catch (Exception var6) {
                log.error("设值失败！", var6);
                throw new RuntimeException("设值失败！方法【" + this.method.getName() + "】,值【" + value + "】", var6);
            }
        }

    }

    private Object getCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == 2) {
                return String.valueOf(cell.getNumericCellValue());
            }
            return getValueForString(cell);
        }

        return null;
    }

    private Object getNullValue() {
        if (this.parameterType != Integer.TYPE &&
                this.parameterType != Byte.TYPE &&
                this.parameterType != Short.TYPE) {
            if (this.parameterType == Long.TYPE) {
                return 0L;
            } else if (this.parameterType == Float.TYPE) {
                return 0.0F;
            } else if (this.parameterType == Double.TYPE) {
                return 0.0D;
            } else {
                return this.parameterType == Boolean.TYPE ? false : null;
            }
        } else {
            return 0;
        }
    }

    private Object getValue(String value) {
        if (this.parameterType != Integer.TYPE && this.parameterType != Integer.class) {
            if (this.parameterType != Long.TYPE && this.parameterType != Long.class) {
                if (this.parameterType != Double.class && this.parameterType != Double.TYPE) {
                    if (this.parameterType != Float.TYPE && this.parameterType != Float.class) {
                        if (this.parameterType != Byte.TYPE && this.parameterType != Byte.class) {
                            if (this.parameterType != Short.TYPE && this.parameterType != Short.class) {
                                return value;
                            } else {
                                if (value.indexOf(".") != -1) {
                                    value = value.substring(0, value.indexOf("."));
                                }
                                return new Short(value);
                            }
                        } else {
                            if (value.indexOf(".") != -1) {
                                value = value.substring(0, value.indexOf("."));
                            }
                            return new Byte(value);
                        }
                    } else {
                        return new Float(value);
                    }
                } else {
                    return new Double(value);
                }
            } else {
                if (value.indexOf(".") != -1) {
                    value = value.substring(0, value.indexOf("."));
                }

                return new Long(value);
            }
        } else {
            if (value.indexOf(".") != -1) {
                value = value.substring(0, value.indexOf("."));
            }
            return new Integer(value);
        }
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
        Class[] types = method.getParameterTypes();
        if (types.length != 1) {
            throw new RuntimeException("方法不符合set方法规范");
        } else {
            this.parameterType = types[0];
        }
    }

    public static String getValueForString(Cell cell) {
        String str = null;
        if (cell != null) {
            switch(cell.getCellType()) {
                case 0:
                    DecimalFormat df = new DecimalFormat("0");
                    str = df.format(cell.getNumericCellValue());
                    break;
                case 1:
                    str = cell.getStringCellValue();
            }
        }

        if (str != null) {
            str = str.trim();
        }

        return str;
    }

}
