import java.util.HashMap;

public class TargetRow extends Row{
    private TargetTable table;
    public TargetRow(TargetTable table, HashMap<String, String> data) {
        super(table, data);
        this.table = table;
    }

    public TargetRow(TargetTable table) {
        super(table);
        this.table = table;
    }
}
