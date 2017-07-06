package ifrn;

public enum ExcelType {

    STRING("LONGTEXT"),
    BOOLEAN("BOOLEAN"),
    DATE("DATETIME"),
    NUMERIC("FLOAT");
    private String mysqlType;

    ExcelType(String mysqlType) {
        this.mysqlType = mysqlType;
    }

    public String getMySqlType() {
        return mysqlType;
    }
}
