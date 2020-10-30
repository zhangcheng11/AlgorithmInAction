package com.atstone.algorithm.stucture.sort;

import java.util.Arrays;

public class BubbleSort extends Sort {

    public static void bubbleSort(int[] arr) {
        for (int end = arr.length - 1; end > 0; end--) {
            int sortedIndex = 0;//标记最后交换的位置,优化冒泡排序
            for (int begin = 1; begin <= end; begin++) {
                if (arr[begin - 1] > arr[begin]) {
                    int tmp = arr[begin - 1];
                    arr[begin - 1] = arr[begin];
                    arr[begin] = tmp;
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] a = {1, 9, 2, 7, 4, 5};
        bubbleSort(a);
    }




    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 0;//标记最后交换的位置,优化冒泡排序
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(array[begin - 1], array[begin]) > 0) {
                    swap(begin, begin - 1);
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }
}
