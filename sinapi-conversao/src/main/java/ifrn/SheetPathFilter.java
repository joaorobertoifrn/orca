package ifrn;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.poi.ss.usermodel.Sheet;

public class SheetPathFilter implements SheetFilter {

    final Set<String> acceptedPaths;

    public SheetPathFilter() {
        this.acceptedPaths = new TreeSet<String>();
        this.acceptedPaths.add("*.*");
    }

    public SheetPathFilter(String... allowedPaths) {
        this.acceptedPaths = new TreeSet<String>();
        this.acceptedPaths.addAll(Arrays.asList(allowedPaths));
    }

    @Override
    public boolean accept(Sheet sheet) {
        final String sheetName = Utils.cleanUp(sheet.getSheetName());
        for (String str : this.acceptedPaths) {
            if (str.startsWith("*.")
                    || str.equals(sheetName)
                    || str.startsWith(sheetName + ".")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean accept(Sheet sheet, String column) {
        final String sheetName = Utils.cleanUp(sheet.getSheetName());
        //Column name path {sheet}.{column} or {sheet}.*
        for (String str : acceptedPaths) {
            if (str.equals("*.*")) {
                return true;
            }
            if (str.startsWith(sheetName)) {
                final String columnPath = str.substring(sheetName.length() + 1);
                if (columnPath.equals("*") || columnPath.equals(column)) {
                    return true;
                }
            }
        }
        return false;
    }
}
