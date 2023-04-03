import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CatalogReaderBD implements ICatalogReader
{

	@Override
	public List<IPokemon> readFromFile(String filename)
			throws FileNotFoundException, IllegalArgumentException, SecurityException, IOException 
	{		
		IPokemon pokemon = new PokemonBD();
		IPokemon pokemon2 = new PokemonBD();
		IPokemon pokemon3 = new PokemonBD();
		List<IPokemon> list=Arrays.asList(pokemon, pokemon2, pokemon3);
		return list;
	}

	@Override
	public void writeToFile(String filename, List<IPokemon> listToStore)
			throws FileNotFoundException, SecurityException, IOException 
	{
		System.out.println("file written to");
		
	}

	@Override
	public IPokemon readCsvLineIntoPokemon(String pokemonCsvFmtStr) throws IllegalArgumentException, IOException 
	{
		return new PokemonBD();
	}

}
