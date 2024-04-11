package com.puzzlesapi.comparators;

import java.util.Comparator;

/**
 * A comparator that compares two integers in decreasing order.
 */
public class DecreasingIntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer integer, Integer t1) {
        return -1 * integer.compareTo(t1);
    }

}
