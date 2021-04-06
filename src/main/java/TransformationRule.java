public interface TransformationRule {
    public void transform_csv(SourceRow source_row, TargetRow target_row);
    public void transform_xml(SourceRow source_row, TargetRow target_row);
    public void transform_json(SourceRow source_row, TargetRow target_row);
}
