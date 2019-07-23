import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Sitong Wu
 * @userid swu321 (i.e. gburdell3)
 * @GTID 903163600 (i.e. 900000000)
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("The Collection is empty.");
        }
        for (T d: data) {
            if (d == null) {
                throw new IllegalArgumentException(
                        "There is null in the Collection");
            }
            add(d);
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (!contains(data)) { // ensure the data is not a duplicate
            if (size == 0) {
                root = new BSTNode<T>(data);
                size++;
            } else {
                addH(data, root);
            }
        }
    }

    /**
     * Helper method for add()
     * go through the tree recursively to find proper location to add data
     * @param data the data to be added
     * @param curr the root of the tree (subtree).
     */
    private void addH(T data, BSTNode<T> curr) {
        //base
        if (curr == null) {
            curr = new BSTNode<T>(data);
            size++;
        }
        if (data.compareTo(curr.getData()) > 0) {
            if (curr.getRight() != null) {
                addH(data, curr.getRight());
            } else {
                curr.setRight(new BSTNode<T>(data));
                size++;
            }
        } else {
            if (curr.getLeft() != null) {
                addH(data, curr.getLeft());
            } else {
                curr.setLeft(new BSTNode<T>(data));
                size++;
            }
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf (no children). In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (size == 0) {
            return null;
        }
        T dummy = get(data); // will return NoSuchElementException
        //                      if the data is not found
        root = removeH(root, data);
        return dummy;
    }

    /**
     * Helper method of remove()
     * go through the tree iteratively to the location that the data
     * is needed to be removed.
     * @param curr the root of the tree (subtree).
     * @param data the data to remove from the tree.
     * @return the node with new data.
     */
    private BSTNode<T> removeH(BSTNode<T> curr, T data) {
        if (curr == null) { // data not found
            return null;
        }
        if (data.equals(curr.getData())) {
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                // no children
                return null;
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                // one child on the right
                return curr.getRight();
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                // one child on the left
                return curr.getLeft();
            } else {
                // two children
                // BSTNode<T> tempDummyNode = curr;

                // one step to left then go right
                curr.setData(predecessor(curr.getLeft()).getData());
                // from the subtree delete that duplicate predecessor
                // I know I should use dummy node to make it more efficiency
                // but I don't have time  :(
                curr.setLeft(removeH(curr.getLeft(), curr.getData()));
                // return tempDummyNode;
            }
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeH(curr.getLeft(), data));
        } else {
            curr.setRight(removeH(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Helper method of removeH()
     * go through the subtree all the way to right
     * to find the predecessor.
     * @param node the node on the path of right subtree
     * @return the predecessor.
     */
    private BSTNode<T> predecessor(BSTNode<T> node) {
        if (node.getRight() == null) {
            return node;
        } else {
            return predecessor(node.getRight());
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (getH(data, root) != null) {
            return getH(data, root);
        } else {
            throw new java.util.NoSuchElementException(
                    "The data is not found in the tree");
        }
    }

    /**
     * Helper method for get()
     * recursively go throw the tree
     * @param data the data to search for in the tree.
     * @param curr the root of the tree (subtree).
     * @return the data of current node if the data is equal to 'data'.
     * If go through the whole tree but cannot find matching, return null.
     */
    private T getH(T data, BSTNode<T> curr) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().equals(data)) {
            return curr.getData();
        }
        if (data.compareTo(curr.getData()) < 0) {
            return getH(data, curr.getLeft());
        } else {
            return getH(data, curr.getRight());
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        try {
            return get(data) != null;
        } catch (java.util.NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> eleList = new ArrayList<T>();
        if (size == 0) {
            return eleList;
        }
        preorderH(root, eleList);
        return eleList;
    }

    /**
     * Helper method for preorder().
     * Try to get the value on the left subtree branch
     * \first for every subtree
     * @param node the root of the tree (subtree).
     * @param eleList the list waiting for adding elements..
     */
    private void preorderH(BSTNode<T> node, ArrayList<T> eleList) {
        if (node == null) {
            return;
        }
        eleList.add(node.getData());
        preorderH(node.getLeft(), eleList);
        preorderH(node.getRight(), eleList);
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> eleList = new ArrayList<T>();
        if (size == 0) {
            return eleList;
        }
        inorderH(root, eleList);
        return eleList;
    }

    /**
     * Helper method for inorder().
     * Try to get the value on the leftest lowest value first for
     * every subtree,
     * @param node the root of the tree (subtree).
     * @param eleList the list waiting for adding elements..
     */
    private void inorderH(BSTNode<T> node, ArrayList<T> eleList) {
        if (node == null) {
            return;
        }
        inorderH(node.getLeft(), eleList);
        eleList.add(node.getData());
        inorderH(node.getRight(), eleList);
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> eleList = new ArrayList<T>();
        if (size == 0) {
            return eleList;
        }
        postorderH(root, eleList);
        return eleList;
    }


    /**
     * Helper method for postorder().
     * (it is not easy to describe it by words)
     * @param node the root of the tree (subtree).
     * @param eleList the list waiting for adding elements..
     */
    private void postorderH(BSTNode<T> node, ArrayList<T> eleList) {
        if (node == null) {
            return;
        }
        postorderH(node.getLeft(), eleList);
        postorderH(node.getRight(), eleList);
        eleList.add(node.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n). This does not need to be done recursively.
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        ArrayList<T> eleList = new ArrayList<T>();
        if (size == 0) {
            return eleList;
        }
        Queue<BSTNode<T>> eleQueue = new LinkedList<BSTNode<T>>();
        eleQueue.add(root);
        while (!eleQueue.isEmpty()) {
            BSTNode<T> node = eleQueue.remove();
            eleList.add(node.getData());
            if (node.getLeft() != null) {
                eleQueue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                eleQueue.add(node.getRight());
            }
        }
        return eleList;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        BSTNode<T> parent = null;
        return isBSTh(treeRoot, parent);
    }

    /**
     * Helper method of isBST()
     * Do In-Order Traversal of the given tree and store the result
     * in a temp array.
     * Check if the temp array is sorted in ascending order, if it is,
     * then the tree is BST.turn a level order traversal of the tree.
     * @param <T> the generic typing
     * @param node child node
     * @param parent parent of node
     * @return whether a tree is a BST
     */
    private static <T extends Comparable<? super T>> boolean isBSTh(
            BSTNode<T> node, BSTNode<T> parent) {
        if (node != null) {
            if (!isBSTh(node.getLeft(), parent)) {
                return false;
            }
            if (parent != null && node.getData().compareTo(
                    parent.getData()) < 0) {
                return false;
            }
            parent = node;
            return isBSTh(node.getRight(), parent);
        }
        return true;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return heightH(root);
    }

    /**
     * Helper method of height()
     * recursive from every leaf to the root to find the height
     * @param node root of the tree (subtree).
     * @return the height of the root
     */
    private int heightH(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int resultL = heightH(node.getLeft());
        int resultR = heightH(node.getRight());
        if (resultL > resultR) {
            return resultL + 1;
        } else {
            return resultR + 1;
        }
    }

    /**
     * Returns the size of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}