import java.lang.reflect.Array;
import java.util.Arrays;

public class FirstTest {
    private static final int FOUR = 4;
    int [] arr2;

    public  int [] changeArray (int [] arr){

        for (int i = arr.length - 1; i >= 0 ; i--) {
            if(arr[i] == FOUR) {
                arr2 = Arrays.copyOfRange(arr, i + 1, arr.length);
                return arr2;
            }


        }

        throw new RuntimeException("В массиве нет цифры 4.");
    }


}
