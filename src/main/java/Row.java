import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Row {
    protected Table table;
    protected HashMap<String, String> row;

    public Row(Table table, HashMap<String, String> data) {
        this.table = table;
        this.row = data;
    }

    public Row(Table table) {
        this.table = table;
        this.row = new HashMap<String, String>();
    }

    public Table getTable() {
        return table;
    }

    public HashMap<String, String> getRow() {
        return row;
    }

    public String getRow(String col) {
        return this.row.get(col);
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setRow(HashMap<String, String> data) {
        this.row = data;
    }

    public void setRow(String col, String val){
        this.row.put(col, val);
    }

    public void setRow(ArrayList<String> columnValues){
        ArrayList<String> columnNames = table.getColumnName();
        for(int i=0; i<columnNames.size(); i++){
            this.row.put(columnNames.get(i), columnValues.get(i));
        }
    }

    public int size(){
        return row.size();
    }

    @Override
    public String toString() {
        return "Row{" +
                "table=" + table +
                ", row=" + row +
                '}';
    }
}
