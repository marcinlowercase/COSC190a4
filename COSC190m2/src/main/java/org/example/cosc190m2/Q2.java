package org.example.cosc190m2;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Q2 {

    public static int totalSum(int[][] naVals) {
        // Replace with a thread-based code that uses one thread for each row to add up the values.
        // For example, for a 3x8 array, you must have 3 threads (one for each row summation).
        // The 2D array can be a jagged array i.e., each row may have different number of columns.

        final int[] finalMax = {0};
        ReentrantLock lockSum = new ReentrantLock();

        //Replace with a thread-based code that uses a thread for each row to find the maximum from the values.
        try (ExecutorService obPool = Executors.newCachedThreadPool()) {

            int counter = 1;
            for (int[] naRow : naVals) {

                int finalCounter = counter;

                System.out.println("Executing thread: " + counter++);
                obPool.execute(() -> {

                    int maxFromThread = sumRow(naRow);

                    lockSum.lock();
//                    if (maxFromThread > finalMax[0]) {
//                        finalMax[0] = maxFromThread;
//                    }

                    finalMax[0] += maxFromThread;
                    lockSum.unlock();

                    // System.out.println("Thread " + finalCounter + " -> Max: " + maxFromThread);
                    System.out.println("Thread " + finalCounter + " -> Sum: " + maxFromThread);

                });

            }

            obPool.shutdown();

            while (!obPool.isTerminated()) ;
        }

        return finalMax[0];
    }


    public static int sumRow(int[] naRow) {

        return Arrays.stream(naRow).sum();

    }

    public static void main(String[] args) {

        int[][] array = {
                {1, 1, 1},
                {1, 1},
                {1, 1, 1, 1, 1},
                {1, 1}
        };

        System.out.println(sumRow(array[0]));
        System.out.println(totalSum(array));
    }
}
