
// noSuchElementException
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the RedBlackTree class.
 * In this class we test:
 * 1. The remove method (three parts)
 * --> we also define a helper method to validate the RBT to condense the code
 * 2. The get method
 * 3. The iterator functionality
 */
public class AlgorithmEngineerTests {

    /**
     * Method to validate the RBT by testing the properties of a RBT.
     * 
     * @param tree the RBT to validate
     * @return true if the RBT is valid, false otherwise
     */
    public boolean validateRBT(RbtAE tree) {
        if (tree.root == null) { // empty tree
            return true;
        }
        if (tree.root.blackHeight != 1) { // root is black
            return false;
        }


        int left = numBlack(tree.root.context[1]); // check black height of left subtree
        int right = numBlack(tree.root.context[2]); // check black height of right subtree

        if (left != right || left == -1) { // check if left and right subtrees have same black height
            return false;
        }

        return true; // if all tests pass, return true
    }

    /**
     * Returns the number of black nodes in the path from the root to the given
     * node. Helper method for validateRBT.
     * 
     * @param node the node to check
     * @return the number of black nodes in the path from the root to the given
     */
    private int numBlack(RbtAE.Node<IPokemon> node) {
        if (node == null) { // empty node
            return 0;
        }

        int left = numBlack(node.context[1]); // check black height of left subtree
        int right = numBlack(node.context[2]); // check black height of right subtree

        if (left != right || left == -1) { // check if left and right subtrees have same black height
            return -1;
        }

        if (node.blackHeight == 1) { // if node is black, add 1 to black height
            return left + 1;
        } else {
            return left; // if node is red, return black height
        }
    }


    /**
     * Tests remove method for:
     * case 1: deleted node is root.
     * case 2: sibling is red
     * 
     * @see RbtAE#remove(IPokemon) and
     *      RbtAE#fixRedBlackTreePropertiesAfterDelete(IPokemon)
     */
    @Test
    public void testRBTRemove1() {
        // case 1: deleted node is root
    {
            RbtAE tree = new RbtAE(); // set up tree
            PokemonAE pkm = new PokemonAE("Charmander");
            tree.insert(pkm); // insert Pokemon
            // check that the tree is not empty
            assertEquals(1, tree.size);
            assertTrue(!tree.isEmpty());

            tree.remove(pkm); // remove Pokemon

            // check that the tree is empty
            assertEquals(0, tree.size);
            assertTrue(tree.isEmpty());
            assertTrue(validateRBT(tree));
            assertEquals("[  ]", tree.toLevelOrderString());
        }

        // case 2: sibling is red
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("b"));
            PokemonAE pkm = new PokemonAE("a");
            tree.insert(pkm);
            tree.insert(new PokemonAE("d"));
            tree.insert(new PokemonAE("c"));
            tree.insert(new PokemonAE("e"));

            tree.root.blackHeight = 1; // set blackheights just to make sure validate() method
                                       // works just this once
            tree.root.context[1].blackHeight = 1;
            tree.root.context[2].blackHeight = 0;
            tree.root.context[2].context[1].blackHeight = 1;
            tree.root.context[2].context[2].blackHeight = 1;

            // check tree size
            assertEquals(5, tree.size);

            tree.remove(pkm); // remove Pokemon

            // check that the tree size is correct
            assertEquals(4, tree.size);

            // validate the tree and check that the tree is correct
            assertTrue(validateRBT(tree));
            assertEquals(
                    "[ PokemonAE{name='d'}, PokemonAE{name='b'}, PokemonAE{name='e'}, PokemonAE{name='c'} ]",
                    tree.toLevelOrderString());

            // checking each blackheight individually once to make sure validate() method
            // works
            assertEquals(1, tree.root.blackHeight);
            assertEquals(1, tree.root.context[1].blackHeight);
            assertEquals(1, tree.root.context[2].blackHeight);
            assertEquals(0, tree.root.context[1].context[2].blackHeight);
        }
    }

    /**
     * Tests remove method for:
     * case 3: sibling is black and both children are black, parent is red
     * case 4: sibling is black and both children are black, parent is black
     * 
     * @see RbtAE#remove(IPokemon) and
     *      RbtAE#fixRedBlackTreePropertiesAfterDelete(IPokemon)
     */
    @Test
    public void testRBTRemove2() {
        // case 3: sibling is black and both children are black, parent is red
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("d")); // insert Pokemon
            tree.insert(new PokemonAE("b"));
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("a"));
            tree.insert(new PokemonAE("c"));
            tree.insert(new PokemonAE("e"));
            PokemonAE pkm = new PokemonAE("g"); // the Pokemon to be removed
            tree.insert(pkm);

            // check tree size
            assertEquals(7, tree.size);

            tree.remove(pkm); // remove Pokemon pkm

            // check that the tree size is correct
            assertEquals(6, tree.size);

            // validate the tree and check that the tree is correct
            assertTrue(validateRBT(tree));
            assertEquals(
                    "[ PokemonAE{name='d'}, PokemonAE{name='b'}, PokemonAE{name='f'}, PokemonAE{name='a'}, PokemonAE{name='c'}, PokemonAE{name='e'} ]",
                    tree.toLevelOrderString());
        }

        // case 4: sibling is black and both children are black, parent is black
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("d"));
            tree.insert(new PokemonAE("b"));
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("a"));
            tree.insert(new PokemonAE("c"));
            tree.insert(new PokemonAE("g"));
            PokemonAE pkm = new PokemonAE("e"); // the Pokemon to be removed
            tree.insert(pkm);

            // check tree size
            assertEquals(7, tree.size);

            tree.remove(pkm); // remove Pokemon pkm

            // check that the tree size is correct
            assertEquals(6, tree.size);

            // validate the tree and check that the tree is correct
            assertTrue(validateRBT(tree));
            assertEquals(
                    "[ PokemonAE{name='d'}, PokemonAE{name='b'}, PokemonAE{name='f'}, PokemonAE{name='a'}, PokemonAE{name='c'}, PokemonAE{name='g'} ]",
                    tree.toLevelOrderString());
        }
    }

    /**
     * Tests remove method for:
     * case 5: sibling is black, left or right red child, "outer" nephew is black
     * case 6: sibling is black, left or right red child, "inner" nephew is red
     * 
     * @see RbtAE#remove(IPokemon) and
     *      RbtAE#fixRedBlackTreePropertiesAfterDelete(IPokemon)
     */
    @Test
    public void testRBTRemove3() {
        // case 5: sibling is black, left or right red child, "outer" nephew is black
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("b"));
            tree.insert(new PokemonAE("a"));
            tree.insert(new PokemonAE("d"));
            PokemonAE pkm = new PokemonAE("c"); // the Pokemon to be removed
            tree.insert(pkm);
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("e"));

            tree.remove(pkm); // remove Pokemon pkm

            // validate the tree and check that the tree is correct
            assertTrue(validateRBT(tree));
            assertEquals(
                    "[ PokemonAE{name='b'}, PokemonAE{name='a'}, PokemonAE{name='e'}, PokemonAE{name='d'}, PokemonAE{name='f'} ]",
                    tree.toLevelOrderString());
        }

        // case 6: sibling is black, left or right red child, "inner" nephew is red
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("b"));
            tree.insert(new PokemonAE("a"));
            tree.insert(new PokemonAE("d"));
            PokemonAE pkm = new PokemonAE("c"); // the Pokemon to be removed
            tree.insert(pkm);
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("e"));
            tree.insert(new PokemonAE("g"));

            tree.remove(pkm); // remove Pokemon pkm

            // validate the tree and check that the tree is correct
            assertTrue(validateRBT(tree));
            assertEquals(
                    "[ PokemonAE{name='b'}, PokemonAE{name='a'}, PokemonAE{name='f'}, PokemonAE{name='d'}, PokemonAE{name='g'}, PokemonAE{name='e'} ]",
                    tree.toLevelOrderString());
        }
    }

    /**
     * Tests get method.
     * 
     * @see RbtAE#get(String)
     */
    @Test
    public void testRBTGet() {

        /**
         * Let's test the get method by setting up a red black tree and trying to
         * retrieve a key that exists and a key that does not exist, and a null key.
         */
        RbtAE tree = new RbtAE(); // set up tree

        tree.insert(new PokemonAE("Charmander")); // insert Pokemon
        tree.insert(new PokemonAE("Charmeleon"));
        tree.insert(new PokemonAE("Charizard"));

        assertEquals("Charizard", tree.get("Charizard").getName()); // get Pokemon
        assertEquals("Charmander", tree.get("Charmander").getName());
        assertEquals("Charmeleon", tree.get("Charmeleon").getName());

        // now let's try to get a Pokemon that does not exist

        try {
            tree.get("Pikachu");
        } catch (Exception e) {
            assertEquals("java.util.NoSuchElementException", e.getClass().getName());
        }

        // now let's try to get a null Pokemon

        try {
            tree.get(null);
        } catch (Exception e) {
            assertEquals("java.lang.NullPointerException", e.getClass().getName());
        }
    }

    /**
     * Tests iterator functionality. Tests that the iterator returns the correct
     * order of the tree's Pokemon elements.
     * 
     * @see RbtAE#iterator(), PokemonIteratorAE#hasNext(), and
     *      PokemonIteratorAE#next()
     */
    @Test
    public void testRBTIterator() {

        /**
         * Let's test the iterator by setting up a red black tree and expecting the
         * iterator to move throw the elements in lexico-graphical order.
         */

        RbtAE tree = new RbtAE(); // set up tree

        tree.insert(new PokemonAE("Charmander")); // insert Pokemon
        tree.insert(new PokemonAE("Charmeleon"));
        tree.insert(new PokemonAE("Charizard"));
        tree.insert(new PokemonAE("Pikachu"));
        tree.insert(new PokemonAE("Raichu"));

        PokemonIteratorAE iter = tree.iterator(); // get iterator

        assertEquals("Charizard", iter.next().getName()); // test iterator
        assertEquals("Charmander", iter.next().getName());
        assertEquals("Charmeleon", iter.next().getName());

        assertTrue(iter.hasNext()); // test iterator for more elements, should be true

        assertEquals("Pikachu", iter.next().getName());
        assertEquals("Raichu", iter.next().getName());

        assertTrue(!iter.hasNext()); // test iterator for no more elements
    }

    // ---- ---- ---- P2W4 Integration Week Tests ---- ---- ---- //

    private IRbt<IPokemon> tree;
    private ICatalogReader reader;
    private BackendBD backend;

    @BeforeEach
    public void setUp() {
        tree = new RbtAE(); // set up tree
        reader = new CatalogReaderDW(); // set up reader
        backend = new BackendBD(tree, reader); // set up backend
    }

    /**
     * We will test the integration of the RBT and the BackendBD by adding and
     * removing and searching for Pokemon from the backend.
     * 
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Test
    public void testRBTIntegrationAddRemoveSearchFromBackend() {

        // add Pokemon to backend:
        String bulbasaur = "\"[\'Overgrow\', \'Chlorophyll\']\",1,1,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,318,45,Seed Pokémon,49,1059860,0.7,45,Fushigidaneフシギダネ,Bulbasaur,88.1,1,65,65,45,grass,poison,6.9,1,0";
        String ivysaur = "\"[\'Overgrow\', \'Chlorophyll\']\",2,2,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,405,45,Seed Pokémon,62,1059860,1,45,Fushigisouフシギソウ,Ivysaur,130.1,1,80,80,60,grass,poison,13,1,0";
        String venausaur = "\"[\'Overgrow\', \'Chlorophyll\']\",3,3,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,625,45,Seed Pokémon,100,1059860,2.4,45,Fushigibanaフシギバナ,Venusaur,1000.0,1,100,100,80,grass,poison,100,1,0";
        try {
            backend.addPokemon(bulbasaur);
            backend.addPokemon(ivysaur);
            backend.addPokemon(venausaur);

        } catch (IOException e) {
            fail("IOException thrown when adding Pokemon to backend");
        }

        // validate they entered the tree
        assertTrue(tree.contains(new PokemonAE("Bulbasaur")));
        assertTrue(tree.contains(new PokemonAE("Ivysaur")));
        assertTrue(tree.contains(new PokemonAE("Venusaur")));
        assertTrue(!tree.contains(new PokemonAE("Charmander"))); // check erroneous add

        // we have confirmed that the Pokemon are in the tree, now let's see if we can
        // search them up:

        assertEquals("Bulbasaur", backend.searchFor("Bulbasaur").getName());
        assertEquals("Ivysaur", backend.searchFor("Ivysaur").getName());
        assertEquals("Venusaur", backend.searchFor("Venusaur").getName());
        assertThrows(NoSuchElementException.class, () -> backend.searchFor("Charmander")); // check erroneous search
        assertThrows(NullPointerException.class, () -> backend.searchFor(null)); // check erroneous search

        // now let's remove some Pokemon from the tree and see if they are removed
        backend.removePokemon("Bulbasaur");
        assertTrue(!tree.contains(new PokemonAE("Bulbasaur")));

        backend.removePokemon("Ivysaur");
        assertTrue(!tree.contains(new PokemonAE("Ivysaur")));

        // now let's try to remove a Pokemon that does not exist
        assertThrows(NoSuchElementException.class, () -> backend.removePokemon("Charmander"));
        assertThrows(NullPointerException.class, () -> backend.removePokemon(null));

        // We've confirmed that the Pokemon are in the tree, we've confirmed that we can
        // search for them, and we've confirmed that we can remove them. This means that
        // the RBT and the BackendBD are integrated correctly.

        // clear the tree for the next test
        tree.clear();
    }

    /**
     * Tests the integration of the RBT and the
     * {@link BackendBD#displayCatalogStats()} and ensures the correct output.
     * 
     */
    @Test
    public void testRBTIntegrationLexicographicStatsDisplay() {

        // add Pokemon to backend:
        String bulbasaur = "\"[\'Overgrow\', \'Chlorophyll\']\",1,1,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,318,45,Seed Pokémon,49,1059860,0.7,45,Fushigidaneフシギダネ,Bulbasaur,88.1,1,65,65,45,grass,poison,6.9,1,0";
        String ivysaur = "\"[\'Overgrow\', \'Chlorophyll\']\",2,2,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,405,45,Seed Pokémon,62,1059860,1,45,Fushigisouフシギソウ,Ivysaur,130.1,1,80,80,60,grass,poison,13,1,0";
        String venausaur = "\"[\'Overgrow\', \'Chlorophyll\']\",3,3,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,625,45,Seed Pokémon,100,1059860,2.4,45,Fushigibanaフシギバナ,Venusaur,1000.0,1,100,100,80,grass,poison,100,1,0";
        String charmander = "\"[\'Blaze\', \'Solar Power\']\",4,4,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,309,45,Lizard Pokémon,52,1059860,0.6,39,Hitoriganeヒトカゲ,Charmander,18.7,1,60,50,65,fire,,8.5,1,0";
        String pikachu = "\"[\'Static\', \'Lightning Rod\']\",5,5,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,320,45,Mouse Pokémon,112,1059860,0.4,25,Pikachuピカチュウ,Pikachu,13.2,1,35,55,40,electric,,6.0,1,0";
        String flareon = "\"[\'Flash Fire\']\",6,6,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,525,45,Flame Pokémon,238,1059860,0.9,136,Enbuエンブー,Flareon,25.0,1,65,130,60,fire,,25.0,1,0";

        try {
            backend.addPokemon(bulbasaur);
            backend.addPokemon(ivysaur);
            backend.addPokemon(venausaur);
            backend.addPokemon(charmander);
            backend.addPokemon(pikachu);
            backend.addPokemon(flareon);

        } catch (IOException e) {
            fail("IOException thrown when adding Pokemon to backend");
        }

        // validate they entered the tree
        assertTrue(tree.contains(new PokemonAE("Bulbasaur")));
        assertTrue(tree.contains(new PokemonAE("Ivysaur")));
        assertTrue(tree.contains(new PokemonAE("Venusaur")));
        assertTrue(tree.contains(new PokemonAE("Charmander")));
        assertTrue(tree.contains(new PokemonAE("Pikachu")));
        assertTrue(tree.contains(new PokemonAE("Flareon")));

        // now let's check the stats
        String stats = backend.displayCatalogStats();
        String[] lines = stats.split(System.lineSeparator());
        assertEquals("BulbasaurClassification: Seed Pokémon", lines[1]);
        assertEquals("CharmanderClassification: Lizard Pokémon", lines[2]);
        assertEquals("FlareonClassification: Flame Pokémon", lines[3]);
        assertEquals("IvysaurClassification: Seed Pokémon", lines[4]);
        assertEquals("PikachuClassification: Mouse Pokémon", lines[5]);
        assertEquals("VenusaurClassification: Seed Pokémon", lines[6]);

        // which means, the backend reports a lexically sorted list of Pokemon names

        // clear the tree for the next test
        tree.clear();
    }

    /**
     * Tests how DataWranglerDW handles different kinds of CSV formatting.
     * 
     */
    @Test
    public void testDataWranglerCSVFormatting() {
        ICatalogReader cr = new CatalogReaderDW();

        // 1. test a valid CSV line
        try {
            cr.readCsvLineIntoPokemon(
                    "\"[\'Static\', \'Lightning Rod\']\",5,5,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,320,45,Mouse Pokémon,112,1059860,0.4,25,Pikachuピカチュウ,Pikachu,13.2,1,35,55,40,electric,,6.0,1,0");
            cr.readCsvLineIntoPokemon(
                    "\"[\'Blaze\', \'Solar Power\']\",6,6,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,905,45,Flame Pokémon,240,1059860,1.7,6,Lizardonリザードン,Charizard,90.5,1,78,84,78,fire,flying,90.5,1,0");
            cr.readCsvLineIntoPokemon(
                    "\"[\'Blaze\', \'Solar Power\']\",4,4,1,0.5,0.5,0.5,2,2,1,0.25,1,2,1,1,2,1,1,0.5,49,5120,70,309,45,Lizard Pokémon,52,1059860,0.6,39,Hitoriganeヒトカゲ,Charmander,18.7,1,60,50,65,fire,,8.5,1,0");
        } catch (IOException | IllegalArgumentException e) {
            fail("IOException thrown when adding Pokemon to backend");
        }

        // 2. tests a CSV line that has been post-processed by {@link
        // ICatalogReader#writeToFile(String, List)}.

        try {
            cr.readCsvLineIntoPokemon(
                    "\"['Wingshot']\",,,,,,,,,,,,,,,,,,,101,32320,0,720,0.0,Beta Pokémon,120,1250000,3.1,120,,PokemonName,,493,120,120,120,Normal,,,,1\n");
            cr.readCsvLineIntoPokemon(
                    "\"['Fearless', 'Water Blast', 'Depths', 'Wave Breaker']\",,,,,,,,,,,,,,,,,,,92,10240,70,460,0.0,Hostile Pokémon,65,1000000,1.0,70,,Waverider,50.0,550,80,55,98,Ground,,,,0\n");
            cr.readCsvLineIntoPokemon(
                    "\"['Shields Down']\",,,,,,,,,,,,,,,,,,,100,6400,70,500,,Meteor Pokémon,60,1059860,0.3,60,,Minior,,774,100,60,120,Rock,Flying,,,0\n");
        } catch (IOException | IllegalArgumentException e) {
            fail("IOException thrown when adding Pokemon to backend");
        }

        // 3. tests an odd but valid CSV line

        try {
            cr.readCsvLineIntoPokemon(
                    "\"[',,-, , ,']\",,,,,,,,,,,,,,,,,,,120,30720,0,720,,,120,1250000,3.2,120,,,,493,120,120,120,Normal,,,,1\n");
        } catch (IOException | IllegalArgumentException e) {
            fail("IOException thrown when adding Pokemon to backend");
        }

        // 4. tests an invalid CSV line
        assertThrows(IllegalArgumentException.class, () -> cr.readCsvLineIntoPokemon(""));
    }

    /**
     * Ensures that the DataWranglerDW class only accepts CSV files.
     */
    @Test
    public void testDataWranglerErroneousFileFormatting() {

        ICatalogReader cr = new CatalogReaderDW();

        // 2. test an invalid CSV file
        assertThrows(IllegalArgumentException.class, () -> cr.readFromFile("data/pokemon.txt"));

        // 3. test a non-existent file
        assertThrows(IOException.class, () -> cr.readFromFile("data/doesnotexist.csv"));

        // 4. test a null file
        assertThrows(NullPointerException.class, () -> cr.readFromFile(null));
    }
}
