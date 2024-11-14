public class Child extends Parent{
    int c;
    public Child(int a) {
        super(a); // first line in any constructor
        this.c = a;
        System.out.println("Child created...");
    }

    public int use(String marks) {
        return 4;
    }

    public void use(int marks) {
        //return age;
    }
}
