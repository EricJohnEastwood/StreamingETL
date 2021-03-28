import java.util.ArrayList;

public class TargetTable {
    private String tableName;
    private ArrayList<String> columnName;
    private String storeDataModule;
    private boolean tableInitialized; // true  initialized, false not initialized
    private boolean columnInitialized;


    public TargetTable() {
        this.tableName = "";
        this.columnName = new  ArrayList<String>();
        this.storeDataModule = "";
        this.tableInitialized = false;
        this.columnInitialized = false;
    }

    public TargetTable(String tableName) {
        this.tableName = tableName;
        this.columnName = new  ArrayList<String>();
        this.storeDataModule = "";
        this.tableInitialized = true;
        this.columnInitialized = false;
    }

    public TargetTable(String tableName, ArrayList<String> columnName, String storeDataModule) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.storeDataModule = storeDataModule;
        this.tableInitialized = true;
        this.columnInitialized = true;
    }

    public String getTableName() {
        return this.tableName;
    }

    public ArrayList<String> getColumnName() {
        return this.columnName;
    }

    public String getStoreDataModule() {
        return storeDataModule;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        this.tableInitialized = true;
    }

    public void setColumnName(ArrayList<String> columnName) {
        this.columnName = columnName;
        this.columnInitialized = true;
    }

    public void setStoreDataModule(String storeDataModule) {
        this.storeDataModule = storeDataModule;
    }
}
