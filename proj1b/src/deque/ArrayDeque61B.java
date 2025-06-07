package deque;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    // Instance variables
    private int nextFirst;
    private int nextLast;
    private int size;
    private int arr_size;
    private T[] arr;

    // Constructor
    public ArrayDeque61B() {
        this.arr_size = 8;
        this.arr = (T[]) new Object[arr_size];
        nextFirst = 0;
        nextLast = 1;
        this.size = 0;
    }

    // Helper functions
    // Calculate index as we move nextFirst and nextLast forward and backward
    private int moveForward(int index) {
        if (index == 0) {
            return this.arr_size - 1;
        }
        return --index;
    }

    private int moveBackward(int index) {
        if (index == this.arr_size - 1) {
            return 0;
        }
        return ++index;
    }

    @Override
    public void addFirst(T x) {
        // Resize up if full
        if (this.size == this.arr_size) {
            resizeUp();
        }
        arr[nextFirst] = x;
        // Move forward
        nextFirst = moveForward(nextFirst);
        // Increment size
        ++this.size;
    }

    @Override
    public void addLast(T x) {
        // Resize up if full
        if (this.size == this.arr_size) {
            resizeUp();
        }
        arr[nextLast] = x;
        // Move backward
        nextLast = moveBackward(nextLast);
        // Increment size
        ++this.size;
    }

    @Override
    public List<T> toList() {
        // List to store results
        List<T> result = new ArrayList<>();
        int start = moveBackward(nextFirst);
        // Iterate through Alist
        while(start != nextLast) {
            result.add(arr[start]);
            start = moveBackward(start);
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
        // Return null if empty
        if (this.size == 0) {
            return null;
        }
        // Decrement size
        --this.size;
        // Resize down if low usage rate
        if ((double) this.size / this.arr_size < 0.25) {
            resizeDown();
        }
        nextFirst = moveBackward(nextFirst);
        // Store value
        T item = arr[nextFirst];
        // Discard value
        arr[nextFirst] = null;
        return item;
    }

    @Override
    public T removeLast() {
        // Return null if empty
        if (this.size == 0) {
            return null;
        }
        // Decrement size
        --this.size;
        // Resize down if low usage rate
        if ((double) this.size / this.arr_size < 0.25) {
            resizeDown();
        }
        nextLast = moveForward(nextLast);
        // Store value
        T item = arr[nextLast];
        // Discard value
        arr[nextLast] = null;
        return item;
    }

    @Override
    public T get(int index) {
        // Point to the first item
        int first = moveBackward(nextFirst);
        // Calculate corresponding array index
        int act_ind = Math.floorMod(first + index, this.arr_size);
        return this.arr[act_ind];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    // Resize
    private void resizeUp() {
        // Two times the size
        int new_arr_size = this.arr_size * 2;
        // New array
        T[] newArr = (T[]) new Object[new_arr_size];
        // Copy values
        for(int i = 0; i < this.size; ++i) {
            newArr[i] = this.get(i);
        }
        // Assign new first and last
        nextFirst = new_arr_size - 1;
        nextLast = this.size;
        // Assign new array
        this.arr = newArr;
        this.arr_size = new_arr_size;
    }

    private void resizeDown() {
        // Half the size
        int new_arr_size = this.arr_size / 2;
        // New array
        T[] newArr = (T[]) new Object[new_arr_size];
        // Copy value
        for(int i = 0; i < this.size; ++i) {
            newArr[i] = this.get(i);
        }
        // Assign new first and last
        nextFirst = new_arr_size - 1;
        nextLast = this.size;
        // Assign new array
        this.arr = newArr;
        this.arr_size = new_arr_size;
    }

    // Implement iterator
    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = moveBackward(nextFirst);
        }

        public boolean hasNext() {
            return arr[pos] != null;
        }

        public T next() {
            T returnItem = arr[pos];
            pos = moveBackward(pos);
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    // Override equals method
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ArrayDeque61B<?> otherDeque) {
            if (this.size != otherDeque.size) {
                return false;
            }
            for (int i = 0; i < this.size; ++i) {
                if (this.get(i) != otherDeque.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
   }

    //Override toString method
    @Override
    public String toString() {
        return this.toList().toString();
    }
}
