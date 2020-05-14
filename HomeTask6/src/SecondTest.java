public class SecondTest {
    public boolean secondTest(int [] arr){
        boolean one = false;
        boolean four = false;
        boolean notOneOrFour = false;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 1){
                one = true;
            }else if(arr[i] == 4){
                four= true;
            }else{
                notOneOrFour = true;
            }

        }
        return one & four & !notOneOrFour;
    }
}
