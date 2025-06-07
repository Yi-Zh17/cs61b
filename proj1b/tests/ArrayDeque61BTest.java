import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    @DisplayName("Testing get() method")
    public void testGet() {
        Deque61B<Integer> arrd = new ArrayDeque61B<>();
        arrd.addFirst(1);
        arrd.addLast(2);
        arrd.addFirst(3);
        assertThat(arrd.get(0)).isEqualTo(3);
        assertThat(arrd.get(2)).isEqualTo(2);
    }

    @Test
    @DisplayName("Testing isEmpty() method")
    public void testIsEmpty() {
        Deque61B<Integer> arrd = new ArrayDeque61B<>();
        assertThat(arrd.isEmpty()).isTrue();
        arrd.addFirst(1);
        assertThat(arrd.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Testing size() method")
    public void testSize() {
        Deque61B<Integer> arrd = new ArrayDeque61B<>();
        assertThat(arrd.size()).isEqualTo(0);
        arrd.addFirst(1);
        arrd.addLast(2);
        assertThat(arrd.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Testing toList() method")
    public void testToList() {
        Deque61B<Integer> arrd = new ArrayDeque61B<>();
        arrd.addFirst(1);
        arrd.addLast(2);
        arrd.addLast(3);
        arrd.addLast(4);
        arrd.addLast(5);
        List<Integer> ls = arrd.toList();
        assertThat(ls).containsExactly(1, 2, 3, 4, 5).inOrder();
    }

    @Test
    @DisplayName("Testing removeFirst() and removeLast() method")
    public void testRemoveFirstAndLast() {
        Deque61B<Integer> arrd = new ArrayDeque61B<>();
        arrd.addLast(1);
        arrd.addLast(2);
        arrd.addLast(3);
        arrd.addLast(4);
        arrd.addLast(5);
        // Remove First
        int pop1 = arrd.removeFirst();
        assertThat(arrd.toList()).containsExactly(2, 3, 4, 5).inOrder();
        assertThat(pop1).isEqualTo(1);

        // Remove LastS
        int pop2 = arrd.removeLast();
        assertThat(arrd.toList()).containsExactly(2, 3, 4).inOrder();
        assertThat(pop2).isEqualTo(5);
    }

    @Test
    @DisplayName("Testing iterator implementation")
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        ad1.addLast("front"); // after this call we expect: ["front"]
        ad1.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad1).containsExactly("front", "middle", "back");
    }

    @Test
    @DisplayName("Testing equals method")
    public void testEqualDeques61B() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad1).isEqualTo(ad2);
    }

    @Test
    @DisplayName("Testing toString method")
    public void testToString() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");
        assertThat(ad1.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    @DisplayName("Test resize")
    public void testResize() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < 33; ++i) {
            ad1.addFirst(i);
            lst.add(33 - i - 1);
        }
        assertThat(ad1.toList()).isEqualTo(lst);
    }
}
