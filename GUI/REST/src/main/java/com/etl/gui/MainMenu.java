package com.etl.gui;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainMenu {

    public static String readStringFromURL(String requestURL) throws IOException
    {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static void runExecute(EngineData engine, ConnectionDB connectionDB) throws InterruptedException {
        Transform.init_source_table("source.xml", engine);
        //Transform.init_target_table("target.xml", engine);
        Transform.init_transformation("url1_json.xml", engine);
        Extract.set_extract_thread("source.xml",engine, connectionDB);
        TimeUnit.SECONDS.sleep(5);
        Extract.stop_extract_thread();
        System.out.println("Successful Extract");
    }

    public static void stopExecute(){
        Extract.stop_extract_thread();
    }

    public  static void runTransform(EngineData engine, ConnectionDB connectionDB) throws InterruptedException {
        Transform.set_transform_schedule(engine,connectionDB);
        TimeUnit.SECONDS.sleep(10);
        Transform.stop_transform_schedule();
        System.out.println("Successful transform");
    }

    public static void stopTransform(){
        Transform.stop_transform_schedule();
    }

    public static void restart(EngineData engine, ConnectionDB connectionDB) throws InterruptedException {
        stopExecute();
        runExecute(engine, connectionDB);
    }

    public static void main(String[] args) throws IOException, Exception {
        EngineData engine = new EngineData();

        Transform.init_transformers(engine);
        Transform.init_source_table("source.xml", engine);
        Transform.init_target_table("target.xml", engine);
        Transform.init_transformation("url1_json.xml", engine);

        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.connectToDB();

        Extract.set_extract_thread("source.xml",engine, connectionDB);
        TimeUnit.SECONDS.sleep(5);
        Extract.stop_extract_thread();


        Transform.set_transform_schedule(engine,connectionDB);
        TimeUnit.SECONDS.sleep(20);
        Transform.stop_transform_schedule();
//        Transform.run_transformation(engine, connectionDB);

        connectionDB.disconnectFromDB();

    }
}
