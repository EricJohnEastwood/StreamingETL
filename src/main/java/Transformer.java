public interface Transformer {
    public void transform_csv(String source_row, TargetTable target_row);
    public void transform_xml(String source_row, TargetTable target_row);
    public void transform_for_json(String source_row, TargetTable target_row);
}