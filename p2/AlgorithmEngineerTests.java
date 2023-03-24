import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * JUnit tests for the RedBlackTree class.
 */
public class AlgorithmEngineerTests {

    @BeforeEach
    public void setUp() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(55);
        tree.insert(40);
        tree.insert(65);
        tree.insert(60);
        tree.insert(75);
        tree.insert(57);

        tree.remove(40);

        System.out.println(tree.toString());
    }
}
