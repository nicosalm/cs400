import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the RedBlackTree class.
 * In this class we test:
 * 1. The insert method
 * 2. The remove method
 * 3. The get method
 * 4. The iterator functionality
 * 5. The Driver class
 */
public class AlgorithmEngineerTests {

    /**
     * Method to validate the RBT.
     * 
     * @param tree the RBT to validate
     * @return true if the RBT is valid, false otherwise
     */
    public boolean validateRBT(RbtAE tree) {
        if (tree.root == null) {
            return true;
        }

        if (tree.root.blackHeight != 1) {
            return false;
        }

        int left = numBlack(tree.root.context[1]);
        int right = numBlack(tree.root.context[2]);

        if (left != right || left == -1) {
            return false;
        }

        return true;
    }

    /**
     * Returns the number of black nodes in the path from the root to the given
     * node. Helper method for validateRBT.
     * 
     * @param node the node to check
     * @return the number of black nodes in the path from the root to the given
     */
    private int numBlack(RbtAE.Node<IPokemon> node) {
        if (node == null) {
            return 0;
        }

        int left = numBlack(node.context[1]);
        int right = numBlack(node.context[2]);

        if (left != right || left == -1) {
            return -1;
        }

        if (node.blackHeight == 1) {
            return left + 1;
        } else {
            return left;
        }
    }

    /**
     * Tests remove method for:
     * case 1: deleted node is root.
     * case 2: sibling is red
     * case 3: sibling is black and both children are black, parent is red
     * case 4: sibling is black and both children are black, parent is black
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

            tree.root.blackHeight = 1;
            tree.root.context[1].blackHeight = 1;
            tree.root.context[2].blackHeight = 0;
            tree.root.context[2].context[1].blackHeight = 1;
            tree.root.context[2].context[2].blackHeight = 1;

            // check tree size
            assertEquals(5, tree.size);

            tree.remove(pkm); // remove Pokemon

            // check that the tree size is correct
            assertEquals(4, tree.size);

            // validate the tree
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

        // case 3: sibling is black and both children are black, parent is red
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("d"));
            tree.insert(new PokemonAE("b"));
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("a"));
            tree.insert(new PokemonAE("c"));
            tree.insert(new PokemonAE("e"));
            PokemonAE pkm = new PokemonAE("g");
            tree.insert(pkm);

            // check tree size
            assertEquals(7, tree.size);

            tree.remove(pkm); // remove Pokemon

            // check that the tree size is correct
            assertEquals(6, tree.size);

            // validate the tree
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
            PokemonAE pkm = new PokemonAE("e");
            tree.insert(pkm);

            // check tree size
            assertEquals(7, tree.size);

            tree.remove(pkm); // remove Pokemon

            // check that the tree size is correct
            assertEquals(6, tree.size);

            // validate the tree
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
    public void testRBTRemove2() {
        // case 5: sibling is black, left or right red child, "outer" nephew is black
        {
            RbtAE tree = new RbtAE(); // set up tree

            tree.insert(new PokemonAE("b"));
            tree.insert(new PokemonAE("a"));
            tree.insert(new PokemonAE("d"));
            PokemonAE pkm = new PokemonAE("c");
            tree.insert(pkm);
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("e"));

            tree.remove(pkm); // remove Pokemon

            // validate the tree
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
            PokemonAE pkm = new PokemonAE("c");
            tree.insert(pkm);
            tree.insert(new PokemonAE("f"));
            tree.insert(new PokemonAE("e"));
            tree.insert(new PokemonAE("g"));

            tree.remove(pkm); // remove Pokemon

            // validate the tree
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

        Iterator<IPokemon> iter = tree.iterator(); // get iterator

        assertEquals("Charizard", iter.next().getName()); // test iterator
        assertEquals("Charmander", iter.next().getName());
        assertEquals("Charmeleon", iter.next().getName());

        assertTrue(iter.hasNext()); // test iterator

        assertEquals("Pikachu", iter.next().getName());
        assertEquals("Raichu", iter.next().getName());

        assertTrue(!iter.hasNext()); // test iterator
    }

    /**
     * Tests the Driver class.
     * 
     * @see Pokeylog.java
     */
    @Test
    public void testDriver() {

    }
}
