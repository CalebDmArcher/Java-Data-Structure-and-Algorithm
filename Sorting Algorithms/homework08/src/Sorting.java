import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Sitong Wu
 * @userid swu321 (i.e. gburdell3)
 * @GTID 903161600 (i.e. 900000000)
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException(
                    "The array or comparator is null.");
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    swap(arr, j, j - 1);
                } else {
                    j = -1;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException(
                    "The array or comparator is null.");
        }
        T min;
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            index = i;
            min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(min, arr[j]) > 0) {
                    min = arr[j];
                    index = j;
                }
            }
            swap(arr, i, index);
        }
    }

    /**
     * swap() method for swap two elements in an array
     * @param <T> data type to sort
     * @param arr is the array having two elements need to be swapped
     * @param i1 is one index
     * @param i2 is another index
     */
    private static <T> void swap(T[] arr, int i1, int i2) {
        T temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException(
                    "The array or comparator is null.");
        }
        if (arr.length > 1) {
            int middleIndex = (int) (arr.length / 2);
            int leftSize = (int) (arr.length / 2);
            int rightSize = arr.length - leftSize;
            T[] leftArr = (T[]) new Object[leftSize];
            T[] rightArr = (T[]) new Object[rightSize];
            for (int i = 0; i < leftSize; i++) {
                leftArr[i] = arr[i];
            }
            for (int i = leftSize; i < arr.length; i++) {
                rightArr[i - leftSize] = arr[i];
            }
            // recursively divide the array
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);

            int leftindex = 0;
            int rightindex = 0;
            int currentindex = 0;
            while (leftindex < middleIndex
                    && rightindex < arr.length - middleIndex) {
                if (comparator.compare(leftArr[leftindex],
                        rightArr[rightindex]) <= 0) {
                    arr[currentindex] = leftArr[leftindex];
                    leftindex++;
                } else {
                    arr[currentindex] = rightArr[rightindex];
                    rightindex++;
                }
                currentindex++;
            }
            // rightArr is invalid
            while (leftindex < middleIndex) {
                arr[currentindex] = leftArr[leftindex];
                leftindex++;
                currentindex++;
            }
            // leftArr is invalid
            while (rightindex < arr.length - middleIndex) {
                arr[currentindex] = rightArr[rightindex];
                rightindex++;
                currentindex++;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if ((arr == null) || (comparator == null) || (rand == null)) {
            throw new IllegalArgumentException(
                    "the array or comparator or rand is null.");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length);
    }

    /**
     * Helper method for quickSort()
     * @param arr Array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand random number
     * @param startIndex starting index to sort from
     * @param endIndex last index to sort till
     * @param <T> data type to sort
     */
    private static <T> void quickSortHelper(T[] arr,
                                            Comparator<T> comparator,
                                            Random rand, int startIndex,
                                            int endIndex) {
        if (endIndex - startIndex >= 2) {
            int pivot = rand.nextInt(endIndex - startIndex)
                    + startIndex;
            swap(arr, startIndex, pivot);
            int i = startIndex + 1;
            int j = endIndex - 1;
            while (i <= j) {
                if (comparator.compare(arr[i], arr[startIndex]) <= 0) {
                    i++;
                } else {
                    if (comparator.compare(arr[j], arr[startIndex]) > 0) {
                        j--;
                    } else {
                        swap(arr, i++, j--);
                    }
                }
            }
            swap(arr, startIndex, j);
            if (startIndex < j) {
                quickSortHelper(arr, comparator, rand, startIndex, j);
            }
            if (endIndex > i) {
                quickSortHelper(arr, comparator, rand, i, endIndex);
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null.");
        }
        //buckets -9 to 9
        LinkedList<Integer>[] buckets = new LinkedList[19];
        //find the longest length
        int max = arr[0];
        int min = arr[0];
        int exp = 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        int longestLength = 0;
        while ((max != 0) || (min != 0)) {
            max = max / 10;
            min = min / 10;
            longestLength++;
        }
        int length = arr.length;

        for (int i = 1; i <= longestLength; i++) {
            for (int j = 0; j <= length - 1; j++) {
                int bucket = (arr[j] / exp) % 10;
                bucket += 9;
                if (buckets[bucket] == null) {
                    buckets[bucket] = new LinkedList<Integer>();
                }
                buckets[bucket].add(arr[j]);
            }
            int index = 0;
            for (int bucket = 0; bucket < 19; bucket++) {
                if (buckets[bucket] == null) {
                    buckets[bucket] = new LinkedList<Integer>();
                }
                while (!buckets[bucket].isEmpty()) {
                    arr[index] = buckets[bucket].removeFirst();
                    index++;
                }
            }
            exp *= 10;
        }
    }
}