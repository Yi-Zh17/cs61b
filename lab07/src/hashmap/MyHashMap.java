package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

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
        // Check loadFactor & resize
        if((double) N / M > loadFactor) {
            // Create new buckets and rehash
            M *= 2;
            Collection<Node>[] newBuckets = new Collection[M];
            for(int i = 0; i < M/2; ++i) {
                if(buckets[i] != null) {
                    Collection<Node> nodes = buckets[i];
                    for (Node n : nodes) {
                        int ind = getIndex(n.key);
                        if (newBuckets[ind] == null) {
                            newBuckets[ind] = createBucket();
                        }
                        newBuckets[ind].add(new Node(n.key, n.value));
                    }
                }
            }
            buckets = newBuckets;
        }

        // Get index
        int ind = getIndex(key);
        // Insert
        if(buckets[ind] == null) {
            buckets[ind] = createBucket();
        }
        for(Node n : buckets[ind]) {
            if(n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        // If new key
        buckets[ind].add(new Node(key, value));
        // Increment N
        ++N;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        // Index
        int ind = getIndex(key);
        // If bucket is empty return null
        if(buckets[ind] == null) return null;
        // Find key
        for(Node n : buckets[ind]) {
            if(n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        // Index
        int ind = getIndex(key);
        if(buckets[ind] == null) return false;
        // Find key
        for(Node n : buckets[ind]) {
            if(n.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return N;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        buckets = new Collection[initialCapacity];
        N = 0;
        M = initialCapacity;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
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

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    int N; // Number of elements
    int M; // Number of buckets
    int initialCapacity;
    double loadFactor;

    /** Constructors */
    public MyHashMap() {
        initialCapacity = 16;
        loadFactor = 0.75;
        N = 0;
        M = initialCapacity;

        buckets = new Collection[initialCapacity];
    }

    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = 0.75;
        N = 0;
        M = initialCapacity;

        buckets = new Collection[initialCapacity];
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        N = 0;
        M = initialCapacity;

        buckets = new Collection[initialCapacity];
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    // Helper method
    private int getIndex(K key) {
        int hash = key.hashCode();
        return Math.floorMod(hash, M);
    }
}
