package com.etl.gui;
import java.util.*;
// import java.FileWriter;
import java.io.*;
import java.io.RandomAccessFile;

public class Fillxml{
  public static void xmlwrite(String element,String last_tag,String s_filepath){
    try{
      // going to last line
    RandomAccessFile f = new RandomAccessFile(s_filepath, "rw");
    long length = f.length() - 1;
    byte b;
    do {
    length -= 1;
    f.seek(length);
    b = f.readByte();
    } while(b != 10 && length>0);
    if (length == 0) {
    f.setLength(length);
    } else {
    f.setLength(length + 1);
    }

    // appending to that last line
    f.writeBytes(element);
    f.writeBytes(last_tag);

    ;}
    catch(IOException e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args){
    Dummy d=new Dummy();
    d.setURL("https://free.currconv.com/api/v7/convert?q=USD_INR&amp;compact=ultra&amp;apiKey=d42abbfa86b83587c2cd");
    d.setType("json");
    d.setName("2");

    DummyTransform t=new DummyTransform();
    t.setTransform("asjhvsdakhdsakjjs\nashsabdskbsa\nnsajnsalnsda\naksahsaus");
    insertSourceTransform(t,d,"./src/main/resources/source.xml","./src/main/resources/url1_json.xml");


  }

  public static void insertsource(Dummy d,String s_filepath){
    String s="<source>\n";
    s=s+"<URL>"+d.getURL()+"</URL>\n";
    s=s+"<type>"+d.getType()+"</type>\n";
    s=s+"<queryFrequency>"+d.getQueryFrequency() +"</queryFrequency>\n";
    s=s+"</source>\n";
    System.out.println(s);
    xmlwrite(s,"</sources>",s_filepath);

  }

  public static void insertSourceTransform(DummyTransform t,Dummy d,String s_filepath,String t_filepath ){
    insertsource(d,s_filepath);
    String s="<transformationSteps>\n<type>"+d.getType()+"</type>\n<URL>"+d.getURL()+"</URL>\n";
    s=s+"<transformation-rule>"+"ConcreteTransformer"+"</transformation-rule>\n";
    s=s+"<datatype>"+"simple:single:same"+"</datatype>\n";
    String[] temp=t.getTransform().split("\n");
    for(int i=0;i<temp.length;i++){
      /*
      if(i==0){
        s=s+"<transformation-rule>"+temp[i]+"</transformation-rule>\n";
      }
      else if(i==1){
        s=s+"<datatype>"+temp[i]+"</datatype>\n";
      }
      else{
        s=s+"<transformation>"+temp[i]+ "</transformation>\n";
      }*/
      s=s+"<transformation>"+temp[i]+ "</transformation>\n";
    }
    s=s+"</transformationSteps>\n";
    xmlwrite(s,"</transformations>",t_filepath);
    // s+="</transformationSteps>\n";

    System.out.println(s);


  }



}
