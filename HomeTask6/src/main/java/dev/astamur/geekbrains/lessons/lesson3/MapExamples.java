package dev.astamur.geekbrains.lessons.lesson3;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapExamples {
    public static void main(String[] args) {
        hashMaps();
        linkedHashMaps();
        treeMaps();
    }

    private static void hashMaps() {
        System.out.print("\n\nHashMap examples\n");

        populateMap(new HashMap<>());
    }

    private static void linkedHashMaps() {
        System.out.print("\n\nLinkedHashMap examples\n");

        populateMap(new LinkedHashMap<>());
    }

    private static void treeMaps() {
        System.out.print("\n\nTreeMap examples\n");

        TreeMap<Integer, String> map = new TreeMap<>((o1, o2) -> o2 - o1);
        map.put(10, "Value10");
        map.put(4, "Value4");
        map.put(1, "Value1");
        map.put(9, "Value9");
        map.put(6, "Value6");
        map.put(2, "Value2");
        map.put(8, "Value8");
        map.put(7, "Value7");
        map.put(3, "Value3");
        //map.put(5, "Value5");

        System.out.println("Elements: " + map);

        System.out.println("Sub map: " + map.subMap(8, true, 3, true));

        System.out.println("Sub map: " + map.headMap(5, true));

        System.out.println("Ceiling for key: " + map.floorKey(5));
    }

    private static void populateMap(Map<String, String> map) {
        for (int i = 1; i < 13; i++) {
            map.put("Key" + i, "Value" + i);
            map.put("Key" + i, "Value" + i);
        }

        System.out.printf("Size: %d. Capacity: %d. Load Factor: %f. Elements: %s.\n",
                map.size(), getCapacity(map), getLoadFactor(map), map);

        for (int i = 13; i < 26; i++) {
            map.put("Key" + i, "Value" + i);
            map.put("Key" + i, "Value" + i);
        }
        System.out.printf("Size: %d. Capacity: %d. Load Factor: %f. Elements: %s.\n",
                map.size(), getCapacity(map), getLoadFactor(map), map);
    }

    private static <K, V> float getLoadFactor(Map<K, V> map) {
        try {
            Field loadFactor = HashMap.class.getDeclaredField("loadFactor");
            loadFactor.setAccessible(true);
            return (float) loadFactor.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Can't fetch map's loadFactor");
        }
    }

    private static <K, V> int getCapacity(Map<K, V> map) {
        try {
            Field table = HashMap.class.getDeclaredField("table");
            table.setAccessible(true);
            return ((Object[]) table.get(map)).length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Can't fetch map's capacity");
        }
    }
}
