import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// The Assertions class that we import from here includes assertion methods like assertEquals()
// which we will used in test1000Inserts().
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;



public class BackendDeveloperTests 
{
	
	 /**
     * This test tests the constructor and the load data from file method. First, I call the constructor
     * and then I run loadDataFromFile, and if the constructor is right, then it should be correct. 
     */
    @Test
    public void test1() 
    {
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
   	//Here I instantiate a new Backend object, to use for testing. 	
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
	//Here, I use loadDataFromFile, after setting up a stream to be 
	//able to access the output later.
        try 
    	{   		
    		backendTester.loadDataFromFile("file");
    	}
    	catch(Exception e)
    	{
    	}
	//Here, I check what I expected to see called, and whether or not 
	//it was.
       String expected  = "insert called\n"
       		+ "insert called\n"
       		+ "insert called".trim();

       assertEquals(expected, stream.toString().trim());
    	    
    	
    }
    
    /**
     * This test tests searchFor() method and whether or not it works by checking the printed output.
     */
    @Test
    public void test2() 
    {    	
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
    	//Here, I instantiate a new Backend object to use for testing	
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
	//Here, I use the searchFor() method after setting up the stream 
	//to check what is being printed.
        try 
    	{   		
    		backendTester.searchFor("pokemon");
    	}
    	catch(Exception e)
    	{
    	}
	//Here, I check to make sure the right output is produced the 
	//right amount of times.
       String expected  = "get called".trim();

       assertEquals(expected, stream.toString().trim());
    }
    
    /**
     * This method tests remove pokemon method, and makes sure that the right methods in 
     * the right order are printed.
     */
    @Test
    public void test3() 
    {
        	
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
    	//Here, I instantiate a new Backend object to use for testing
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
	//Here, I call the removePokemon() method to test it after setting 
	//up the stream.
        try 
    	{   		
    		backendTester.removePokemon("name");
    	}
    	catch(Exception e)
    	{
    	}
        //Here, I check to make sure the expected string is produced.
        String expected  = "get called\nremove called".trim();

        assertEquals(expected, stream.toString().trim());
        	    	 
    }
    
    /**
     * this method tests the saveDataToFile() method, and I make sure that the right sequence of methods is called
     * by checking the printed statements.
     */
    @Test
    public void test4() 
    {
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
    	//Here, I instantiate a new Backend object to use for testing
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
        //Here, I call the method loadDatafromFile() to check what will be 
	//printed.
    	try
    	{
    		backendTester.loadDataFromFile("file");
        	backendTester.saveDataToFile("file name");
    	}
    	catch(Exception e)
    	{   		
    	}
    	//Here, I check to make sure my print expectations are met.
    	 String expected  = "insert called\n"
    	 		+ "insert called\n"
    	 		+ "insert called\n"
    	 		+ "iterator called".trim();

         assertEquals(expected, stream.toString().trim());
    	


    }
    
    /**
     * this method test add Pokemon method, and I make sure that the insert method is called by checking
     * what is being printed.
     */
    @Test
    public void test5() 
    {
    	
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
    	//Here, I instantiate a new Backend object to use for testing
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
	//Here, I call the addPokemon() method to make sure the right 
	//output is produced, but only after the stream is set up.
        try 
    	{   		
    		backendTester.addPokemon("name");
    	}
    	catch(Exception e)
    	{
    	}
        //I check the expected output against the real output here.
        String expected  = "insert called".trim();

        assertEquals(expected, stream.toString().trim());
    	
    	
    }
    	
}
