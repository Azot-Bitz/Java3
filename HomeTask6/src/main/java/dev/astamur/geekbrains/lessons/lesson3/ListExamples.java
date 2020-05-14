package dev.astamur.geekbrains.lessons.lesson3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListExamples {
    public static void main(String[] args) {
        fixedArrays();
        arrayLists();
        linkedLists();
    }

    private static void fixedArrays() {
        System.out.print("\n\nArrays examples\n");

        int[] arr = {1, 2, 3, 4};

        System.out.println(Arrays.toString(arr));

        int[] newArr = new int[10];

        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;

        for (int i = 5; i < 11; i++) {
            arr[i - 1] = i;
        }

        System.out.println(Arrays.toString(arr));
    }

    private static void arrayLists() {
        System.out.print("\n\nArrayList examples\n");

        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.printf("Elements: %s. Size: %d. Capacity: %d.\n", numbers, numbers.size(), getCapacity(numbers));

        for (int i = 1; i < 11; i++) {
            numbers.add(i);
        }

        System.out.printf("Elements: %s. Size: %d. Capacity: %d.\n", numbers, numbers.size(), getCapacity(numbers));

        for (int i = 11; i < 16; i++) {
            numbers.add(i);
        }

        System.out.printf("Elements: %s. Size: %d. Capacity: %d.\n", numbers, numbers.size(), getCapacity(numbers));

        numbers.add(10, 999);
        System.out.printf("Elements: %s. Size: %d. Capacity: %d.\n", numbers, numbers.size(), getCapacity(numbers));
    }

    private static void linkedLists() {
        System.out.print("\n\nLinkedList examples\n");

        LinkedList<Integer> numbers = new LinkedList<>();

        for (int i = 1; i < 11; i++) {
            numbers.offer(i);
        }

        System.out.printf("Elements: %s. First: %d. Last: %d.\n", numbers, numbers.getFirst(), numbers.getLast());

        numbers.remove(5);

        System.out.printf("Elements: %s. First: %d. Last: %d.\n", numbers, numbers.getFirst(), numbers.getLast());
    }

    private static <T> int getCapacity(List<T> list) {
        try {
            Field data = ArrayList.class.getDeclaredField("elementData");
            data.setAccessible(true);
            return ((Object[]) data.get(list)).length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Can't fetch list's data");
        }
    }
}
