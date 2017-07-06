package ifrn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utils {

    public static String cleanUp(String str) {
        return str.replaceAll("'", "\\\\'").replaceAll(" ", "");
    }

    public static Connection getConnection(
            final String host,
            final int port,
            final String schema,
            final String user,
            final String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema, user, password);
    }

    public static ExcelType excelTypeToMySql(Cell cell) {
        final FormulaEvaluator evaluator = cell.getRow().getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
            case Cell.CELL_TYPE_STRING:
                return ExcelType.STRING;
            case Cell.CELL_TYPE_BOOLEAN:
                return ExcelType.BOOLEAN;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return ExcelType.DATE;
                } else {
                    return ExcelType.NUMERIC;
                }
            default:
                return null;
        }
    }

    public static Workbook createWorkbook(File file) throws IOException {
        if (file.getName().endsWith(".xls")) {
            return new HSSFWorkbook(new FileInputStream(file));
        } else if (file.getName().endsWith(".xlsx")) {
            return new XSSFWorkbook(file.getAbsolutePath());
        }
        return null;
    }

    public static boolean typesContain(List<Map.Entry<String, ExcelType>> types, String str) {
        for (Map.Entry<String, ExcelType> entry : types) {
            if (entry != null && entry.getKey().equals(str)) {
                return true;
            }
        }

        return false;
    }

    public static void executeStatements(final Connection conn, final List<String> statements) throws SQLException {
        for (final String statement : statements) {
            conn.createStatement().execute(statement);
        }
    }

    public static void executeStatements(final Connection conn, final String... statements) throws SQLException {
        for (final String statement : statements) {
            conn.createStatement().execute(statement);
        }
    }
}
