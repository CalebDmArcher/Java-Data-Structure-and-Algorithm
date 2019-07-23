import java.util.ArrayList;
/**
 * Your implementation of a max heap.
 *
 * @author Sitong Wu
 * @userid swu321 (i.e. gburdell3)
 * @GTID 903163600 (i.e. 900000000)
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>> {

    // DO NOT ADD OR MODIFY THESE INSTANCE/CLASS VARIABLES.
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of INITIAL_CAPACITY
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MaxHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.backingArray[0] = null;
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the passed in ArrayList before you start the Build Heap Algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        for (T ele: data) {
            if (ele == null) {
                throw new IllegalArgumentException("Data has null element.");
            }
        } // end of exception
        int capArrayList = data.size();
        this.backingArray = (T[]) new Comparable[capArrayList * 2 + 1];
        this.size = capArrayList;
        this.backingArray[0] = null;
        for (int i = 0; i < capArrayList; i++) {
            this.backingArray[i + 1] = data.get(i);
        }
        int index = size / 2;
        for (int i = index; i > 0; i--) {
            downHeap(i);
        }
    }

    /**
     * Private downheap() method for downheap algorithm.
     * Checking whether the relationship between parent and children
     * are legal.
     * Parent should be bigger than the children. If not, swap.
     * @param index is the parent node.
     */
    private void downHeap(int index) {
        while (index * 2 <= size) {
            int leftIndex = index * 2;
            int rightIndex = leftIndex + 1;
            int swapIndex = leftIndex; // if need swap, set the index at
            // left first
            if (rightIndex <= size) {
                // find if need swap, choose the biggest child index
                if (this.backingArray[rightIndex].compareTo(
                        this.backingArray[leftIndex]) > 0) {
                    swapIndex = rightIndex;
                }
            }
            // test whether need to swap
            // swap only when the child is bigger than the parent
            if (this.backingArray[swapIndex].compareTo(
                    this.backingArray[index]) > 0) {
                swap(index, swapIndex);
            }
            // if the element at the swapped position has child
            // then more swapping need to be checked.
            index = swapIndex;
        }
    }

    /**
     * Private swap() method to exchange elements at two positions
     * in the array.
     * @param i one element index.
     * @param j another element index.
     */
    private void swap(int i, int j) {
        T iCopy = this.backingArray[i];
        this.backingArray[i] = this.backingArray[j];
        this.backingArray[j] = iCopy;
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The item is null.");
        }
        this.size++;
        if (this.size == this.backingArray.length) {
            T[] newArray = (T[]) new Comparable[this.backingArray.length * 2];
            newArray[0] = null;
            for (int i = 1; i < this.backingArray.length; i++) {
                newArray[i] = this.backingArray[i];
            }
            this.backingArray = newArray;
        }
        this.backingArray[size] = item;

        int ind = size;
        upHeap(ind);
    }

    /**
     * Private upHeap() method for upheap algorithm.
     * Checking whether the relationship between parent and children
     * are legal.
     * Parent should be bigger than the children. If not, swap.
     * @param index is the child node.
     */
    private void upHeap(int index) {
        // while condition checks whether reach the root
        // whether current element has parent.
        while (index > 1) {
            int parentIndex = index / 2;
            // if child is larger than the parent,
            // parent child switch position.
            if (this.backingArray[index].compareTo(
                    this.backingArray[parentIndex]) > 0) {
                swap(index, parentIndex);
            }
            index = parentIndex;
        }
    }

    /**
     * Removes and returns the max item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException(
                    "The heap is empty.");
        }
        T result = this.backingArray[1];
        this.backingArray[1] = this.backingArray[size];
        this.backingArray[size] = null;
        this.size--;
        downHeap(1);
        return result;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element, null if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            return null;
        } else {
            return this.backingArray[1];
        }
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and rests the backing array to a new array of capacity
     * {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.backingArray[0] = null;
        this.size = 0;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}