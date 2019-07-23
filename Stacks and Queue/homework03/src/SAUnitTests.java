import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SAUnitTests {

    ArrayQueue<Integer> aq; //AQ
    ArrayStack<Integer> as; //AS
    LinkedQueue<Integer> lq; //LQ
    LinkedStack<Integer> ls; //LS

    private static final int TIMEOUT = 200000000;

    @Before
    public void setUp() {
        aq = new ArrayQueue<>();
        as = new ArrayStack<>();
        lq = new LinkedQueue<>();
        ls = new LinkedStack<>();
    }

    @Test
    public void testArrayQueueInstantiation() {
        aq = new ArrayQueue<>();
        assertEquals("Size is not set to 0 in constructor", 0, aq.size());
        assertArrayEquals("Backing array is not intialized correctly in "
                          + "constructor",
                          new Object[ArrayQueue.INITIAL_CAPACITY],
                          aq.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAQEnequeueNull() {
        aq = new ArrayQueue<>();
        aq.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testAQDequeueEmpty() {
        aq = new ArrayQueue<>();
        aq.dequeue();
    }

    @Test(timeout = TIMEOUT)
    public void testAQPeekEmpty() {
        aq = new ArrayQueue<>();
        assertNull(aq.peek());
    }

    @Test(timeout = 1000000000)
    public void testAQEnqueueAndDequeue() {
        aq = new ArrayQueue<>();
        assertEquals("Size is not zero in an empty array queue", 0, aq.size());
        for (int i = 1; i < 10; i++) {
            aq.enqueue(i);
        }
        //aq = [1,2,3,4,5,6,7,8,9]
        Object[] expected = {1,2,3,4,5,6,7,8,9};
        assertArrayEquals("Array Queue does not enqueue properly", expected,
                          aq.getBackingArray());
        assertEquals("Size is not updated correctly in array queue", 9,
                     aq.size());
        for (int i = 0; i < 8; i++) {
            assertEquals("Array Queue does not dequeue properly (might not be"
                         + " returning the correct element)", expected[i],
                         aq.dequeue());
            expected[i] = null;
            assertEquals("Size is not updated properly when dequeing in array"
                         + " queue", aq.getBackingArray().length - i - 1,
                         aq.size());
        }
        //expected is now: {null x 8, 9};
        assertArrayEquals("Array queue did not dequeue properly (element "
                          + "dequeued might not have been set to null)",
                          expected, aq.getBackingArray());

        for (int i = 10; i < 18; i++) {
            aq.enqueue(i);
            expected[i - 10] = i;
        }
        assertEquals("Array queue does not update size correctly when "
                     + "enqueing after dequeing", 9, aq.size());
        assertArrayEquals("Array queue might not have properly implemented "
                          + "front index", expected, aq.getBackingArray());

        aq.enqueue(18);
        assertEquals("Array Queue did not increase size properly after "
                     + "enqueing at full capacity", 10, aq.size());

        expected = new Object[ArrayQueue.INITIAL_CAPACITY * 2];
        for (int i = 9; i < 19; i++) {
            expected[i - 9] = i;
            if (i - 9 < 10) {
                assertNotNull("Array Queue did not shift elements to the "
                              + "front after calling enqueue at full "
                              + "capacity", aq.getBackingArray()[i - 9]);
            }
        }
        assertArrayEquals("Array Queue did not resize properly", expected,
                          aq.getBackingArray());

        assertEquals("Array Queue might not have set front to zero after "
                     + "resizing array in enqueue", new Integer(9), aq.dequeue());
        aq.enqueue(19);
        aq.enqueue(20);
        assertEquals(11, aq.size());

        while (aq.peek() != 20) {
            aq.dequeue();
        }
        assertEquals(1, aq.size());
        expected = new Object[ArrayQueue.INITIAL_CAPACITY * 2];
        expected[11] = 20;
        assertArrayEquals(expected, aq.getBackingArray());

        aq.dequeue();
        assertEquals("Array Queue does not decrement size properly upon "
                     + "calling dequeue", 0, aq.size());
        expected = new Object[ArrayQueue.INITIAL_CAPACITY * 2];
        assertArrayEquals(expected, aq.getBackingArray());

        for (int i = 1; i <= ArrayQueue.INITIAL_CAPACITY * 2; i++) {
            aq.enqueue(i);
            expected[i - 1] = i;
        }
        assertArrayEquals(expected, aq.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testAQPeek() {
        aq = new ArrayQueue<>();

        Object[] expected = new Object[ArrayQueue.INITIAL_CAPACITY * 2];
        for (int i = 1; i <= 10; i++) {
            aq.enqueue(i);
            expected[i - 1] = i;
        }
        assertArrayEquals(expected, aq.getBackingArray());
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Array Queue does not peek properly", expected[i],
                         aq.peek());
            if (aq.size() != 0) {
                aq.dequeue();
            }
        }
        assertEquals("Array Queue does not update size correctly", 0,
                     aq.size());
    }

    @Test(timeout = TIMEOUT)
    public void testASPeek() {
        as = new ArrayStack<>();

        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY * 2];
        for (int i = 1; i <= 10; i++) {
            as.push(i);
            expected[i - 1] = i;
        }
        assertArrayEquals(expected, as.getBackingArray());
        for (int i = 9; i >= 0; i--) {
            assertEquals("Array Stack does not peek properly", expected[i],
                         as.peek());
            if (as.size() != 0) {
                as.pop();
            }
        }
        assertEquals("Array Stack does not update size correctly", 0,
                     as.size());
    }

    @Test(timeout = TIMEOUT)
    public void testASPushAndPop() {
        as = new ArrayStack<>();
        assertEquals(0, as.size());
        Object[] expected = new Object[ArrayStack.INITIAL_CAPACITY];
        for (int i = 1; i <= 9; i++) {
            as.push(i);
            expected[i - 1] = i;
        }
        assertArrayEquals(expected, as.getBackingArray());
        as.push(10);
        assertEquals(10, as.size());
        assertEquals(18, as.getBackingArray().length);
        expected = new Object[18];
        for (int i = 1; i <= as.size(); i++) {
            expected[i - 1] = i;
        }
        assertArrayEquals(expected, as.getBackingArray());
        for (int i = 9; i >= 0; i--) {
            assertEquals(expected[i], as.pop());
            expected[i] = null;
        }
        assertArrayEquals(expected, as.getBackingArray());
        assertEquals(0, as.size());
    }

    @Test(timeout = TIMEOUT)
    public void testLQEnqueueDequeuePeek() {
        lq = new LinkedQueue<>();
        assertEquals(0, lq.size());
        assertNull(lq.getHead());
        assertNull(lq.getTail());
        assertNull(lq.peek());

        // Handles the cases for size 1 and 0
        lq.enqueue(1);
        assertEquals((Integer) 1, lq.getHead().getData());
        assertEquals((Integer) 1, lq.getTail().getData());
        assertEquals((Integer) 1, lq.peek());
        assertEquals(1, lq.size());
        assertEquals((Integer) 1, lq.dequeue());
        assertEquals(0, lq.size());
        assertNull(lq.getHead());
        assertNull(lq.getTail());

        lq.enqueue(1);
        lq.enqueue(2);
        lq.enqueue(3);

        LinkedNode<Integer> curr = lq.getHead();
        int i = 1;
        while (curr != null) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
            i++;
        }

        assertEquals(3, lq.size());
        assertEquals((Integer) 1, lq.getHead().getData());
        assertEquals((Integer) 3, lq.getTail().getData());
        assertEquals((Integer) 1, lq.dequeue());
        assertEquals(2, lq.size());
        assertEquals((Integer) 2, lq.getHead().getData());
        assertEquals((Integer) 3, lq.getTail().getData());
        assertEquals((Integer) 2, lq.peek());
        assertEquals((Integer) 2, lq.dequeue());
        assertEquals(1, lq.size());
        assertEquals((Integer) 3, lq.peek());
        assertEquals(lq.getHead(), lq.getTail());
        assertEquals((Integer) 3, lq.getHead().getData());
        assertEquals((Integer) 3, lq.getTail().getData());
        assertEquals((Integer) 3, lq.dequeue());
        assertEquals(0, lq.size());
        assertNull(lq.peek());
        assertNull(lq.getHead());
        assertNull(lq.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testLSPushPopPeek() {
        ls = new LinkedStack<>();
        assertEquals(0, ls.size());
        assertNull(ls.getHead());
        assertNull(ls.peek());

        ls.push(1);
        assertEquals(1, ls.size());
        assertEquals((Integer) 1, ls.peek());
        assertEquals((Integer) 1, ls.getHead().getData());
        assertEquals((Integer) 1, ls.pop());
        assertNull(ls.getHead());
        assertEquals(0, ls.size());

        ls.push(1);
        ls.push(2);
        ls.push(3);

        LinkedNode<Integer> curr = lq.getHead();
        int i = 3;
        while (curr != null) {
            assertEquals((Integer) i, curr.getData());
            curr = curr.getNext();
            i--;
        }

        assertEquals(3, ls.size());
        assertEquals((Integer) 3, ls.peek());
        assertEquals((Integer) 3, ls.getHead().getData());
        assertEquals((Integer) 3, ls.pop());
        assertEquals((Integer) 2, ls.getHead().getData());
        assertEquals((Integer) 2, ls.pop());
        assertEquals(1, ls.size());
        assertEquals((Integer) 1, ls.peek());
        assertEquals((Integer) 1, ls.pop());
        assertEquals(0, ls.size());
        assertNull(ls.getHead());
        assertNull(ls.peek());
    }


}
