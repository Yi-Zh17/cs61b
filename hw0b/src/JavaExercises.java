import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        return new int[]{1, 2, 3, 4, 5, 6};
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        if (customer.equals("Ergun")) {
            return new String[]{"beyti", "pizza", "hamburger", "tea"};
        } else if (customer.equals("Erik")) {
            return new String[]{"sushi", "pasta", "avocado", "coffee"};
        } else {
            return new String[3];
        }
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        return max - min;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // Add new int
        list.add(x);
        // Return list when reaching 1
        if(x == 1) {
            return list;
        }
        // Set up next int
        int next;
        // Conditions
        if(x % 2 == 0) {
            next = x / 2;
        } else {
            next = x * 3 + 1;
        }
        // Recurse
        hailstoneHelper(next, list);
        return list;
    }

}
