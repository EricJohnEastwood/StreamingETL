package com.etl.gui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.etl.gui.Dummy;
import com.etl.gui.DummyTransform;
import com.etl.gui.Employee;
import com.etl.gui.Transform.*;
import com.etl.gui.EngineData;

import static com.etl.gui.Fillxml.insertSourceTransform;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping({ "/ETL" })
public class TestController {

    private List<Dummy> test = createDummy();
    private List<DummyTransform> transformations = createTransform();
    private ConnectionDB connectionDB = conn();
    private EngineData engine = engine();

    private int isInit = init(engine);

    public TestController() throws Exception {
    }

    //private ConnectionDB connectionDB = new ConnectionDB();
    //private connectionDB.connectToDB();

    @GetMapping(produces = "application/json")
    public List<Dummy> firstPage() {
        //System.out.println("HERE");
        return test;
    }

    @DeleteMapping(path = { "/{name}" })
    public Dummy delete(@PathVariable("name") String name) {
        Dummy deleteddummy = null;
        for (Dummy e : test) {
            if (e.getName().equals(name)) {
                test.remove(e);
                //System.out.println(e.getName());
                deleteddummy = e;
                break;
            }
        }
        return deleteddummy;
    }

    @DeleteMapping(path = {"/stop/{name}"})
    public Dummy stopSource(@PathVariable("name") String name){
        Dummy deleteddummy = null;
        for (Dummy e : test) {
            if (e.getName().equals(name)) {
                test.remove(e);
                //System.out.println(e.getName());
                deleteddummy = e;
                break;
            }
        }
        return deleteddummy;
    }

    @PostMapping
    public Dummy create(@RequestBody Dummy user) {
        test.add(user);
        DummyTransform t = new DummyTransform();
        t.setTransform(user.getMapping());
        insertSourceTransform(t,user,"./src/main/resources/source.xml","./src/main/resources/url1_json.xml");
        //System.out.println(employees);
        return user;
    }

    @PostMapping(path = "/transform")
    public DummyTransform transform(@RequestBody DummyTransform transform){
        //System.out.println(transform.getTransform());
        transformations.add(transform);
        return transform;
    }

    @PostMapping(path = "/run")
    public String run(@RequestBody String msg) throws InterruptedException {
        //System.out.println(msg);
        MainMenu.runExecute(engine, connectionDB);
        MainMenu.runTransform(engine, connectionDB);
        return msg;
    }
    @GetMapping(path = "/transform")
    public List<DummyTransform> getTransformations(){
        return transformations;
    }

    public List<DummyTransform> createTransform(){
        List<DummyTransform> test = new ArrayList<>();
        return test;
    }

    public List<Dummy> createDummy(){
        List<Dummy> test = new ArrayList<>();
        //Dummy d = new Dummy();
        //d.setType("json");
        //d.setURL("www.foreignExchange.com");
        //d.setName("ForeignCurrency");
        //test.add(d);
        return test;
    }

    public ConnectionDB conn()
    {
        ConnectionDB connectionDB = new ConnectionDB();
        connectionDB.connectToDB();
        return connectionDB;
    }

    public EngineData engine()
    {
        EngineData engine = new EngineData();
        return engine;
    }

    public int init(EngineData engine) throws Exception {
        Transform.init_transformers(engine);
        Transform.init_source_table("source.xml", engine);
        Transform.init_target_table("target.xml", engine);
        Transform.init_transformation("url1_json.xml", engine);
        return 1;
    }

    private static List<Employee> createList() {
        List<Employee> tempEmployees = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setName("emp1");
        emp1.setDesignation("manager");
        emp1.setEmpId("1");
        emp1.setSalary(3000);

        Employee emp2 = new Employee();
        emp2.setName("emp2");
        emp2.setDesignation("developer");
        emp2.setEmpId("2");
        emp2.setSalary(3000);
        tempEmployees.add(emp1);
        tempEmployees.add(emp2);
        return tempEmployees;
    }

}
