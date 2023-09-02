package server.entity;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class RegistedUserTableModel extends AbstractTableModel {
    private String[] attribute ={"QQ号", "密码", "用户名", "性别"};
    private List<String[]> rows = new ArrayList<String[]>();
    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
        return this.attribute.length;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        return this.rows.get(rowIndex)[columnIndex];
    }

    public String getColumnName(int columnIndex) {
        return this.attribute[columnIndex];
    }

    public void AddRow(String[] value) {
        int rowIndex = rows.size();
        rows.add(value);
        fireTableRowsInserted(rowIndex, rowIndex);
    }
    public void RemoveRow(long id) {
        int rowIndex = Integer.parseInt(null);
        for(int i = 0; i < rows.size(); i++) {
            if (String.valueOf(id).equals(getValueAt(i, 0))) {
                rowIndex = i;
                break;
            }
        }
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
