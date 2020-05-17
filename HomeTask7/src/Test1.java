public class Test1 {
    @BeforeSuite
    public void init() {
        System.out.println("init");
    }
    @MyTest
    public void sayHello(){
        System.out.println("Hello!");
    }
    @MyTest(priority = 3)
    public void iAmFine(){
        System.out.println("I am fine.");
    }
    @MyTest(priority = 4)
    public void sayHowAreYou(){
        System.out.println("How are you?");
    }
    @MyTest(priority = 2)
    public void thankYou(){
        System.out.println("Thank you!");
    }
    @MyTest(priority = 1)
    public void andYou(){
        System.out.println("And you?");

}
    @AfterSuite
    public void stop() {
        System.out.println("stop");
    }
}
