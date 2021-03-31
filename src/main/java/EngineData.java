import java.util.ArrayList;
import java.util.HashMap;


public class EngineData {
    private SourceTable source_table;
    private TargetTable target_table;
    private HashMap<ArrayList<String>,Transformations> transformations;


    public EngineData() {
        this.source_table = new SourceTable();
        this.target_table = new TargetTable();
        this.transformations = new HashMap<ArrayList<String>,Transformations>();

    }

    public SourceTable getSourceTable() {
        return this.source_table;
    }

    public TargetTable getTargetTable() {
        return this.target_table;
    }

    public void constructSourceTable(String tableName, ArrayList<String> columnName, String urlRunModule, String storeDataModule) {
        this.source_table.setTableName(tableName);
        this.source_table.setColumnName(columnName);
        this.source_table.setUrlRunModule(urlRunModule);
        this.source_table.setStoreDataModule(storeDataModule);
    }

    public void constructTargetTable(String tableName, ArrayList<String> columnName, String storeDataModule) {
        this.target_table.setTableName(tableName);
        this.target_table.setColumnName(columnName);
        this.target_table.setStoreDataModule(storeDataModule);
    }

    public void constructTransformations(String url, String data_type, ArrayList<String> transformationTypesModule) {
        ArrayList<String> key = new ArrayList<String>();
        key.add(url);
        key.add(data_type);

        Transformations value = new Transformations(url, data_type, transformationTypesModule);

        this.transformations.put(key, value);
    }

    @Override
    public String toString() {
        return "EngineData{ " + this.source_table.toString() + " \n" + this.target_table.toString() +
                " \n" + this.transformations.toString() + " }";
    }
}
