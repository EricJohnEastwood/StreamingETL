import java.util.ArrayList;

public class TargetTable {
    private String tableName;
    private ArrayList<String> columnName;
    private boolean tableInitialized; // true  initialized, false not initialized
    private boolean columnInitialized;


    public TargetTable() {
        this.tableName = "";
        this.columnName = new  ArrayList<String>();
        this.tableInitialized = false;
        this.columnInitialized = false;
    }

    public TargetTable(String tableName) {
        this.tableName = tableName;
        this.columnName = new  ArrayList<String>();
        this.tableInitialized = true;
        this.columnInitialized = false;
    }

    public TargetTable(String tableName, ArrayList<String> columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.tableInitialized = true;
        this.columnInitialized = true;
    }

    public String getTableName() {
        return this.tableName;
    }

    public ArrayList<String> getColumnName() {
        return this.columnName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.tableInitialized = true;
    }

    public void setColumnName(ArrayList<String> columnName) {
        this.columnName = columnName;
        this.columnInitialized = true;
    }

    public void setOneColumn(Integer position,String column_value) {
        this.columnName.set(position,column_value);
    }

    public String getOneColumn(Integer position) {
        return this.columnName.get(position);
    }


    @Override
    public String toString() {
        return "TargetTable{" +
                "tableName='" + tableName + '\'' +
                ", columnName=" + columnName +
                '}';
    }
}
