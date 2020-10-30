package com.atstone.algorithm.stucture.sort;

public class HeapSort extends Sort {
    int heapSize;

    @Override
    protected void sort() {
        //原地建堆
        heapSize = array.length;
        for (int i = (heapSize >> 1) - 1; i > 0; i--) {
            siftDown(i);
        }
        while (heapSize > 1) {
            //交换堆顶元素和尾部元素
            swap(0, heapSize - 1);
            heapSize--;

            //对0位置元素进行siftDown(恢复堆的性质)
            siftDown(0);
        }
    }


    private void siftDown(int index) {
        Integer element = array[index];
        int half = heapSize >> 1;
        //index < 第一个叶子节点的索引(即非叶子节点的数量)   ==> floor[n/2]
        while (index < half) {//必须保证index位置是非叶子节点
            //index的节点有两种情况：1：只有左子节点，2：同时有左右子节点
            int childIndex = (index << 1) + 1;
            Integer child = array[childIndex];
            //右子节点索引
            int rightIndex = childIndex + 1;

            //选出左右子节点最大的
            if (rightIndex < heapSize && cmpElements(array[rightIndex], child) > 0) {
                childIndex = rightIndex;
                child = array[childIndex];
            }

            if (cmpElements(element, child) >= 0) break;

            array[index] = child;
            //重新设置index
            index = childIndex;
        }
        array[index] = element;
    }
}
