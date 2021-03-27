
public class MainMenu {
    public static void main(String[] args) {
        Transform.get_source_db("source.xml");
        String[] entry_details = new String[5];
        entry_details[0] = "eric";
        entry_details[1] = "john";
        System.out.println(GenInstructionDB.insert_instruction("source_data_dump", entry_details));
        Transform t = new Transform();
    }
}
