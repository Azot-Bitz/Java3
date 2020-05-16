import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayClassTest {
    FirstTest firstTest;
    @Before
    public void init(){
        firstTest = new FirstTest();
    }
    @Test
    public void testArr(){
        int [] arr = {1, 2, 2, 4, 3, 4, 5, 4, 1, 7};
        int [] arr2 = {1, 7};
        Assert.assertArrayEquals(arr2, firstTest.changeArray(arr));
    }
    @Test
    public void testArr2(){
        int [] arr = {1, 2, 4, 3, 7, 1, 7};
        int [] arr2 = {3, 7, 1, 7};
        Assert.assertArrayEquals(arr2, firstTest.changeArray(arr));
    }

    @Test
    public void testArr3(){
        int [] arr = {4, 5, 6, 8, 3, 7, 1, 7};
        int [] arr2 = {5, 6, 8, 3, 7, 1, 7};
        Assert.assertArrayEquals(arr2, firstTest.changeArray(arr));
    }

    @Test(expected = RuntimeException.class)
    public void testArr4(){
        int [] arr = {1, 2, 8, 3, 7, 1, 7};
        int [] arr2 = {3, 7, 1, 7};
        Assert.assertArrayEquals(arr2, firstTest.changeArray(arr));
    }
}
