/**
 * Your implementation of an array-backed queue.
 *
 * @author Sitong Wu
 * @userid swu321 (e.g. gburdell3)
 * @GTID 903163600 (e.g. 900000000)
 * @version 1.0
 */
public class ArrayQueue<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * The initial capacity of a queue with fixed-size backing storage.
     */
    public static final int INITIAL_CAPACITY = 9;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.front = 0;
        this.size = 0;
    }

    /**
     * Adds the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * resize it to double the current length. If a resize is necessary,
     * you should copy elements to the front of the new array and reset
     * front to 0.
     *
     * This method should be implemented in amortized O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        int currCap = backingArray.length;
        if (size < currCap) {
            // Do not need to double the array capacitance
            if (front == 0) {
                // front is at index 0
                backingArray[size] = data;
            } else {
                // front is in the middle of the array
                backingArray[(front + size) % currCap] = data;
            }
            size++;
        } else {
            // Do need to double the array capacitance
            T[] newArray = (T[]) new Object[currCap * 2];
            if (front == 0) {
                // if front is at index 0
                for (int i = 0; i < currCap; i++) {
                    newArray[i] = backingArray[i];
                }
                newArray[size] = data;
                backingArray = newArray;
            } else {
                // if front is in the middle
                // when resize the array, put front to index 0
                for (int i = front; i < currCap; i++) {
                    newArray[i - front] = backingArray[i];
                }
                for (int i = 0; i < front; i++) {
                    newArray[i + (currCap - front)] = backingArray[i];
                }
                backingArray = newArray;
                backingArray[size] = data;
                front = 0;
            }
            size++;
        }
    }

    /**
     * Removes the data from the front of the queue.
     *
     * Do not shrink the backing array. If the queue becomes empty as a result
     * of this call, you should explicitly reset front to 0.
     *
     * You should replace any spots that you dequeue from with null. Failure to
     * do so can result in a loss of points.
     *
     * This method should be implemented in O(1) time.
     *
     * See the homework pdf for more information on implementation details.
     *
     * @return the data from the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The queue is empty");
        }
        int currCap = backingArray.length;
        T result = backingArray[front];
        backingArray[front] = null;
        if (size == 1) {
            front = 0;
            size = 0;
        } else if (front < currCap - 1) {
            // if front is not at the end
            front++;
            size--;
        } else {
            // if front is at the end
            // after remove the thing at front
            // front cycles to the beginning of the array
            front = 0;
            size--;
        }
        //size--;
        return result;
    }

    /**
     * Retrieves the next data to be dequeued without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return backingArray[front];
        }
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}