import java.util.NoSuchElementException;

public interface IRbt<T extends Comparable<T>> extends SortedCollectionInterface<T>, Iterable<IPokemon> {
    // public RbtROLE() { /* create an empty RBT. */ }
    // public RbtROLE(List<IPokemon>) { /* load the list, one by one, into the RBT.
    // */ }
    // ^ These are the constructors everyone will need to make in their stub
    // (placeholder) implementation of this interface.

    // ^ The second constructor is there to let us avoid repeatedly calling
    // .insert() with different values;
    // here we can just make an RBT from an arraylist instead.

    // ^ We implement Iterable<T> here so that we can use `for(IPokemon pkm : myRbt)
    // { /* ... */ }` in our future code.
    // This will be helpful during tests and when displaying catalog statistics;
    // e.g. we could check the ordering of the RBT after an insertion/deletion.
    // Only the algorithm engineer needs to implement iterator(). (Other roles may
    // use placeholder/stub implementations for this.)

    /**
     * @return the item associated with the search key, if it exists.
     * @throws NoSuchElementException if no element matches the search key
     */
    T get(String searchKey) throws NoSuchElementException;

    /**
     * Clears the red-black tree.
     */
    void clear();
}