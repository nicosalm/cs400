// --== CS400 Spring 2023 File Header Information ==--
// Name: Nico Salm
// Email: nbsalm@wisc.edu
// Team: AC
// TA: Rachit Tibdewal
// Lecturer: Gary Dahl
// Notes to Grader: NONE

import java.util.LinkedList;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Red-Black Tree implementation with a Node inner class for representing the
 * nodes of the tree.
 * Currently, this implements a Binary Search Tree that we will turn into a red
 * black tree by
 * modifying the insert functionality. In this activity, we will start with
 * implementing rotations
 * for the binary search tree insert algorithm.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;
        public int blackHeight;

        // The context array stores the context of the node in the tree:
        // - context[0] is the parent reference of the node,
        // - context[1] is the left child reference of the node,
        // - context[2] is the right child reference of the node.
        @SuppressWarnings("unchecked")
        public Node<T>[] context = (Node<T>[]) new Node[3];

        public Node(T data) {
            this.data = data;
            this.blackHeight = 0;
        }

        /**
         * @return true when this node has a parent and is the right child of that
         *         parent, otherwise
         *         return false
         */
        public boolean isRightChild() {
            return context[0] != null && context[0].context[2] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs the rotation operation on the provided nodes within this tree. When
     * the provided
     * child is a left child of the provided parent, this method will perform a
     * right rotation. When
     * the provided child is a right child of the provided parent, this method will
     * perform a left
     * rotation. When the provided nodes are not related in one of these ways, this
     * method will
     * throw an IllegalArgumentException.
     * 
     * @param child  is the node being rotated from child to parent position
     *               (between these two node
     *               arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node
     *               arguments)
     * @throws IllegalArgumentException when the provided child and parent node
     *                                  references are not
     *                                  initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Provided child a/or parent node are null.");
        }

        if (!child.context[0].equals(parent)) {
            throw new IllegalArgumentException(
                    "Provided nodes are related in the appropriate way.");
        }

        // if rightchild, left rotation
        if (child.isRightChild()) {
            // Assign child left reference to parent right reference
            parent.context[2] = child.context[1];
            // Assign parent to child left reference;
            child.context[1] = parent;

        } else { // if leftchild, right rotation
            // Assign child right reference to parent left reference
            parent.context[1] = child.context[2];
            // Assign parent to child right reference;
            child.context[2] = parent;
        }

        // Assign parent parent reference to child parent reference (if parent is root,
        // child becomes root)
        if (parent.equals(root)) {
            root = child;
        } else if (parent.isRightChild()) {
            parent.context[0].context[2] = child;
        } else {
            parent.context[0].context[1] = child;
        }

        // Assign child parent reference to parent parent reference
        child.context[0] = parent.context[0];
        parent.context[0] = child;
    }

    /**
     * Helper method that will replace a node with a replacement node. The
     * replacement node may be
     * null to remove the node from the tree.
     * 
     * @param nodeToReplace   the node to replace
     * @param replacementNode the replacement for the node (may be null)
     */
    protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode) {
        if (nodeToReplace == null) {
            throw new NullPointerException("Cannot replace null node.");
        }
        if (nodeToReplace.context[0] == null) {
            // we are replacing the root
            if (replacementNode != null)
                replacementNode.context[0] = null;
            this.root = replacementNode;
        } else {
            // set the parent of the replacement node
            if (replacementNode != null)
                replacementNode.context[0] = nodeToReplace.context[0];
            // do we have to attach a new left or right child to our parent?
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
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            enforceRBTreePropertiesAfterInsert(newNode);
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
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
    public void enforceRBTreePropertiesAfterInsert(Node<T> node) {
        if (root == node) { // if new node is root, set to black and return
            root.blackHeight = 1;
            return;
        }
        Node<T> p = node.context[0]; // parent pointer
        Node<T> gP = p.context[0]; // grandparent pointer
        if (p == root || p.blackHeight != 0) { // if parent is black,
            return;
        }
        Node<T> u = null; // create uncle pointer and assign if possible
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
     */
    public boolean remove(T data) throws NullPointerException, IllegalArgumentException {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // throw exception if node with data does not exist
            if (nodeWithData == null) {
                throw new IllegalArgumentException(
                        "The following value is not in the tree and cannot be deleted: "
                                + data.toString());
            }
            boolean hasRightChild = (nodeWithData.context[2] != null);
            boolean hasLeftChild = (nodeWithData.context[1] != null);
            if (hasRightChild && hasLeftChild) {
                // has 2 children
                Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
                // replace value of node with value of successor node
                nodeWithData.data = successorNode.data;
                // remove successor node
                if (successorNode.context[2] == null) {
                    // successor has no children, replace with null
                    this.replaceNode(successorNode, null);
                } else {
                    // successor has a right child, replace successor with its child
                    this.replaceNode(successorNode, successorNode.context[2]);
                }
            } else if (hasRightChild) {
                // only right child, replace with right child
                this.replaceNode(nodeWithData, nodeWithData.context[2]);
            } else if (hasLeftChild) {
                // only left child, replace with left child
                this.replaceNode(nodeWithData, nodeWithData.context[1]);
            } else {
                // no children, replace node with a null node
                this.replaceNode(nodeWithData, null);
            }
            this.size--;
            enforceRBTreePropertiesAfterDeletion(nodeWithData);
            return true;
        }
    }

    /**
     * Helper method that will replace a node with a replacement node. The
     * replacement node may be null to remove the node from the tree.
     * Intended to be used by the remove method to ensure that the tree is
     * properly updated after a node is removed.
     * 
     * @param node the node to replace
     */
    public void enforceRBTreePropertiesAfterDeletion(Node<T> node) {
        return; // TODO implement this method
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
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
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
    protected Node<T> findMinOfRightSubtree(Node<T> node) {
        if (node.context[1] == null && node.context[2] == null) {
            throw new IllegalArgumentException("Node must have two children");
        }
        // take a steop to the right
        Node<T> current = node.context[2];
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
    protected Node<T> findNodeWithData(T data) {
        Node<T> current = this.root;
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
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
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
     * representations of each
     * data value within this tree are assembled into a comma separated string
     * within brackets
     * (similar to many implementations of java.util.Collection). This method will
     * be helpful as a
     * helper for the debugging and testing of your rotation implementation.
     * 
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while (!q.isEmpty()) {
                Node<T> next = q.removeFirst();
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

    public String toString() {
        return "level order: " + this.toLevelOrderString() + "\nin order: "
                + this.toInOrderString();
    }
}