import java.util.ArrayList;


public class Transformations {
    private String url;
    private String data_type;
    private ArrayList<String> transformationTypesModule;

    public Transformations() {
        this.url = "";
        this.data_type = "";
        this.transformationTypesModule = new ArrayList<String>();
    }

    public  Transformations(String url, String data_type, ArrayList<String> transformationTypesModule) {
        this.url = url;
        this.data_type = data_type;
        this.transformationTypesModule = transformationTypesModule;
    }

    public String getUrl() {
        return this.url;
    }

    public String getData_type() {
        return this.data_type;
    }

    public ArrayList<String> getTransformationTypesModule() {
        return this.transformationTypesModule;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public void setTransformationTypesModule(ArrayList<String> transformationTypesModule) {
        this.transformationTypesModule = transformationTypesModule;
    }

    public String getNextTransformation() {
        return this.transformationTypesModule.remove(0);
    }
}
