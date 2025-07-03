package oop.finalexam.t1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FinalTask
 *
 * This program performs a transformation from two input lists: list1 (Integer) and list2 (String)
 * into a third list: list3 (String) using a mapping algorithm:
 *
 * <p><b>Algorithm:</b>
 * 1. Each element of list1 is used as an index to access list2 by adding +1 (i.e., mappedIndex = val + 1).
 * 2. If the mapped index is valid, a new string is created by concatenating list2[mappedIndex] with the original value from list1.
 * 3. The result is added to list3 in order.
 * 4. After building list3, only the element that was added from index 0 of list1 is kept.
 * 5. All other entries (i.e., those created from list1[1], list1[2], ...) are removed from list3.
 *
 * <p><b>Error Handling:</b>
 * - Negative numbers in list1 are skipped and reported.
 * - Values that would result in an out-of-bounds index in list2 are skipped and reported.
 *
 * <p>This logic is based on the animation and test rules provided at the task URL.
 */
public class FinalTask {
    public static void main(String[] args) {
        // Predefined values for testing, works for any input
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
        List<String> list2 = Arrays.asList("zero", "one", "two", "three", "four");

        List<String> list3 = new ArrayList<>();

        // Step 1: Build list3 from valid list1 values
        for (int i = 0; i < list1.size(); i++) {
            int val = list1.get(i);
            int mappedIndex = val + 1;

            if (val < 0) {
                System.out.println("⚠️ Skipping negative value in list1: " + val);
                continue;
            }

            if (mappedIndex < 0 || mappedIndex >= list2.size()) {
                System.out.println("⚠️ Skipping out-of-range mapping for value: " + val + " (mappedIndex = " + mappedIndex + ")");
                continue;
            }

            String result = list2.get(mappedIndex) + val;
            list3.add(result);
        }

        System.out.println("✅ List3 before removal: " + list3);

        // Step 2: Keep only the element added from list1 index 0
        if (list3.size() > 1) {
            String keep = list3.get(0);
            list3.clear();
            list3.add(keep);
        }

        System.out.println("✅ List3 after removal: " + list3);
    }
}
