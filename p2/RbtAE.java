// --== CS400 Spring 2023 File Header Information ==--
// Name: Nico Salm
// Email: nbsalm@wisc.edu
// Team: AC
// TA: Rachit Tibdewal
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing the
 * nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red
 * black tree by
 * modifying the insert functionality. In this activity, we will start with
 * implementing rotations
 * for the binary search tree insert algorithm.
 * 
 * @author Nico S. and Course Staff
 */
public class RbtAE implements IRbt<IPokemon> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<IPokemon> {
        public IPokemon data;
        public int blackHeight;
        public Node(IPokemon data) {
            this.data = data;
            this.blackHeight = 0;
        }

        // The context array stores the context of the node in the tree:
        // - context[0] is the parent reference of the node,
        // - context[1] is the left child reference of the node,
        // - context[2] is the right child reference of the node.
        @SuppressWarnings("unchecked")
        public Node<IPokemon>[] context = (Node<IPokemon>[]) new Node[3];


        /**
         * @return true when this node has a parent and is the right child of that
         *         parent, otherwise
         *         return false
         */
        public boolean isRightChild() {
            return context[0] != null && context[0].context[2] == this;
        }

        /**
         * Gets the inorder successor of this node in the tree. The inorder successor is
         * the next node in an inorder traversal of the tree. When this node has a right
         * child, then the inorder successor is the left-most node in the right subtree
         * of this node. When this node does not have a right child, then the inorder
         * successor is the closest ancestor of this node whose left child is also an
         * ancestor of this node. If this node does not have an inorder successor, then
         * this method returns null.
         * 
         * @return the inorder successor of this node, or null if none exists
         */
        public Node<IPokemon> getInorderSuccessor() {
            Node<IPokemon> current = this;
            if (current.context[2] != null) {
                current = current.context[2];
                while (current.context[1] != null) {
                    current = current.context[1];
                }
                return current;
            } else {
                Node<IPokemon> parent = current.context[0];
                while (parent != null && current == parent.context[2]) {
                    current = parent;
                    parent = parent.context[0];
                }
                return parent;
            }
        }
    }

    protected Node<IPokemon> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<IPokemon> child, Node<IPokemon> parent) throws IllegalArgumentException {
        int compare = child.data.compareTo(parent.data);
        // Throw IllegalArgumentException
        if(!(compare < 0 || compare > 0)) {
            throw new IllegalArgumentException("");
        }
        // Preform Right Rotation when child < parent (left child) < 0
        // steps: (parent = P child = C)
        // 1) P left = C right, C right parent = P
        // 2) C parent = P parent
        // 3) root = C or P parent right = C or P parent left = C
        // 4) C right = P, P parent = C
        if(compare < 0) {
            parent.context[1] = child.context[2]; // P left = C right
            if(child.context[2] != null) { // if C right is null
                child.context[2].context[0] = parent; // C right parent = P
            }
            child.context[0] = parent.context[0]; // C parent = P parent
            if(parent.context[0] == null) { // P is root
                this.root = child; // root = C
            } else if(parent == parent.context[0].context[2]) { // P is right child
                parent.context[0].context[2] = child; // P parent right = C
            } else { // P is left child
                parent.context[0].context[1] = child; // P parent left = C
            }
            child.context[2] = parent; // C right = P
            parent.context[0] = child; // P parent = C
        }
        // Preform Left Rotation when child > parent (right child) > 0
        // steps: (parent = P child = C)
        // 1) P right = C left, C left parent = P
        // 2) C parent = P parent
        // 3) root = C or P parent left = C or P parent right = C
        // 4) C left = P, P parent = C
        if(compare > 0) {
            parent.context[2] = child.context[1]; // P right = C left
            if(child.context[1] != null) { // if C left null
                child.context[1].context[0] = parent; // C left parent = P
            }
            child.context[0] = parent.context[0]; // C parent = P parent
            if(parent.context[0] == null) { // P is root
                this.root = child; // root = C
            } else if(parent == parent.context[0].context[1]) { // P is left child
                parent.context[0].context[1] = child; // P parent left = C
            } else { // P is right child
                parent.context[0].context[2] = child; // P parent right = C
            }
            child.context[1] = parent; // C left = P
            parent.context[0] = child; // P parent = C
        }
    }

    /**
     * Helper method that will replace a node with a replacement node. The
     * replacement node may be
     * null to remove the node from the tree.
     * 
     * @param nodeToReplace   the node to replace
     * @param replacementNode the replacement for the node (may be null)
     */
    protected void replaceNode(Node<IPokemon> nodeToReplace, Node<IPokemon> replacementNode) {
        if (nodeToReplace == null) {
            throw new NullPointerException("Cannot replace null node.");
        }
        if (nodeToReplace.context[0] == null) {
            // replace the root
            if (replacementNode != null)
                replacementNode.context[0] = null;
            this.root = replacementNode;
        } else {
            // set the parent of the replacement node
            if (replacementNode != null)
                replacementNode.context[0] = nodeToReplace.context[0];
            // replace the node in the parent
            if (nodeToReplace.isRightChild()) {
                nodeToReplace.context[0].context[2] = replacementNode;
            } else {
                nodeToReplace.context[0].context[1] = replacementNode;
            }
        }
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the input data
     * alue to a new node in a leaf position within the tree. After this insertion,
     * attempt is made to restructure or balance the tree. This tree will not hold
     * null references, nor uplicate data alues.
     * 
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when data is already contained in the tree
     */
    public boolean insert(IPokemon data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");

        Node<IPokemon> newNode = new Node<>(data);
        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
        } else {
            // insert into subtree
            Node<IPokemon> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                    throw new IllegalArgumentException(
                            "This RedBlackTree already contains value " + data.toString());
                } else if (compare < 0) {
                    // insert in left subtree
                    if (current.context[1] == null) {
                        // empty space to insert into
                        current.context[1] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[1];
                    }
                } else {
                    // insert in right subtree
                    if (current.context[2] == null) {
                        // empty space to insert into
                        current.context[2] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[2];
                    }
                }
            }
        }
    }

    /**
     * The job of this enforceRBTreePropertiesAfterInsert method is to resolve any
     * red-black tree property violations that are introduced by inserting each new
     * new node into a red-black tree. This method will be called recursively.
     * 
     * @param node
     */
    private void enforceRBTreePropertiesAfterInsert(Node<IPokemon> node) {
        if (root == node) { // if new node is root, set to black and return
            root.blackHeight = 1;
            return;
        }
        Node<IPokemon> p = node.context[0]; // parent pointer
        Node<IPokemon> gP = p.context[0]; // grandparent pointer
        if (p == root || p.blackHeight != 0) { // if parent is black,
            return;
        }
        Node<IPokemon> u = null; // create uncle pointer and assign if possible
        if (p.isRightChild()) {
            u = gP.context[1];
        } else {
            u = gP.context[2];
        }

        // if parent is red
        if (node.blackHeight == 0 && p.blackHeight == 0) {
            // if uncle is red
            if (u != null && u.blackHeight == 0) {
                // recolor
                p.blackHeight = 1;
                u.blackHeight = 1;
                gP.blackHeight = 0;
                enforceRBTreePropertiesAfterInsert(gP);
            } else {
                // if uncle is black
                if (!p.isRightChild() && node.isRightChild()
                        || p.isRightChild() && !node.isRightChild()) {
                    // different sides, rotate parent and newNode
                    rotate(node, p);
                    enforceRBTreePropertiesAfterInsert(p);
                } else {
                    // same sides, rotate grandparent and parent and recolor
                    int gPHeight = gP.blackHeight;
                    gP.blackHeight = p.blackHeight;
                    p.blackHeight = gPHeight;
                    rotate(p, gP);
                }
            }
        }
        root.blackHeight = 1; // root is always black
    }

    /**
     * Removes the value data from the tree if the tree contains the value. This
     * method will attempt to rebalance the tree after the removal and should be
     * updated once the tree uses Red-Black Tree insertion.
     * 
     * @return true if the value was remove, false if it didn't exist
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when data is not stored in the tree
     * 
     * @author Nico Salm, with inspiration from 400 course staff
     */
    public boolean remove(IPokemon data) throws NullPointerException, IllegalArgumentException {
        Node<IPokemon> node = root;

        // Find the node to be deleted
        while (node != null && node.data != data) {
            // Traverse the tree to the left or right depending on the key
            if (data.compareTo((IPokemon) node.data) < 0) {
                node = node.context[1];
            } else {
                node = node.context[2];
            }
        }

        // Node not found?
        if (node == null) {
            return false;
        }

        // Node found, delete it and fix the tree; keep track of the node that was moved
        // up to replace the deleted node and the color of the deleted node
        Node<IPokemon> movedUpNode;
        int deletedNodeColor;

        // Node has zero or one child
        if (node.context[1] == null || node.context[2] == null) {
            movedUpNode = deleteNodeZeroOrOneChild(node);
            deletedNodeColor = node.blackHeight;
        }

        // Node has two children
        else {
            // Find minimum node of right subtree ("inorder successor" of current node)
            Node<IPokemon> successorNode = findMinimum(node.context[2]);

            // Copy inorder successor's data to current node (keep its color!)
            node.data = successorNode.data;

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeZeroOrOneChild(successorNode);
            deletedNodeColor = successorNode.blackHeight;
        }

        if (deletedNodeColor == 1) {
            enforceRBTreePropertiesAfterRemove(movedUpNode);

            // Remove the temporary NIL node
            if (movedUpNode.getClass() == NilNode.class) {
                replaceNode(movedUpNode, null);
            }
        }

        size--;
        return true;
    }

    /**
     * Helper method that will delete a node with zero or one child. The method will
     * return the node that was moved up to replace the deleted node.
     * 
     * @param node the node to be deleted
     * @return the node that was moved up to replace the deleted node
     */
    private Node<IPokemon> deleteNodeZeroOrOneChild(Node<IPokemon> node) {
        // Node has ONLY a left child: replace by its left child
        if (node.context[1] != null) {
            replaceNode(node, node.context[1]);
            return node.context[1]; // moved-up node
        }

        // Node has ONLY a right child: replace by its right child
        else if (node.context[2] != null) {
            replaceNode(node, node.context[2]);
            return node.context[2]; // moved-up node
        }

        // Node has no children: replace by NIL node
        else {
            @SuppressWarnings("unchecked")
            Node<IPokemon> newChild = node.blackHeight == 1 ? new NilNode() : null;
            replaceNode(node, newChild);
            return newChild;
        }
    }

    /**
     * Helper method that will replace a node with a replacement node. The
     * replacement node may be null to remove the node from the tree.
     * Intended to be used by the remove method to ensure that the tree is
     * properly updated after a node is removed.
     * 
     * In contrast to the enforceRBTreePropertiesAfterInsert method, this method
     * does
     * not care about the uncle of the node being removed. It only cares about the
     * sibling of the node being removed.
     * 
     * Cases: 1. deleted node is root, 2. sibling is red, 3. sibling is black with 2
     * black children; parent is red, 4. sibling is black and has two black
     * children, parent is black, 5. sibling is black and has at least one red
     * child, "outer nephew" is black, 6. sibling is black and has at least one red
     * child, "outer nephew" is red. Behold, the cases!
     * 
     * @param node the node to replace
     */
    private void enforceRBTreePropertiesAfterRemove(Node<IPokemon> node) {
        // Case 1: Examined node is root, end of recursion
        if (node == root) {
            // node.blackHeight = 1;
            return;
        }

        Node<IPokemon> sibling = getSibling(node);

        // Case 2: Red sibling
        if (sibling.blackHeight == 0) {
            sibling.blackHeight = 1;
            node.context[0].blackHeight = 0;

            // rotate
            if (node == node.context[0].context[1]) {
                // rotate left
                Node<IPokemon> gP = node.context[0].context[0];
                Node<IPokemon> rightChild = node.context[0].context[2];

                node.context[0].context[2] = rightChild.context[1];
                if (rightChild.context[1] != null) {
                    rightChild.context[1].context[0] = node.context[0];
                }

                rightChild.context[1] = node.context[0];
                node.context[0].context[0] = rightChild;

                replaceParentsChild(gP, node.context[0], rightChild); // replace parents child
            } else {
                // rotate right
                Node<IPokemon> gP = node.context[0].context[0];
                Node<IPokemon> leftChild = node.context[0].context[1];

                node.context[0].context[1] = leftChild.context[2];
                if (leftChild.context[2] != null) {
                    leftChild.context[2].context[0] = node.context[0];
                }

                leftChild.context[2] = node.context[0];
                node.context[0] = leftChild;

                replaceParentsChild(gP, node.context[0], leftChild); // replace parents child
            }
            sibling = getSibling(node); // Get new sibling for fall-through to cases 3-6
        }

        // Cases 3 and 4: Black sibling, two black children
        if (isBlack(sibling.context[1]) && isBlack(sibling.context[2])) {
            sibling.blackHeight = 0;

            // Case 3: Black sibling with two black children + red parent
            if (node.context[0].blackHeight == 0) {
                node.context[0].blackHeight = 1;
            }

            // Case 4: Black sibling with two black children, black parent
            else {
                enforceRBTreePropertiesAfterRemove(node);
            }
        }

        // Case 5 and 6: Black sibling, at least one red child
        else {
            handleBlackSiblingRedChild(node, sibling);
        }
    }

    /**
     * Helper method for the fixRedBlackPropertiesAfterDelete method. This method
     * handles case 5 and 6.
     * 
     * @param node    the node being removed
     * @param sibling the sibling of the node being removed
     */
    private void handleBlackSiblingRedChild(Node<IPokemon> node, Node<IPokemon> sibling) {
        boolean nodeIsLeftChild = node == node.context[0].context[1];

        // Case 5: Black sibling with at least one red child and "outer nephew" is black
        // Recolor sibling and its child, and rotate around sibling
        if (nodeIsLeftChild && isBlack(sibling.context[2])) {
            sibling.context[1].blackHeight = 0;
            rotate(sibling.context[1], sibling); // rotate right
            sibling = node.context[0].context[2];
        } else if (!nodeIsLeftChild && isBlack(sibling.context[1])) {
            sibling.context[2].blackHeight = 1;
            sibling.blackHeight = 0;
            rotate(sibling.context[2], sibling); // rotate left
            sibling = node.context[0].context[1];
        }

        // Case 6: Black sibling with at least one red child and "outer nephew" is red
        // Recolor sibling + parent + sibling's child, and rotate around parent
        sibling.blackHeight = node.context[0].blackHeight;
        node.context[0].blackHeight = 1;
        if (nodeIsLeftChild) {
            sibling.context[2].blackHeight = 1;
            rotate(sibling, node.context[0]); // rotate left
        } else {
            sibling.context[1].blackHeight = 1;
            rotate(sibling, node.context[0]); // rotate right
        }
    }

    /**
     * Returns the sibling of the given node.
     * 
     * @param node the node to get the sibling of
     * @return the sibling of the given node
     */
    private Node<IPokemon> getSibling(Node<IPokemon> node) {
        Node<IPokemon> parent = node.context[0]; // parent of node
        if (node == parent.context[1]) { // node is left child
            return parent.context[2];
        } else if (node == parent.context[2]) { // node is right child
            return parent.context[1];
        } else { // node is not a child of its parent
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    /**
     * Returns true if the node is black, or null.
     * 
     * @param node the node to check
     * @return true if the node is black, or null
     */
    private boolean isBlack(Node<IPokemon> node) {
        return node == null || node.blackHeight == 1; // null nodes are black
    }

    /**
     * A class representing a NIL node. This is a singleton class, so that there is
     * only one NIL. This is necessary for the fixRedBlackPropertiesAfterDelete to
     * be a constant time operation.
     * 
     */
    @SuppressWarnings("unchecked") // Safe cast
    private static class NilNode extends Node {
        private NilNode() {
            super(0);
            this.blackHeight = 1;
        }
    }

    /**
     * Replaces a child of a parent with a new child. Helper used in
     * fixRedBlackPropertiesAfterDelete.
     * 
     * @param parent   the parent node
     * @param oldChild the old child node
     * @param newChild the new child node
     */
    private void replaceParentsChild(Node<IPokemon> parent, Node<IPokemon> oldChild, Node<IPokemon> newChild) {
        if (parent == null) { // parent is root
            root = newChild;
        } else if (parent.context[1] == oldChild) { // parent is left child
            parent.context[1] = newChild;
        } else if (parent.context[2] == oldChild) { // parent is right child
            parent.context[2] = newChild;
        } else { // parent is not a child of its parent
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) { // newChild is not NIL
            newChild.context[0] = parent;
        }
    }

    /**
     * Finds the minimum node in the tree.
     * 
     * @param root the node to start the search from
     * @return the minimum node in the tree
     */
    private Node<IPokemon> findMinimum(Node<IPokemon> root) {
        while (root.context[1] != null) { // while root has a left child
            root = root.context[1];
        }
        return root;
    }

    /**
     * Get the size of the tree (its number of nodes).
     * 
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * 
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * 
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(IPokemon data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<IPokemon> nodeWithData = this.findNodeWithData(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Helper method that will return the inorder successor of a node with two
     * children.
     * 
     * @param node the node to find the successor for
     * @return the node that is the inorder successor of node
     */
    protected Node<IPokemon> findMinOfRightSubtree(Node<IPokemon> node) {
        if (node.context[1] == null && node.context[2] == null) {
            throw new IllegalArgumentException("Node must have two children");
        }
        // take a steop to the right
        Node<IPokemon> current = node.context[2];
        while (true) {
            // then go left as often as possible to find the successor
            if (current.context[1] == null) {
                // we found the successor
                return current;
            } else {
                current = current.context[1];
            }
        }
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * value. Returns
     * null if there is no node that contains the value.
     * 
     * @return the node that contains the data, or null of no such node exists
     */
    protected Node<IPokemon> findNodeWithData(IPokemon data) {
        Node<IPokemon> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                // keep looking in the left subtree
                current = current.context[1];
            } else {
                // keep looking in the right subtree
                current = current.context[2];
            }
        }
        // we're at a null node and did not find data, so it's not in the tree
        return null;
    }

    /**
     * This method performs an inorder traversal of the tree. The string
     * representations of each
     * data value within this tree are assembled into a comma separated string
     * within brackets
     * (similar to many implementations of java.util.Collection, like
     * java.util.ArrayList,
     * LinkedList, etc).
     * 
     * @return string containing the ordered values of this tree (in-order
     *         traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<IPokemon>> nodeStack = new Stack<>();
            Node<IPokemon> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<IPokemon> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if (!nodeStack.isEmpty() || popped.context[2] != null)
                        sb.append(", ");
                    current = popped.context[2];
                } else {
                    nodeStack.add(current);
                    current = current.context[1];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations of
     * java.util.Collection). This method will be helpful as a elper for the
     * debugging and testing of your rotation implementation.
     * 
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<IPokemon>> q = new LinkedList<>();
            q.add(this.root);
            while (!q.isEmpty()) {
                Node<IPokemon> next = q.removeFirst();
                if (next.context[1] != null)
                    q.add(next.context[1]);
                if (next.context[2] != null)
                    q.add(next.context[2]);
                sb.append(next.data.toString());
                if (!q.isEmpty())
                    sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method returns an iterator over the values in this tree. The values are
     * iterated over in a level order sequence.
     * 
     * @return an iterator of type PokemonIteratorAE over the values in this tree
     */
    @Override
    public PokemonIteratorAE iterator() {
        return new PokemonIteratorAE((RbtAE.Node<IPokemon>) findMinimum(root));
    }

    /**
     * This method returns a string representation of the tree.
     * 
     * @return string containing the values of this tree in level order and in order
     */
    @Override
    public String toString() {
        return "level order: " + this.toLevelOrderString() + "\nin order: "
                + this.toInOrderString();
    }

    /**
     * This method retrieves the data value associated with the specified key. If no
     * such key exists, it throws a NoSuchElementException.
     * 
     * @param searchKey the key to search for
     * @return the value associated with the search key
     * @throws NoSuchElementException if the key is not found
     */
    @Override
    public IPokemon get(String searchKey) throws NoSuchElementException {
        if (searchKey == null) { // cannot search for null key
            throw new NullPointerException("Cannot search for null key!");
        }

        Node<IPokemon> current = this.root; // start at the root

        while (current != null) { // while we have not reached a null node yet
            int compare = searchKey.compareTo(current.data.getName()); // compare
            if (compare == 0) {
                // we found our value
                return current.data;
            } else if (compare < 0) {
                // keep looking in the left subtree
                current = current.context[1];
            } else {
                // keep looking in the right subtree
                current = current.context[2];
            }
        }
        throw new NoSuchElementException("No such key in the tree!"); // key not found
    }

    /**
     * Clears the tree.
     */
    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }
}
