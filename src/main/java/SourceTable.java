import java.util.ArrayList;

public class SourceTable {
    private String tableName;
    private ArrayList<String> columnName;
    private boolean tableInitialized; // true  init, false not int
    private boolean columnInitialized;


    public SourceTable() {
        this.tableName = "";
        this.columnName = new  ArrayList<String>();
        this.tableInitialized = false;
        this.columnInitialized = false;
    }

    public SourceTable(String tableName) {
        this.tableName = tableName;
        this.columnName = new  ArrayList<String>();
        this.tableInitialized = true;
        this.columnInitialized = false;
    }

    public SourceTable(String tableName, ArrayList<String> columnName) {
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

}
