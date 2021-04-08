import java.util.ArrayList;

public class SourceTable extends Table{
    private String data_time_column;
    private String urlRunModule;
    private String storeDataModule;
    private boolean tableInitialized; // true  init, false not int
    private boolean columnInitialized;


    public SourceTable() {
        super();
        this.urlRunModule= "";
        this.storeDataModule = "";
        this.tableInitialized = false;
        this.columnInitialized = false;
        this.data_time_column = "Date_Time";
        System.out.println("Constructed SourceTable");
    }

    public SourceTable(String tableName) {
        super(tableName);
        this.columnName = new  ArrayList<String>();
        this.urlRunModule= "";
        this.storeDataModule = "";
        this.tableInitialized = true;
        this.columnInitialized = false;
        this.data_time_column = "Data_Time";
        System.out.println("Constructed SourceTable");
    }

    public SourceTable(String tableName, ArrayList<String> columnName, String urlRunModule, String storeDataModule) {
        super(tableName, columnName);
        this.urlRunModule= urlRunModule;
        this.storeDataModule = storeDataModule;
        this.tableInitialized = true;
        this.columnInitialized = true;
        this.data_time_column = "Data_Time";
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

    public ArrayList<String> getKey() {
        ArrayList<String> key = new ArrayList<String>();
        key.add(this.columnName.get(0));
        key.add(this.columnName.get(1));
        return key;
    }

    public  String getData() {
        return this.columnName.get(3);
    }

    public void setUrlRunModule(String urlRunModule) {
        this.urlRunModule = urlRunModule;
    }

    public void setStoreDataModule(String storeDataModule) {
        this.storeDataModule = storeDataModule;
    }

    public SourceRow select_top_row(ConnectionDB connectionDB){
        String selectCommand = GenInstructionDB.select_one_instruction(this.tableName, this.data_time_column);
        return connectionDB.selectFromTable(selectCommand, this);
    }

    public void delete_row(ConnectionDB connectionDB, SourceRow sourceRow){
        String deleteCommand = GenInstructionDB.delete_instruction(this.tableName, this.data_time_column, sourceRow.getRow(this.data_time_column));
        connectionDB.deleteFromTable(deleteCommand);
    }

    @Override
    public String toString() {
        return "SourceTable{" +
                "tableName='" + tableName + '\'' +
                ", columnName=" + columnName +
                ", urlRunModule='" + urlRunModule + '\'' +
                ", storeDataModule='" + storeDataModule + '\'' +
                '}';
    }
}
