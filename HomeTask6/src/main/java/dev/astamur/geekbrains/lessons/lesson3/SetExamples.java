package dev.astamur.geekbrains.lessons.lesson3;

import java.util.*;

public class SetExamples {
    private static final Random rand = new Random(System.currentTimeMillis());


    public static void main(String[] args) {
        hashSets();
        linkedHashSets();
        treeSets();
    }

    private static void hashSets() {
        System.out.print("\n\nHashSet examples\n");

        Set<String> set = new HashSet<>();

        for (int i = 0; i < 15; i++) {
            set.add("Value" + rand.nextInt(10));
        }

        System.out.println("Elements: " + set);
    }

    private static void linkedHashSets() {
        System.out.print("\n\nLinkedHashSet examples\n");

        Set<String> set = new LinkedHashSet<>();

        for (int i = 10; i > 0; i--) {
            set.add("Value" + i);
        }

        System.out.println("Elements: " + set);
    }

    private static void treeSets() {
        System.out.print("\n\nTreeSet examples\n");

        TreeSet<String> set = new TreeSet<>();

        for (int i = 0; i < 1_000_000; i++) {
            set.add("Value" + rand.nextInt(10));
        }

        set.remove("Value5");

        System.out.println("Sorted set: " + set);

        System.out.println(set.ceiling("Value5"));

        Iterator<String> iterator = set.iterator();

        while (iterator.hasNext()) {
            System.out.println("From iterator: " + iterator.next());
            iterator.remove();
        }

        iterator = set.iterator();

        // Nothing
        while (iterator.hasNext()) {
            System.out.println("From iterator 2: " + iterator.next());
        }
    }
}