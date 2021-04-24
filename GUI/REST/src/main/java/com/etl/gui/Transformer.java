package com.etl.gui;

import java.util.ArrayList;

public interface Transformer {
    public void transform_csv(String source_row, TargetTable target_row);
    public void transform_xml(String source_row, TargetTable target_row);
    public void transform_for_json(String source_row, TargetTable target_row);
    public void transform_for_json(SourceTable source_row, ArrayList<TargetTable> target_row, Transformations transformations) throws Exception;
}