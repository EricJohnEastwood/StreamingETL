import java.util.ArrayList;
import java.util.Objects;


public class Transformations {
    private String url;
    private String data_type;
    private ArrayList<String> transformationTypesModule;
    private TransformationRule1 transformation_rule;

    public Transformations() {
        this.url = "";
        this.data_type = "";
        this.transformationTypesModule = new ArrayList<String>();
        this.transformation_rule = new TransformationRule1();
    }

    public  Transformations(String url, String data_type, ArrayList<String> transformationTypesModule) {
        this.url = url;
        this.data_type = data_type;
        this.transformationTypesModule = transformationTypesModule;
        this.transformation_rule = new TransformationRule1();
    }

    public String getUrl() {
        return this.url;
    }

    public String getData_type() {
        return this.data_type;
    }

    public Integer getSize() {
        return this.transformationTypesModule.size();
    }

    public String getTransformationTypesModule(Integer i) {
        return this.transformationTypesModule.get(i);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transformations that = (Transformations) o;
        return url.equals(that.url) && data_type.equals(that.data_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, data_type);
    }

    @Override
    public String toString() {
        return "Transformations{" +
                "url='" + url + '\'' +
                ", data_type='" + data_type + '\'' +
                ", transformationTypesModule=" + transformationTypesModule +
                ", transformation_rule=" + transformation_rule +
                '}';
    }

    public void run(SourceRow sourceRow, TargetRow targetRow){
//        targetRow.setRow("Foreign_Exchange_Id", "2");
//        targetRow.setRow("Currency_Format", "CAD");
//        targetRow.setRow("Base_Format", "USD");
//        targetRow.setRow("Currency_Value_Multiplier", "1.5");
//        targetRow.setRow("Country", "Canada");
        for(String t: transformationTypesModule) {
            transformation_rule.transform_json(sourceRow, targetRow, t);
        }
    }
}
