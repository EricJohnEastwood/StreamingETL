import java.util.ArrayList;

public class TargetTable extends Table {
    private String storeDataModule;
    private boolean tableInitialized; // true  initialized, false not initialized
    private boolean columnInitialized;


    public TargetTable() {
        super("");
        this.columnName = new  ArrayList<String>();
        this.storeDataModule = "";
        this.tableInitialized = false;
        this.columnInitialized = false;
    }

    public TargetTable(String tableName) {
        super(tableName);
        this.columnName = new  ArrayList<String>();
        this.storeDataModule = "";
        this.tableInitialized = true;
        this.columnInitialized = false;
    }

    public TargetTable(String tableName, ArrayList<String> columnName, String storeDataModule) {
        super(tableName, columnName);
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

    public void insert_one_row(TargetRow targetRow, ConnectionDB connectionDB){
        String[] entry_details = new String[targetRow.size()];
        String[] column_details = new String[targetRow.size()];
        int j=0;
        for(String i: targetRow.getRow().keySet()){
            column_details[j] = i;
            entry_details[j++] = targetRow.getRow().get(i);
        }
        String insertCommand = GenInstructionDB.insert_instruction(this.tableName, column_details, entry_details);
        System.out.println(insertCommand);
        connectionDB.insertIntoTable(insertCommand);
    }

    @Override
    public String toString() {
        return "TargetTable{" +
                "tableName='" + tableName + '\'' +
                ", columnName=" + columnName +
                ", storeDataModule='" + storeDataModule + '\'' +
                '}';
    }
}
