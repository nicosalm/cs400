import java.util.Iterator;
import java.util.NoSuchElementException;

public class PokemonIteratorAE implements Iterator<IPokemon> {

    private RedBlackTree.Node<PokemonAE> next;

    public PokemonIteratorAE(RedBlackTree.Node<PokemonAE> root) {
        next = root;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public PokemonAE next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more Pokemon!");
        }

        PokemonAE nextPokemon = next.data;
        next = next.getInorderSuccessor();
        return nextPokemon;

    }
}
