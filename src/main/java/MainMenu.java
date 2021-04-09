import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

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

    public static void main(String[] args) throws IOException {
        EngineData engine = new EngineData();
//        testing transform class
        Transform.init_source_table("source.xml", engine);
        Transform.init_target_table("target.xml", engine);
        Transform.init_transformation("target.xml", engine);

        System.out.println(engine);


//        Testing SQL connection
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.connectToDB();

//        Transform.run_transformation(engine,connectionDB);

        String requestURL = "https://free.currconv.com/api/v7/convert?q=USD_INR&compact=ultra&apiKey=c0dbece0e1a955a43e02";
        System.out.println(MainMenu.readStringFromURL(requestURL));

        String json = "{\"amount\": \"1.0000\", \"base_currency_code\": \"EUR\", \"base_currency_name\": \"Euro\", \"rates\":{ \"GBP\":{ \"currency_name\":\"Pound sterling\", \"rate\": \"0.8541\", \"rate_for_amount\":\"0.8541\" } }, \"status\": \"success\" , \"updated_date\": \"2020-01-20\"  }";
        System.out.println(json);

        ObjectMapper mapper = new ObjectMapper();

            // convert JSON string to Map
//        Map<String, String> map = mapper.readValue(MainMenu.readStringFromURL(requestURL), Map.class);
        Map<String, String> map = mapper.readValue(json, Map.class);

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

            System.out.println(map);


            connectionDB.disconnectFromDB();

    }
}
