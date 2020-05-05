import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {
    static Cat[] arr = new Cat[3];
    static int arr2 [] = new int[3];

    public static void arrToArrayList() {
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = i;
        }
        System.out.println(Arrays.toString(arr2));
        System.out.println(arr2.getClass().getSimpleName());

        ArrayList<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < arr2.length; i++) {
            arrayList.add(arr2[i]);
        }
        System.out.println(arrayList);
        System.out.println(arrayList.getClass().getSimpleName());
    }


    public static void main(String[] args) {
        Box<Orange> or = new Box<>();
        Box<Orange> or1 = new Box<>();
        Box<Apple> ap = new Box<>();
        Box<Apple> ap1 = new Box<>();
        or.add(new Orange(),7);
        or1.add(new Orange(),14);
        ap.add(new Apple(),9);
        ap1.add(new Apple(),21);
        System.out.println("Box 1: "+or.getWeight());
        System.out.println("Box 2: "+or1.getWeight());
        System.out.println("Box 3: "+ap.getWeight());
        System.out.println("Box 4: "+ap1.getWeight());
        System.out.println("Box 1 equals box 3: "+or.compare(ap));
        System.out.println("Box 2 equals box 4: "+or1.compare(ap1));
        or.pour(or1);
        ap.pour(ap1);
        System.out.println("Box 1: "+or.getWeight());
        System.out.println("Box 2: "+or1.getWeight());
        System.out.println("Box 3: "+ap.getWeight());
        System.out.println("Box 4: "+ap1.getWeight());
        arrToArrayList();

       arr[0] = new Cat("Vas'ka", "black", 3);
       arr[1] = new Cat("Dol'ka", "white", 7);
       arr[2] = new Cat("Mas'ka", "brown", 5);

        for (int i = 0; i< arr.length; i++){
            System.out.println(arr[i]);
        }
        Arrays.sort(arr);
        for (int i = 0; i< arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
