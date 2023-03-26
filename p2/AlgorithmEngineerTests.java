import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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

    @BeforeEach
    public void setUp() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();

    }

    @Test
    public void testRBTInsert() {
        // - Case 1: Case: Parents sibling is black, parent and child on same side,
        // . . . . . Response: Rotate grandparent and parent, then color swap
        { // left rotation
            RedBlackTree<Integer> test = new RedBlackTree<>(); // set up tree

            // ensure that the tree is empty
            assertTrue(test.isEmpty(), "Tree should be empty but is not.");

            // insert nodes such that case 1 is true
            test.insert(0);
            test.insert(1);
            test.insert(2);
            test.insert(3);
            test.insert(4); // triggers case 1

            // check that the tree is in the correct state
            assertEquals("[ 0, 1, 2, 3, 4 ]", test.toInOrderString());
            assertEquals("[ 1, 0, 3, 2, 4 ]", test.toLevelOrderString());

            // check that the colors are correct
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].blackHeight);
            assertEquals(0, test.root.context[2].context[2].blackHeight);
        }
        { // right rotation
            RedBlackTree<Integer> test = new RedBlackTree<>(); // set up tree
            // insert nodes such that case 1 is true
            test.insert(9);
            test.insert(10);
            test.insert(11);
            test.insert(8);
            test.insert(7); // triggers case 1

            // check that the tree is in the correct state
            assertEquals("[ 7, 8, 9, 10, 11 ]", test.toInOrderString());
            assertEquals("[ 10, 8, 11, 7, 9 ]", test.toLevelOrderString());

            // check that the colors are correct
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[1].context[1].blackHeight);
            assertEquals(0, test.root.context[1].context[2].blackHeight);
        }
        // - Case 2: Case: Parents sibling is black, parent and child on opposite sides,
        // . . . . . Response: Rotate red child and parent, then rotate and color swap
        { // left rotation
            RedBlackTree<Integer> test = new RedBlackTree<>(); // set up tree
            // insert nodes such that case 2 is true
            test.insert(0);
            test.insert(2);
            test.insert(4);
            test.insert(6);
            test.insert(5); // triggers case 2

            // check that the tree is in the correct state
            assertEquals("[ 0, 2, 4, 5, 6 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 5, 4, 6 ]", test.toLevelOrderString());

            // check that the colors are correct
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].blackHeight);
            assertEquals(0, test.root.context[2].context[2].blackHeight);
        }
        { // right rotation
            RedBlackTree<Integer> test = new RedBlackTree<>(); // set up tree
            // insert nodes such that case 2 is true
            test.insert(9);
            test.insert(7);
            test.insert(5);
            test.insert(3);
            test.insert(4); // triggers case 2, right rotation

            // check that the tree is in the correct state
            assertEquals("[ 3, 4, 5, 7, 9 ]", test.toInOrderString());
            assertEquals("[ 7, 4, 9, 3, 5 ]", test.toLevelOrderString());

            // check that the colors are correct
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[1].context[1].blackHeight);
            assertEquals(0, test.root.context[1].context[2].blackHeight);
        }
        // - Case 3: Case: Parents sibling is red
        // . . . . . Response: Recolor parent, GP, and uncle; recurse. Also, misc tests
        // at the end of this test to ensure that the tree is in the correct state for
        // various insertions
        {
            RedBlackTree<Integer> test = new RedBlackTree<>(); // set up tree
            // insert nodes such that case 3 is true
            test.insert(0);
            test.insert(2);
            test.insert(4);
            test.insert(6); // triggers case 3

            // check that the tree is in the correct state
            assertEquals("[ 0, 2, 4, 6 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 4, 6 ]", test.toLevelOrderString());

            // check that the colors are correct
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[2].blackHeight);

            // other misc. checks
            test.insert(5);

            // check that the tree is in the correct state
            assertEquals("[ 0, 2, 4, 5, 6 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 5, 4, 6 ]", test.toLevelOrderString());

            // check that the colors are correct
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].blackHeight);
            assertEquals(0, test.root.context[2].context[2].blackHeight);

            // left left case
            test.insert(1);
            assertEquals("[ 0, 1, 2, 4, 5, 6 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 5, 1, 4, 6 ]", test.toLevelOrderString());
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(1, test.root.context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].blackHeight);
            assertEquals(0, test.root.context[2].context[2].blackHeight);

            // left right case
            test.insert(3);
            assertEquals("[ 0, 1, 2, 3, 4, 5, 6 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 5, 1, 4, 6, 3 ]", test.toLevelOrderString());
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(0, test.root.context[2].blackHeight);
            assertEquals(1, test.root.context[2].context[1].blackHeight);
            assertEquals(1, test.root.context[2].context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].context[1].blackHeight);

            // right right case
            test.insert(7);
            assertEquals("[ 0, 1, 2, 3, 4, 5, 6, 7 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 5, 1, 4, 6, 3, 7 ]", test.toLevelOrderString());
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(0, test.root.context[2].blackHeight);
            assertEquals(1, test.root.context[2].context[1].blackHeight);
            assertEquals(1, test.root.context[2].context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].context[1].blackHeight);
            assertEquals(0, test.root.context[2].context[2].context[2].blackHeight);

            // right left case
            test.insert(8);
            assertEquals("[ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]", test.toInOrderString());
            assertEquals("[ 2, 0, 5, 1, 4, 7, 3, 6, 8 ]", test.toLevelOrderString());
            assertEquals(1, test.root.blackHeight);
            assertEquals(1, test.root.context[1].blackHeight);
            assertEquals(0, test.root.context[2].blackHeight);
            assertEquals(1, test.root.context[2].context[1].blackHeight);
            assertEquals(1, test.root.context[2].context[2].blackHeight);
            assertEquals(0, test.root.context[2].context[1].context[1].blackHeight);
            assertEquals(0, test.root.context[2].context[2].context[2].blackHeight);
        }
        System.out.println("Insert ✔️");
    }

    @Test
    public void testRBTRemove() {
        RedBlackTree<Integer> tree = new RedBlackTree<>(); // set up tree

        // case 1: deleted node is root

        // case 2: sibling is red

        // case 3: sibling is black and both children are black, parent is red

        // case 4: sibling is black and both children are black, parent is black

        // case 5: sibling is black, left or right red child, "outer" nephew is black

        // case 6: sibling is black, left or right red child, "inner" nephew is red
    }

    @Test
    public void testRBTGet() {
        RedBlackTree<Integer> tree = new RedBlackTree<>(); // set up tree

    }

    @Test
    public void testRBTIterator() {
        RedBlackTree<IPokemon> tree = new RedBlackTree<>(); // set up tree
        tree.insert(new PokemonAE("Pikachu", 15, "Electric"));
        tree.insert(new PokemonAE("Raichu", 16, "Electric"));
        tree.insert(new PokemonAE("Charmander", 4, "Fire"));
        tree.insert(new PokemonAE("Charmeleon", 5, "Fire"));
        tree.insert(new PokemonAE("Charizard", 6, "Fire"));

        assertEquals(
                "[ PokemonAE@7364985f, PokemonAE@5d20e46, PokemonAE@709ba3fb, PokemonAE@3d36e4cd, PokemonAE@6a472554 ]",
                tree.toInOrderString());

        Iterator<IPokemon> iter = tree.iterator();

        assertEquals("Charmander", iter.next().getName());
        assertEquals("Charmeleon", iter.next().getName());
        assertEquals("Charizard", iter.next().getName());
        assertEquals("Pikachu", iter.next().getName());
        assertEquals("Raichu", iter.next().getName());

    }

    @Test
    public void testDriver() {

    }
}
