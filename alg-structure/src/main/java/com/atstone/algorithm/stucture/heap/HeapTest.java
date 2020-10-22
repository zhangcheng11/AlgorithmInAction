package com.atstone.algorithm.stucture.heap;

import com.atstone.algorithm.stucture.printer.BinaryTrees;

import java.util.Comparator;

public class HeapTest {

    /**
     * Top k 问题
     */
    public static void topK() {
        //小顶堆结构
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        //找出最大的前k个数
        int k = 5;
        Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93,
                91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
                90, 6, 65, 49, 3, 26, 61, 21, 48};
        for (int i = 0; i < data.length; i++) {
            if (heap.size < k) {
                heap.add(data[i]);
            } else if (data[i] > heap.get()) {
                heap.replace(data[i]);
            }
        }

        BinaryTrees.println(heap);
    }


    public static void main(String[] args) {
        topK();
    }
}
