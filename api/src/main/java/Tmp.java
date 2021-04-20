public class Tmp {
    public static void main(String[] args) {
        ConcreteTransformer c = new ConcreteTransformer();
        String s = c.getClass().getName();
        System.out.println(s);
    }
}