public class ChildClass extends FatherClass
{
    public ChildClass()
    {
        System.out.println("ChildClass Create");
    }
    public static void main(String[] args) throws ClassNotFoundException {
//        FatherClass fc = new FatherClass();
//        ChildClass cc = new ChildClass();
        Class c=Class.forName("ChildClass");
    }
}