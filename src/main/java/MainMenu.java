
public class MainMenu {
    public static void main(String[] args) {
        EngineData engine = new EngineData();
//        testing transform class
        Transform.get_source_table("source.xml", engine);
        Transform.get_target_table("target.xml", engine);
        Transform.get_transformation("target.xml", engine);

        System.out.println(engine);


//        testing instructions construction in GenInstructionDB
        String[] entry_details = new String[5];
        entry_details[0] = "eric";
        entry_details[1] = "john";
        System.out.println(GenInstructionDB.insert_instruction("source_data_dump", entry_details));
        System.out.println(GenInstructionDB.select_one_row_instruction("source_data_dump", "Date_Time"));

    }
}
