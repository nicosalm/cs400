import java.util.Iterator;
import java.util.NoSuchElementException;

public class PokemonIteratorAE implements Iterator<IPokemon> {

    private RbtAE.Node<IPokemon> next;

    public PokemonIteratorAE(RbtAE.Node<IPokemon> root) {
        next = root;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public IPokemon next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more Pokemon!");
        }

        IPokemon nextPokemon = next.data;
        next = next.getInorderSuccessor();
        return nextPokemon;

    }
}
