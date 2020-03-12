package lesson07;


@ForTest
public class SomeClass {

    @BeforeSuite
    public void startTest(){
        System.out.println("The begging of the test");
    }



    @AfterSuite
    public void endTest(){
        System.out.println("The test has been ended");
    }


    @Test
    public void test1(){
        System.out.println(1);
    }

    @Test(priority = 3)
    public static void test2(){
        System.out.println(2);
    }


    public static void test3(){
        System.out.println(3);
    }

    @Test(priority = 15)
    public static void test4(){
        System.out.println(5);
    }



}
