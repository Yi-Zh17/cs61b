import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    // Private variables
    private Node root;
    private int size;
    // Private class Node
    private class Node {
        private final K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.value = value;
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if(root == null) {
            root = new Node(key, value);
            ++size;
        }
        getLocationInsert(root, key, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node ptr = getLocation(root, key);
        return ptr == null ? null : ptr.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return getLocation(root, key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    // Helper functions
    void getLocationInsert(Node ptr, K key, V value) {
        if (key.compareTo(ptr.key) < 0) {
            if(ptr.left != null) {
                getLocationInsert(ptr.left, key, value);
            } else {
                ptr.left = new Node(key, value);
                ++size;
            }
        } else if (key.compareTo(ptr.key) > 0) {
            if(ptr.right != null) {
                getLocationInsert(ptr.right, key, value);
            } else {
                ptr.right = new Node(key, value);
                ++size;
            }
        } else {
            ptr.value = value;
        }
    }

    Node getLocation(Node ptr, K key) {
        if(ptr == null) {
            return null;
        }
        if(key.compareTo(ptr.key) < 0) {
            return getLocation(ptr.left, key);
        } else if(key.compareTo(ptr.key) > 0) {
            return getLocation(ptr.right, key);
        } else {
            return ptr;
        }
    }
}
