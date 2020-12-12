package com.atstone.algorithm.leetcode.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreedySubject {

    /**
     * 455、分发饼干：https://leetcode-cn.com/problems/assign-cookies/
     *
     * @param children 数组存放每个孩子的饥饿值
     * @param cookies  数组存放每块饼干的尺度
     * @return
     */
    public static int findContentChildren(int[] children, int[] cookies) {
        Arrays.sort(children);
        Arrays.sort(cookies);
        int child = 0, cookie = 0;
        while (child < children.length && cookie < cookies.length) {
            if (children[child] <= cookies[cookie]) child++;
            cookie++;
        }
        return child;
    }

    /**
     * 135、分发糖果：https://leetcode-cn.com/problems/candy/
     *
     * @param ratings 数组存放每个孩子的评分
     * @return 返回总的糖果数
     */
    public static int candy(int[] ratings) {
        int size = ratings.length;
        int[] count = new int[size];
        //先给每个孩子分一颗糖果
        for (int i = 0; i < size; i++) {
            count[i] = 1;
        }
        //从左往右遍历，比较相邻的两孩子评分及其糖果数
        for (int i = 1; i < size; i++) {
            if (ratings[i - 1] < ratings[i]) {
                count[i] = count[i - 1] + 1;
            }
        }
        //从右往左遍历，比较相邻的两孩子评分及其糖果数
        for (int j = size - 1; j > 0; j--) {
            if (ratings[j - 1] > ratings[j]) {
                count[j - 1] = Math.max(count[j - 1], count[j] + 1);
            }
        }
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += count[i];
        }
        return sum;
    }

    /**
     * 435. 无重叠区间：https://leetcode-cn.com/problems/non-overlapping-intervals/
     *
     * @param intervals 区间集合
     * @return 重叠个数
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null) return 0;
        if (intervals.length < 2) return 0;
        Arrays.sort(intervals, (o1, o2) -> o1[1] - o2[1]);
        int total = 0;
        int prev = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prev) {
                total++;
            } else {
                prev = intervals[i][1];
            }
        }
        return total;
    }

    public static int findMinArrowShots(int[][] points) {
        if(points == null|| points.length == 0 || (points.length == 1 && points[0] == null)) return 0;
        Arrays.sort(points, (int[] o1, int[] o2) -> {
            if (o1[1] > o2[1]) {
                return 1;
            } else if (o1[1] < o2[1]) {
                return -1;
            } else {
                return 0;
            }
        });
        int total = 0;
        int prev = points[0][1];
        for (int i = 1; i < points.length; i++) {
            if(points[i][0] <= prev){
                total++;
            }else{
                prev = points[i][1];
            }
        }
        return points.length - total;
    }

    /**
     * 605. 种花问题 :https://leetcode-cn.com/problems/can-place-flowers/
     * @param flowerbed  给定数组（花坛）
     * @param n    需要种的花数量
     * @return     是否能按规则种下这n朵花
     */
    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0, count = 0;
        while (i <  flowerbed.length) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i -1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                count++;
            }
            if(count >= n) return true;
            i++;
        }
        return false;
    }

    /**
     * 763. 划分字母区间:https://leetcode-cn.com/problems/partition-labels/
     * @param s   "ababcbacadefegdehijhklij"
     * @return    切片长度列表
     */
    public static List<Integer> partitionLabels(String s) {
        List<Integer> list = new ArrayList<>();
        int begin = 0;
        int end = 0;
        do{
            int i = begin;
            char c = s.charAt(begin);
            end = s.lastIndexOf(c);
            while (i < end){
                char c1 = s.charAt(i);
                int i1 = s.lastIndexOf(c1);
                if(i1 > end) end = i1;
                i++;
            }
            String substring = s.substring(begin, end+1);
            System.out.println(substring);
            list.add(substring.length());
            begin = end + 1;
        }while (begin < s.length() && end < s.length());
        return list;
    }

    public static void main(String[] args) {
       /* int[] a = {1, 2};
        int[] b = {1, 2, 3};
        int contentChildren = findContentChildren(a, b);
        System.out.println(contentChildren);*/

        /*int[] a = {1, 2, 87, 87, 87, 2, 1};
        int candies = candy(a);
        System.out.println(candies);*/

        /*int[] flowered = {0, 1, 0, 1, 0, 1, 0, 0};
        //int[] flowered = {1, 0, 0, 0, 0, 1};
        boolean b = canPlaceFlowers(flowered, 1);
        System.out.println(b);*/
        //int[][] points =  {{10,16},{2,8},{1,6},{7,12}};
       /* int[][] points =  {{-2147483646,-2147483645},{2147483646,2147483647}};
        int minArrowShots = findMinArrowShots(points);
        System.out.println(minArrowShots);*/

       String s = "ababcbacadefegdehijhklij";
        List<Integer> integers = partitionLabels(s);
        System.out.println(integers);
    }
}
