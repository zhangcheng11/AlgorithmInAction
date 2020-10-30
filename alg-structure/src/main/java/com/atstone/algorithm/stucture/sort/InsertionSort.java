package com.atstone.algorithm.stucture.sort;

public class InsertionSort extends Sort {

    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            while (cur > 0 && cmp(cur, cur - 1) < 0) {
                swap(cur, cur - 1);
                cur--;
            }
        }
    }

    /**
     * 插入排序优化，通过移位来实现插入排序
     */
    private void sort2() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            int v = array[cur];
            while (cur > 0 && cmp(v, cur - 1) < 0) {
                array[cur] = array[cur - 1];
                cur--;
            }
            array[cur] = v;
        }
    }

    /**
     *  通过二分查找来优化插入排序算法
     */
    public void sort3() {
        for (int i = 0; i < array.length; i++) {
            insert(i, search(i));
        }
    }


    private void insert(int source,int destPos){
        for (int i = source; i > destPos; i--) {
            array[i] = array[i -1];
        }
        array[destPos] = array[source];
    }

    private int search(int index) {
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmp(index, mid) < 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

}
