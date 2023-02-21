// --== CS400 Project One File Header ==--
// Name: Nico Salm
// CSL Username: salm
// Email: nbsalm@wisc.edu
// Lecture #: 01 @ 09:30
// Notes to Grader: funny story, dragons are real and they're in my basement

import java.util.NoSuchElementException;

/**
 * This abstract data type represents a collection that maps keys to values,
 * utilizing open addressing with linear probing, in which duplicate keys are
 * not allowed (each key maps to exactly one value).
 * 
 * @author Nico
 * 
 * @param <KeyType>   the key type
 * @param <ValueType> the value type
 * @see this class is based on the MapADT interface
 */

@SuppressWarnings("unchecked")
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    /**
     * This class represents a node in the hashtable. Each node contains a key, a
     * value.
     * 
     * @param <KeyType>   the key type
     * @param <ValueType> the value type
     * @see this is a basic HashNode class
     */
    class Node<KeyType, ValueType> {
        KeyType key;
        ValueType value;
        boolean isDeleted; // whether the node has been deleted. This is important for searching
                           // for a key in the hashtable.

        public Node(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }

    protected Node<KeyType, ValueType>[] table; // the hashtable
    protected int size; // the number of items in the hashtable
    protected int capacity; // the capacity of the hashtable
    protected final double LOAD_FACTOR = 0.70; // the load factor of the hashtable

    /**
     * Creates a new HashtableMap with the specified capacity.
     * 
     * @param capacity the initial capacity of the hashtable
     */
    public HashtableMap(int capacity) {
        this.capacity = capacity;
        this.table = new Node[capacity];
        this.size = 0;
    }

    /**
     * Creates a new HashtableMap with the default capacity. Default capacity is 8.
     */
    public HashtableMap() {
        this(8);
    }

    /**
     * Adds a new key-value pair/mapping to this collection. Throws exception when
     * key is null or duplicate of one already stored. If the load factor is greater
     * than 0.70, the hashtable is resized.
     * 
     * @param key   the key to be added
     * @param value the value to be added
     * @throws IllegalArgumentException when key is null or duplicate of one already
     */
    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        if (containsKey(key))
            throw new IllegalArgumentException("Key already exists");

        // get the index of the key
        int index = Math.abs(key.hashCode()) % capacity;

        // if the index is empty, add the key-value pair to the index
        if (table[index] == null) {
            table[index] = new Node<KeyType, ValueType>(key, value);
            size++;
        }

        // if the index is not empty, check each successive index until an empty index
        // is found
        else {
            Node<KeyType, ValueType> current = table[index];
            for (int i = 0; i < capacity; i++) {
                if (current == null) {
                    table[(index + i) % capacity] = new Node<KeyType, ValueType>(key, value);
                    size++;
                    break;
                }
                current = table[(index + i + 1) % capacity];
            }
        }

        // resize the hashtable if the load factor is greater than 0.70
        if ((double) size / capacity >= LOAD_FACTOR)
            rehash();
    }

    /**
     * Resizes the hashtable to twice its current capacity.
     */
    private void rehash() {
        // create a new hashtable with double the capacity
        Node<KeyType, ValueType>[] newTable = new Node[capacity * 2];

        // copy all the key-value pairs from the old hashtable to the new hashtable
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !table[i].isDeleted)
                newTable[Math.abs(table[i].key.hashCode()) % (capacity * 2)] = table[i];
        }

        // set the new hashtable as the hashtable, and double the capacity
        table = newTable;
        capacity *= 2;

    }

    /**
     * Returns true if this collection contains a mapping for the specified key.
     * 
     * @param key the key to be checked
     * @return true if this collection contains a mapping for the specified key
     */
    @Override
    public boolean containsKey(KeyType key) {
        if (key == null) // key cannot be null
            return false;
        int index = Math.abs(key.hashCode()) % capacity; // get the index of the key
        while (table[index] != null) { // check each index until the key is found
            if (table[index].key.equals(key) && !table[index].isDeleted)
                return true; // return true if the key is found
            index = (index + 1) % capacity;
        }
        return false; // otherwise return false
    }

    /**
     * Returns the value associated with the specified key. Throws exception when
     * key
     * is null or not found.
     * 
     * @param key the key to be checked
     * @throws NoSuchElementException when key is null or not found
     * @return the value associated with the specified key
     */
    public ValueType get(KeyType key) throws NoSuchElementException {
        int index = Math.abs(key.hashCode()) % capacity; // get the index of the key
        ValueType value = null; // the value to be returned
        while (table[index] != null) { // check each index until the key is found
            if (table[index].key.equals(key) && !table[index].isDeleted) {
                value = table[index].value;
                break; // return the value if the key is found
            }
            index = (index + 1) % capacity; // otherwise, check the next index
        }
        if (value == null) // if the value is still null, the key was not found
            throw new NoSuchElementException("Key not found");

        return value; // return the value
    }

    /**
     * Returns the removed value associated with the specified key.
     */
    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {

        if (key == null) // key cannot be null
            throw new NoSuchElementException("Key cannot be null");
        if (!containsKey(key)) // key must be found
            throw new NoSuchElementException("Key not found");

        int index = Math.abs(key.hashCode()) % capacity; // get the index of the key
        ValueType value = null; // the value to be returned

        while (value == null) { // check each index until the key is found
            if (table[index].key.equals(key) && !table[index].isDeleted) {
                value = table[index].value; // if it is
                table[index].isDeleted = true; // mark the node as deleted
                size--; // decrease the size
            }
            index = (index + 1) % capacity; // otherwise, check the next index
        }
        return value; // return the value
    }

    /**
     * Clear each index of the hashtable.
     */
    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Returns the size.
     * 
     * @return the number of key-value pairs in this collection
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Returns the capacity.
     * 
     * @return the capacity of this collection
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

}