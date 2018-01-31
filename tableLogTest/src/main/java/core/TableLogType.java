package core;

public enum TableLogType {

    INSERT("插入"),
    UPDATE("更新"),
    DELETE("删除");

    private String value;

    TableLogType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
