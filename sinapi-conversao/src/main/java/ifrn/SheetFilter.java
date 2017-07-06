
package ifrn;

import org.apache.poi.ss.usermodel.Sheet;

public interface SheetFilter {

    public boolean accept(Sheet sheet);

    public boolean accept(Sheet sheet, String column);
}
