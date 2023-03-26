import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public class BackendAE implements IBackend {

    IRbt<IPokemon> rbt;
    ICatalogReader reader;

    public BackendAE(IRbt<IPokemon> rbt, ICatalogReader reader) {
        this.rbt = rbt;
        this.reader = reader;
    }

    @Override
    public void loadDataFromFile(String filename)
            throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException {
        return;
    }

    @Override
    public IPokemon searchFor(String nameToSearch) throws NoSuchElementException {
        return new PokemonAE("Pokemon");
    }

    @Override
    public void addPokemon(String pokemonCsvFmtStr) throws IllegalArgumentException, IOException {
        return;
    }

    @Override
    public void removePokemon(String pokemonName) throws IllegalArgumentException {
        return;
    }

    @Override
    public void saveDataToFile(String filename) throws FileNotFoundException, SecurityException, IOException {
        return;
    }

    @Override
    public String displayCatalogStats() {
        return "<Stats go here>";
    }

}
