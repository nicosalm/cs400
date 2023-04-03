import java.util.Iterator;
import java.util.NoSuchElementException;

public class RbtBD implements IRbt<IPokemon>
{

	@Override
	public boolean insert(IPokemon data) throws NullPointerException, IllegalArgumentException 
	{
		System.out.println("insert called");
		return true;
	}

	@Override
	public boolean remove(IPokemon data) throws NullPointerException, IllegalArgumentException
	{
		System.out.println("remove called");

		return false;
	}

	@Override
	public boolean contains(IPokemon data)
	{		
		System.out.println("contains called");

		return false;
	}

	@Override
	public int size() 
	{
		return 3;
	}

	@Override
	public boolean isEmpty() 
	{
		return false;
	}

	@Override
	public Iterator<IPokemon> iterator() 
	{
		System.out.println("iterator called");
		return null;
	}

	@Override
	public IPokemon get(String searchKey) throws NoSuchElementException
	{
		System.out.println("get called");
		return null;
	}
	

	@Override
    public void clear()
    {
		return;
    }

}
