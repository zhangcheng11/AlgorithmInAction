package com.atstone.algorithm.stucture.sort;

public class SelectSort extends Sort {

    public static void selectedSort(int[] arr) {
        for (int end = arr.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (arr[begin] >= arr[maxIndex]) {
                    maxIndex = begin;
                }
            }
            int tmp = arr[maxIndex];
            arr[maxIndex] = arr[end];
            arr[end] = tmp;
        }
    }

    public static void main(String[] args) {
        byte a = (byte) 0x82;
        System.out.println(a);
    }

    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] >= array[maxIndex]) {
                    maxIndex = begin;
                }
            }
            swap(array[maxIndex], array[end]);
        }
    }


}
