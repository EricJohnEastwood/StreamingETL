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
        System.out.println("Constructed SourceTable");
    }

    public SourceTable(String tableName) {
        this.tableName = tableName;
        this.columnName = new  ArrayList<String>();
        this.tableInitialized = true;
        this.columnInitialized = false;
        System.out.println("Constructed SourceTable");
    }

    public SourceTable(String tableName, ArrayList<String> columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.tableInitialized = true;
        this.columnInitialized = true;
        System.out.println("Constructed SourceTable");
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

    public void setOneColumn(Integer position,String column_value) {
        this.columnName.set(position,column_value);
    }

    public String getOneColumn(Integer position) {
        return this.columnName.get(position);
    }

    public void setColumnName(ArrayList<String> columnName) {
        this.columnName = columnName;
        this.columnInitialized = true;
    }

    public ArrayList<String> getKey() {
        ArrayList<String> key = new ArrayList<String>();
        key.add(this.columnName.get(0));
        key.add(this.columnName.get(1));
        return key;
    }

    public  String getData() {
        return this.columnName.get(3);
    }

    public Integer getSize() {
        return this.columnName.size();
    }

    @Override
    public String toString() {
        return "SourceTable{" +
                "tableName='" + tableName + '\'' +
                ", columnName=" + columnName +
                '}';
    }
}
