import java.util.ArrayList;
import java.util.HashMap;

public class SourceRow extends Row{
    private SourceTable table;

    public SourceRow(SourceTable table, HashMap<String, String> data) {
        super(table, data);
        this.table = table;
    }

    public SourceRow(SourceTable table) {
        super(table);
        this.table = table;
    }

    public ArrayList<String> getKey(){
        ArrayList<String> columnNames = this.table.getKey();
        ArrayList<String > key = new ArrayList<String>();
        for(String i: columnNames){
            key.add(this.row.get(i));
        }
        return key;
    }

    public String getData(){
        return this.row.get(this.table.getData());
    }
}
