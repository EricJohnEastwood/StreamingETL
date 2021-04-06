
public class MainMenu {
    public static void main(String[] args) {
        EngineData engine = new EngineData();
//        testing transform class
        TransformEngine.init_source_table("source.xml", engine);
        TransformEngine.init_target_table("target.xml", engine);
        TransformEngine.init_transformation("url1_json.xml", engine);

        System.out.println(engine);


//        Testing SQL connection
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.connectToDB();

        TransformEngine.run_transformation(engine,connectionDB);

    }
}
