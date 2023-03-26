import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogReaderAE implements ICatalogReader {

    @Override
    public List<IPokemon> readFromFile(String filename) {
        List<IPokemon> pokemonList = new ArrayList<IPokemon>();
        pokemonList.add(new PokemonAE("Bulbasaur"));
        pokemonList.add(new PokemonAE("Ivysaur"));
        pokemonList.add(new PokemonAE("Venusaur"));

        return pokemonList;
    }

    @Override
    public void writeToFile(String filename, List<IPokemon> listToStore)
            throws FileNotFoundException, SecurityException, IOException {
        return;
    }

    @Override
    public IPokemon readCsvLineIntoPokemon(String pokemonCsvFmtStr) throws IllegalArgumentException, IOException {
        return new PokemonAE("Pokemon");
    }

}
