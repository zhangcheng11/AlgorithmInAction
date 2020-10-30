package com.atstone.algorithm.stucture.sort;

/**
 * 快速排序
 * 关键点：找轴点位置
 * 步骤：
 * 1、备份begin位置元素作为轴点元素（begin指向序列第一个元素）
 * 2、end指向最后一个元素
 * 3、先从右往左扫描，
 * 若end位置的元素大于轴点位置元素，end--,继续从右往左；
 * 若end位置元素小于轴点，将end位置元素覆盖begin位置元素，begin++,改为从左往右扫面
 * 4、直至begin == end ，找到了轴点的位置。
 */
public class QuickSort extends Sort {
    @Override
    protected void sort() {
        sort(0, array.length);
    }

    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        //确定轴点的位置
        int mid = pivotIndex(begin, end);

        // 对子序列进行快速排序
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private int pivotIndex(int begin, int end) {
        //随机选择一个位置与begin位置元素交换（避免每次取第一个元素）
        swap(begin, begin + (int) Math.random() * (end - begin));
        // 备份begin位置的元素
        int pivot = array[begin];
        // end指向最后一个元素
        end--;

        while (begin < end) {
            while (begin < end) {
                if (cmp(pivot, array[end]) < 0) {//右边元素大于轴点元素
                    end--;
                } else {//右边元素 <= 轴点元素
                    array[begin] = array[end];
                    begin++;
                    break;
                }
            }

            while (begin < end) {
                if (cmp(array[begin], pivot) < 0) {//左边元素 < 轴点元素
                    begin++;
                } else {//左边元素 >= 轴点元素
                    array[end] = array[begin];
                    end--;
                    break;
                }
            }
        }
        //begin == end
        // 将轴点元素放入最终的位置
        array[begin] = pivot;
        // 返回轴点元素的位置
        return begin;
    }
}
