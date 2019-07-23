/**
 * Your implementation of a circular singly linked list.
 *
 * @author Sitong Wu
 * @userid swu321 (e.g. gburdell3)
 * @GTID 903163600 (e.g. 900000000)
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("The index value is illegal.");
        } else {
            if (size == 0) {
                System.out.println("enter size=0");
                head = new LinkedListNode<T>(data, null);
                head.setNext(head);

            } else {
                //System.out.println("enter index not 0");
                if (index == 0) {
                    System.out.println("enter index=0");
                    LinkedListNode<T> newFirts =
                            new LinkedListNode<T>(head.getData(),
                            head.getNext());
                    head.setNext(newFirts);
                    head.setData(data);

                } else {
                    System.out.println("enter");
                    LinkedListNode<T> curr = head;
                    for (int i = 0; i < index - 1; i++) {
                        curr = curr.getNext();
                    }
                    LinkedListNode<T> newNode =
                            new LinkedListNode<T>(data, curr.getNext());
                    curr.setNext(newNode);
                }
            }

            LinkedListNode<T> curr = head;
            System.out.println(curr.getData());
            for (int i = 0; i < size - 1; i++) {
                curr = curr.getNext();

                System.out.println(curr.getData());
            }

            System.out.println(" ");

            size++;
        }


    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size != 0) {
            LinkedListNode<T> newSecond =
                    new LinkedListNode<T>(head.getData(),
                            head.getNext());
            head.setNext(newSecond);
            head.setData(data);
        } else {
            head = new LinkedListNode<T>(data, null);
            head.setNext(head);
        }

        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            head = new LinkedListNode<T>(data, null);
            head.setNext(head);
        } else  {
            LinkedListNode<T> newFirst =
                    new LinkedListNode<T>(head.getData(),
                            head.getNext());
            head.setNext(newFirst);
            head.setData(data);
            head = head.getNext();
            //this.addAtIndex(size, data);
        }

        size++;
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index value is illegal.");
        }
        T data = null;
        if (size == 0) {
            return null;
        } else {
            if (index == 0) {
                data = head.getData();
                head.setData(head.getNext().getData());
                head.setNext(head.getNext().getNext());
            } else {
                LinkedListNode<T> curr = head;
                for (int i = 0; i < index - 1; i++) {
                    curr = curr.getNext();
                }
                data = curr.getNext().getData();
                curr.setNext(curr.getNext().getNext());
                //System.out.println(data);
                //System.out.println(curr.getData());
            }
            size--;
            return data;
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        T data = head.getData();
        head.setData(head.getNext().getData());
        head.setNext(head.getNext().getNext());
        size--;
        return data;
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }
        T data = null;
        LinkedListNode<T> curr = head;
        for (int i = 0; i < size - 2; i++) {
            curr = curr.getNext();
        }
        data = curr.getNext().getData();
        curr.setNext(curr.getNext().getNext());
        //System.out.println(data);
        //System.out.println(curr.getData());
        size--;
        return data;
    }

    /**
     * Removes the last copy of the given data from the list.
     *
     * Must be O(n) for all cases.
     *
     * @param data the data to be removed from the list
     * @return the removed data occurrence from the list itself (not the data
     * passed in), null if no occurrence
     * @throws java.lang.IllegalArgumentException if data is null
     */

    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            return null;
        }

        T result = null;
        int locationRecord = 0;
        if (size == 1) {
            result = data.equals(head.getData()) ? head.getData() : null;
        }

        //int locationRecord = 0;
        LinkedListNode<T> curr = head;
        for (int i = 1; i < this.size - 1; i++) {
            curr = curr.getNext();
            locationRecord = curr.getData().equals(data) ? i : locationRecord;
            result = curr.getData();
        }
        Boolean doRemove = false;
        if (locationRecord == 0) {
            System.out.println("enter 0");
            result = data.equals(head.getData()) ? head.getData() : null;
            doRemove = data.equals(head.getData()) ? true : false;
        }
        if (doRemove) {
            this.removeAtIndex(locationRecord);
        }

        if (result != null && size != 0) {
            size--;
        }

        return result;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting index 0 should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T data = head.getData();
        LinkedListNode<T> curr = head;
        if (index != 0) {
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            data = curr.getData();
        }
        return data;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        Object[] result = new Object[size];
        result[0] = head.getData();
        LinkedListNode<T> curr = head;
        for (int i = 0; i < this.size - 1; i++) {
            curr = curr.getNext();
            result[i + 1] = curr.getData();
        }
        return result;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}