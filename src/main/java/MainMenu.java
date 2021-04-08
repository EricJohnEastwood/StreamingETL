
public class MainMenu {
    public static void main(String[] args) {
        EngineData engine = new EngineData();
//        testing transform class
        Transform.init_source_table("source.xml", engine);
        Transform.init_target_table("target.xml", engine);
        Transform.init_transformation("target.xml", engine);

        System.out.println(engine);


//        Testing SQL connection
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.connectToDB();

        Transform.run_transformation(engine,connectionDB);

    }
}
