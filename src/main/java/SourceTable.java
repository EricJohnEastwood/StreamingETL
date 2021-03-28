import java.util.ArrayList;

public class SourceTable {
    private String tableName;
    private ArrayList<String> columnName;
    private String urlRunModule;
    private String storeDataModule;
    private boolean tableInitialized; // true  init, false not int
    private boolean columnInitialized;


    public SourceTable() {
        this.tableName = "";
        this.columnName = new  ArrayList<String>();
        this.urlRunModule= "";
        this.storeDataModule = "";
        this.tableInitialized = false;
        this.columnInitialized = false;
        System.out.println("Constructed SourceTable");
    }

    public SourceTable(String tableName) {
        this.tableName = tableName;
        this.columnName = new  ArrayList<String>();
        this.urlRunModule= "";
        this.storeDataModule = "";
        this.tableInitialized = true;
        this.columnInitialized = false;
        System.out.println("Constructed SourceTable");
    }

    public SourceTable(String tableName, ArrayList<String> columnName, String urlRunModule, String storeDataModule) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.urlRunModule= urlRunModule;
        this.storeDataModule = storeDataModule;
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

    public String getUrlRunModule() {
        return urlRunModule;
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

    public void setUrlRunModule(String urlRunModule) {
        this.urlRunModule = urlRunModule;
    }

    public void setStoreDataModule(String storeDataModule) {
        this.storeDataModule = storeDataModule;
    }

}
