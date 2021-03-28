
public class MainMenu {
    public static void main(String[] args) {
//        testing transform class
        Transform.get_source_table("source.xml");
        Transform.get_target_table("target.xml");
        Transform.get_transformation("target.xml");


//        testing instructions construction in GenInstructionDB
        String[] entry_details = new String[5];
        entry_details[0] = "eric";
        entry_details[1] = "john";
        System.out.println(GenInstructionDB.insert_instruction("source_data_dump", entry_details));
        Transform t = new Transform();
    }
}
