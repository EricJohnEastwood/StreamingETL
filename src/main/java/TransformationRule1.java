public class TransformationRule1 implements TransformationRule{
    @Override
    public void transform_csv(SourceRow source_row, TargetRow target_row) {

    }

    @Override
    public void transform_xml(SourceRow source_row, TargetRow target_row) {

    }

    @Override
    public void transform_json(SourceRow source_row, TargetRow target_row) {

    }

    public void transform_json(SourceRow source_row, TargetRow target_row, String mapping) {
        String data = source_row.getData();

//        targetRow.setRow("Foreign_Exchange_Id", "2");
    }
}
