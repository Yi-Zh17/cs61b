import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    /** This tests the isEmpty() method. */
    public  void isEmptyTest() {
        // Empty deque
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        // Non-empty deque
        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
        // Empty deque after removal
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    /** This tests the size method */
    public void sizeTest() {
        // Empty
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);
        // Non-empty
        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(1);
        // More ops
        lld1.addLast(3);
        lld1.addFirst(4);
        lld1.addLast(4);
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    /** This tests the removeFirst and removeLast methods. */
    public void removeTest() {
        // Initialize a deque
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        // Test removeFirst()
        int pop1 = lld1.removeFirst();
        assertThat(pop1).isEqualTo(1);
        assertThat(lld1.toList()).containsExactly(2, 3, 4).inOrder();
        // Test removeLast()
        int pop2 = lld1.removeLast();
        assertThat(pop2).isEqualTo(4);
        assertThat(lld1.toList()).containsExactly(2, 3).inOrder();

        // Empty deque
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        Integer pop3 = lld2.removeFirst();
        Integer pop4 = lld2.removeLast();
        assertThat(pop3).isNull();
        assertThat(pop4).isNull();
    }

    @Test
    /** Tests get method. */
    public void getTest() {
        // Initialize
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Integer int1 = lld1.get(0);
        assertThat(int1).isNull();
        // Out of index
        lld1.addLast(1);
        lld1.addLast(2);
        Integer int2 = lld1.get(3);
        assertThat(int2).isNull();
        // Get
        lld1.addLast(3);
        lld1.addFirst(4);
        int int3 = lld1.get(3);
        int int4 = lld1.get(0);
        assertThat(int3).isEqualTo(3);
        assertThat(int4).isEqualTo(4);
    }

    @Test
    /** Tests getRecursive method. */
    public void getRecursiveTest() {
        // Initialize
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Integer int1 = lld1.getRecursive(0);
        assertThat(int1).isNull();
        // Out of index
        lld1.addLast(1);
        lld1.addLast(2);
        Integer int2 = lld1.getRecursive(3);
        assertThat(int2).isNull();
        // Get
        lld1.addLast(3);
        lld1.addFirst(4);
        int int3 = lld1.getRecursive(3);
        int int4 = lld1.getRecursive(0);
        assertThat(int3).isEqualTo(3);
        assertThat(int4).isEqualTo(4);
    }
}