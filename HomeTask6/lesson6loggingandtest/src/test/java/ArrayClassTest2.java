import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayClassTest2 {
    SecondTest secondTest;

    @Before
    public void init(){
        secondTest = new SecondTest();
    }
    @Test
    public void arrTest(){
        int [] arr = {1, 4, 4, 1, 4, 1};
        Assert.assertTrue(secondTest.secondTest(arr));
    }
    @Test
    public void arrTest2(){
        int [] arr = {1, 4, 4, 1, 4, 3};
        Assert.assertFalse(secondTest.secondTest(arr));
    }
    @Test
    public void arrTest3(){
        int [] arr = {1, 2, 7, 1, 6, 5};
        Assert.assertFalse(secondTest.secondTest(arr));
    }

}
