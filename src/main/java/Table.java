import java.util.ArrayList;

public class Table {
    protected String tableName;
    protected ArrayList<String> columnName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public Table(String tableName, ArrayList<String> columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public Table() {
        tableName = "";
        columnName  =new ArrayList<String>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(ArrayList<String> columnName) {
        this.columnName = columnName;
    }

//    public ArrayList<String> getKey() {
//        ArrayList<String> key = new ArrayList<String>();
//        key.add(this.columnName.get(0));
//        key.add(this.columnName.get(1));
//        return key;
//    }
}
