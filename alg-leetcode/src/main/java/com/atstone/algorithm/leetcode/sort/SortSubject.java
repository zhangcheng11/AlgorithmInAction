package com.atstone.algorithm.leetcode.sort;

import java.util.Arrays;
import java.util.Comparator;

public class SortSubject {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length < 2) return intervals;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int count = 0;
        for (int i = 1; i < intervals.length; i++) {
            int a1 = intervals[i][0];
            int a2 = intervals[i][1];
            int b1 = intervals[i - 1][0];
            int b2 = intervals[i - 1][1];

            if (a1 <= b1 && b1 <= a2 && a2 <= b2) {
                intervals[i][0] = a1;
                intervals[i][1] = b2;
                intervals[i - 1] = null;
                count++;
            } else if (a1 <= b1 && b2 <= a2) {
                intervals[i][0] = a1;
                intervals[i][1] = a2;
                intervals[i - 1] = null;
                count++;
            } else if (b1 <= a1 && a2 <= b2) {
                intervals[i][0] = b1;
                intervals[i][1] = b2;
                intervals[i - 1] = null;
                count++;
            } else if (b1 <= a1 && a1 <= b2 && b2 <= a1) {
                intervals[i][0] = b1;
                intervals[i][1] = a2;
                intervals[i - 1] = null;
                count++;
            }
        }

        int[][] result = new int[intervals.length - count][];
        for (int i = 0, j = 0; i < intervals.length; i++) {
            if (intervals[i] != null) {
                result[j++] = intervals[i];
            }
        }
        return result;
    }

    /**
     * 合并两个有序数组
     * https://leetcode-cn.com/problems/merge-sorted-array/
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] leftArr = new int[m];
        for (int i = 0; i < m; i++) {
            if(nums1[i] != 0){
                leftArr[i] = nums1[i];
            }

        }

        int li = 0, ri = 0, ci = 0;
        while (ci < m) {
            if (ri < n && leftArr[ci] > nums2[ri]) {
                nums1[li] = nums2[ri];
                ri++;
                li++;
            } else {
                nums1[li] = leftArr[ci];
                li++;
                ci++;
            }
        }
        for (int j = ri; j < n; j++) {
            nums1[li++] = nums2[j];
        }
        System.out.println(Arrays.toString(nums1));

    }

    public static void main(String[] args) {
        int[] a = {1,3,5,7,0,0,0};
        int[] b = {3,8,9};
        merge(a,4,b,3);
    }
}
