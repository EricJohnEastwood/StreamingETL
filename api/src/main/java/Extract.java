import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;
import java.util.Date;

class PeriodicExtract extends TimerTask{
    private boolean run_thread;
    public final Element eElement;
    private final EngineData engine;
    private final ConnectionDB connectionDB;

    public PeriodicExtract(Element e, EngineData engine, ConnectionDB connectionDB){
        this.run_thread = true;
        this.eElement = e;
        this.engine = engine;
        this.connectionDB = connectionDB;
        System.out.println("Construction Completed");
    }
    @Override
    public void run() {
        if(this.run_thread) {
            System.out.println("ThreadExecution");
            Extract.data_dump(this.eElement, this.engine, this.connectionDB);
        }
    }

    public void stop() {
        this.run_thread = false;
    }

}

public class Extract {
    private static ArrayList<PeriodicExtract> pe_threads;
    private static Timer timer;

    public static String readStringFromURL(String requestURL) throws IOException
    {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static String get_URl_json_data(String link) throws IOException {
        URL json_data=new URL(link);
        BufferedReader in=new BufferedReader(new InputStreamReader(json_data.openStream()));
        String line_input="";
        while(true){
            String holder=in.readLine();
            if(holder==null){
                break;
            }
            line_input=line_input+holder;

        }
        line_input=line_input.replace("\n","");
        return line_input;

    }

    public static String get_URl_csv_data(String link) throws IOException {
        URL json_data=new URL(link);
        BufferedReader in=new BufferedReader(new InputStreamReader(json_data.openStream()));
        String line_input="";

//        ACTUAL CODE TO RUN TO GET COMPLETE CSV FILE
        while(true){
            String holder=in.readLine();
//            holder=holder+"\n";
//            holder=holder.replace("\n","");
            if(holder==null){
                break;
            }
            line_input=line_input+holder+"\n";
        }
        return line_input;

    }

    public static void data_dump(Element eElement, EngineData engine, ConnectionDB connectionDB){
        try{

            String[] entry_details = new String[5];
            Date date=Calendar.getInstance().getTime();
            DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date_time = date_format.format(date);


            entry_details[1] = eElement.getElementsByTagName("type").item(0).getTextContent();
            entry_details[2] = eElement.getElementsByTagName("URL").item(0).getTextContent();
            entry_details[3] = date_time;
            entry_details[0] = Long.toString(System.nanoTime());
//
//            if(entry_details[1].equals("csv")){
//                entry_details[4] = get_URl_csv_data(entry_details[2]);
//            }
//            else if(entry_details[1].equals("json")){
//                entry_details[4] = get_URl_json_data(entry_details[2]);
//            }

            entry_details[4] = Extract.readStringFromURL(entry_details[2]);

            System.out.println("The data downloaded from URL is the following:\n"+entry_details[4]);

            String insertCommand = GenInstructionDB.insert_instruction(engine.getSourceTable().getTableName(),engine.getSourceTable().getColumnName().toArray(new String[0]), entry_details);
            connectionDB.insertIntoTable(insertCommand);

        }catch(Exception e){ System.out.println(e);}
    }


    public static void set_extract_thread(String filename, EngineData engine, ConnectionDB connectionDB) {
        try{
            Extract.pe_threads = new ArrayList<PeriodicExtract>();

            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document document=builder.parse(new File("./src/main/resources/" + filename));

            NodeList nodes_list = document.getElementsByTagName("source");
            // source is the name of the element
            System.out.println("The total number of data sources is: "+nodes_list.getLength());


            Extract.timer = new Timer();

            for(int i=0;i<nodes_list.getLength();i++){
                Node nod = nodes_list.item(i);
                if (nod.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nod;
                    String interval = eElement.getElementsByTagName("queryFrequency").item(0).getTextContent();
                    float f_inter = Float.parseFloat(interval);
                    f_inter *= 1000;
                    int i_inter = (int) f_inter;



                    PeriodicExtract pe = new PeriodicExtract(eElement, engine, connectionDB);
                    timer.schedule(pe, 0, i_inter);
                    Extract.pe_threads.add(pe);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    public static void stop_extract_thread() {
        System.out.println("Waking up and closing threads");
        for (PeriodicExtract pe_thread : Extract.pe_threads) {
            pe_thread.cancel();
        }
        Extract.timer.cancel();
        Extract.timer.purge();
    }
}


