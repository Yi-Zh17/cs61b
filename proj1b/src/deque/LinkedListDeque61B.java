package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    // Node class
    public class Node {
        public Node prev;
        public T item;
        public Node next;

        Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    // Variables
    private final Node sentinel;
    private int size;

    // Constructor
    public LinkedListDeque61B() {
        // Create sentinel node
        this.sentinel = new Node(null, null, null);
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;

    }

    @Override
    public void addFirst(T x) {
        // Add to the next of sentinel
        Node oldFirst = this.sentinel.next;
        this.sentinel.next = new Node(this.sentinel, x, oldFirst);
        // Change the wiring of the oldFirst
        oldFirst.prev = this.sentinel.next;
        // Increment size
        this.size++;
    }

    @Override
    public void addLast(T x) {
        // Add to the prev of the sentinel
        Node oldLast = this.sentinel.prev;
        this.sentinel.prev = new Node(oldLast, x, this.sentinel);
        // Change the wiring of the oldLast
        oldLast.next = this.sentinel.prev;
        // Increment size
        this.size++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node curNode = this.sentinel.next;
        while (curNode != this.sentinel) {
            result.add(curNode.item);
            curNode = curNode.next;
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        T popItem = this.sentinel.next.item;
        Node newFirst = this.sentinel.next.next;
        this.sentinel.next = newFirst;
        newFirst.prev = this.sentinel;
        // Decrement size
        this.size--;
        // Return pop item
        return popItem;
    }

    @Override
    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        T popItem = this.sentinel.prev.item;
        Node newLast = this.sentinel.prev.prev;
        this.sentinel.prev = newLast;
        newLast.next = this.sentinel;
        // Decrement size
        this.size--;
        // Return item
        return popItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        Node curNode = sentinel.next;
        while (index > 0) {
            curNode = curNode.next;
            index--;
        }
        return curNode.item;
    }

    @Override
    public T getRecursive(int index) {
        Node curNode = sentinel.next;
        if (index < 0 || index >= this.size) {
            return null;
        }
        return recurseNode(curNode, index);
    }

    // Helper function
    private T recurseNode(Node curNode, int index) {
        if (index == 0) {
            return curNode.item;
        }
        return recurseNode(curNode.next, index-1);
    }

    // Implement iterator
    private class LinkedListIterator implements Iterator<T> {
        private Node cur;

        public LinkedListIterator() {
            cur = sentinel.next;
        }

        public boolean hasNext() {
            return cur != sentinel;
        }

        public T next() {
            T returnItem = cur.item;
            cur = cur.next;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }


    // Overrides equal method
    public boolean equals(LinkedListDeque61B<T> lld) {
        if (size != lld.size) {
            return false;
        }
        Node cur = sentinel.next;
        Node cur_lld = lld.sentinel.next;
        while(cur != sentinel) {
            if (cur.item != cur_lld.item) {
                return false;
            }
            cur = cur.next;
            cur_lld = cur_lld.next;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkedListDeque61B<?> otherDeque) {
            if (this.size != otherDeque.size) {
                return false;
            }
            Node cur = this.sentinel.next;
            Node cur_other = (Node) otherDeque.sentinel.next;
            while (cur != this.sentinel) {
                if (cur.item != cur_other.item) {
                    return false;
                }
                cur = cur.next;
                cur_other = cur_other.next;
            }
            return true;
        }
        return false;
    }

    // Override toString method
    @Override
    public String toString() {
        return this.toList().toString();
    }
}
