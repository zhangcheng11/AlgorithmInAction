package com.atstone.algorithm.stucture.list;

import java.util.*;

public class JdkArray {
    public static boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                map.put(arr[i], map.get(i) + 1);
            } else {
                map.put(arr[i], 1);
            }
        }
        Set<Integer> set = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (!set.add(entry.getValue())) return false;
        }
        return true;
    }

     public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) return intervals;
        int count = 0;
        for (int i = 1; i < intervals.length; i++) {
            int a1 = intervals[i - 1][0];
            int a2 = intervals[i - 1][1];
            int b1 = intervals[i][0];
            int b2 = intervals[i][1];
            if (a1 <= b1 && b2 <= a2) {
                intervals[i][0] = a1;
                intervals[i][1] = a2;
                intervals[i - 1] = null;
                count = count + 1;
            } else if (a1 <= b1 && b1 <= a2 && a2 <= b2) {
                intervals[i][0] = a1;
                intervals[i][1] = b2;
                intervals[i - 1] = null;
                count = count + 1;
            } else if (b1 <= a1 && a2 <= b2) {
                intervals[i - 1] = null;
                count = count + 1;
            } else if (b1 <= a1
                    && a1 <= b2
                    && b2 <= a2) {
                intervals[i][0] = b1;
                intervals[i][1] = a2;
                intervals[i - 1] = null;
                count = count + 1;
            }
        }
        int[][] ret = new int[intervals.length-count][];
        for (int i = 0,j= 0; i < intervals.length; i++) {
            if (intervals[i] != null) {
                ret[j++] = intervals[i];
            }
        }
        return ret;
    }


    public static void main(String[] args) {
        //int[] a= {1,2,2};
        //uniqueOccurrences(a);

        //int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        //int[][] intervals = {{1, 4}, {2, 3}, {4, 5}, {8, 10}, {15, 18}};
        int[][] intervals = { {2, 3}, {4, 5}, {8, 10}, {1,10}};
        int[][] ints = merge(intervals);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(Arrays.toString(ints[i]));
        }
    }
}
