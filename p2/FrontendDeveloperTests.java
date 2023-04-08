import static org.junit.Assert.fail;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FrontendDeveloperTests {
    
    // Tester for loadDataCommand()
    @Test
    void testloadDataCommand() {
        TextUITester tester = new TextUITester("L\n~/data/catalog1.csv\nQ\n");

        Scanner scanner = new Scanner(System.in);
        BackendFD backend = new BackendFD();
		FrontendFD frontend = new FrontendFD(scanner, backend);
		frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Load Data Command
        if( !(output.startsWith("=== Welcome to Pokeylog™, Trainer! ===") && (output.contains("CSV loaded!"))) ) {
            fail("Error: testloadDataCommand failed.");
        }
    }

    // Tester for addCommand() and deleteCommand()
    @Test
    void testAddAndDeleteCommands() {
        TextUITester tester = new TextUITester("A\nCharizard\nD\nCharizard\nQ\n");

        Scanner scanner = new Scanner(System.in);
        BackendFD backend = new BackendFD();
		FrontendFD frontend = new FrontendFD(scanner, backend);
		frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Add Command
        if( !(output.startsWith("=== Welcome to Pokeylog™, Trainer! ===") && (output.contains("Pokemon added!"))) ) {
            fail("Error: testAddAndDeleteCommands failed.");
        }
        // Test Delete Command
        if(!(output.contains("Pokemon deleted!"))) {
            fail("Error: testAddAndDeleteCommands failed.");
        }
    }

    // Tester for addCommand() and deleteCommand()
    @Test
    void testSearchCommand() {
        TextUITester tester = new TextUITester("S\nCharizard\nQ\n");

        Scanner scanner = new Scanner(System.in);
        BackendFD backend = new BackendFD();
		FrontendFD frontend = new FrontendFD(scanner, backend);
		frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Search Command
        String searchOutput = "=> NAME (id: 0) <=\n> Type: Normal, Normal\n> ATK: 0\n> DEF: 0\n> SPA: 0\n> SPD: 0\n> SPE: 0\n> Abilities: NAME, NAME, NAME\n";
        if(!(output.contains(searchOutput))) {
            fail("Error: testSearchCommand failed.");
        }
    }

    // Tester for saveDataCommand()
    @Test
    void testSaveDataCommand() {
        TextUITester tester = new TextUITester("E\nFileName\nQ\n");

        Scanner scanner = new Scanner(System.in);
        BackendFD backend = new BackendFD();
		FrontendFD frontend = new FrontendFD(scanner, backend);
		frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Save Data Command
        if( !(output.startsWith("=== Welcome to Pokeylog™, Trainer! ===") && (output.contains("Exported to FileName!"))) ) {
            fail("Error: testSaveDataCommand failed.");
        }
    }

    // Tester for displayCatalogStatsCommand()
    @Test
    void testDisplayCatalogStatsCommand() {
        TextUITester tester = new TextUITester("C\nQ\n");

        Scanner scanner = new Scanner(System.in);
        BackendFD backend = new BackendFD();
		FrontendFD frontend = new FrontendFD(scanner, backend);
		frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Display Catalog Stats Command
        if( !(output.startsWith("=== Welcome to Pokeylog™, Trainer! ===") && (output.contains("Success!"))) ) {
            fail("Error: testDisplayCatalogStatsCommand failed.");
        }
    }

    // Integration Tests
    // FrontendDeveloperIntegration test addPokemon Method
    @Test
    void FDIntegrationTestAddPokemon() {
        TextUITester tester = new TextUITester("A\nCharizard\nQ\n");

        Scanner scanner = new Scanner(System.in);
        // setup Backend
        IRbt<IPokemon> rbt = new RbtBD();
        ICatalogReader reader = new CatalogReaderBD();
        BackendBD backend = new BackendBD(rbt, reader);
        // setup Frontend
        FrontendFD frontend = new FrontendFD(scanner, backend);
        frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Add Pokemon Output
        if( !(output.startsWith("=== Welcome to Pokeylog™, Trainer! ===") && (output.contains("insert called"))
            && (output.contains("Pokemon added!")) )) {
            fail("Error: FDIntegrationTestAddPokemon failed.");
        }
    }

    // FrontendDeveloperIntegration test removePokemon method
    @Test
    void FDIntegrationTestRemovePokemon() {
        TextUITester tester = new TextUITester("D\nCharizard\nQ\n");

        Scanner scanner = new Scanner(System.in);
        // setup Backend
        IRbt<IPokemon> rbt = new RbtBD();
        ICatalogReader reader = new CatalogReaderBD();
        BackendBD backend = new BackendBD(rbt, reader);
        // setup Frontend
        FrontendFD frontend = new FrontendFD(scanner, backend);
        frontend.runCommandLoop();
        scanner.close();

        String output = tester.checkOutput();
        // Test Remove Pokemon Output
        if( !(output.startsWith("=== Welcome to Pokeylog™, Trainer! ===") && (output.contains("get called\nremove called"))
            && (output.contains("Pokemon deleted!")) )) {
            fail("Error: FDIntegrationTestRemovePokemon failed.");
        }
    }

    // CodeReviewOfBackendDeveloper displayCatalogStats Method
    @Test
    void CodeReviewOfBackendDeveloperTest1() {
        // setup Backend
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));

        // call displayCatalogStats method 
        try {   		
    		backendTester.displayCatalogStats();
    	} catch(Exception e) {}

        // correct output since displayCatalogStats calls IRBT.iterator()
        String expected  = "iterator called".trim();
        // check the expected output against real output 
        assertEquals(expected, stream.toString().trim());
    }

    // CodeReviewOfBackendDeveloper loadData, searchFor, saveDataToFile methods
    @Test
    void CodeReviewOfBackendDeveloperTest2() {
         // setup Backend
    	IRbt<IPokemon> rbt = new RbtBD();
    	ICatalogReader reader = new CatalogReaderBD();
    	BackendBD backendTester = new BackendBD(rbt, reader);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));

        // call displayCatalogStats method 
        try {   		
            backendTester.loadDataFromFile("file");
    		backendTester.searchFor("name");
            backendTester.saveDataToFile("file name");
    	} catch(Exception e) {}

        // correct output in correct order
        String expected  = ("insert called\n" + "insert called\n" + "insert called\n" 
                            + "get called\n" + "iterator called" ).trim();
        // check the expected output against real output 
        assertEquals(expected, stream.toString().trim());
    }
}
