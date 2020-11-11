package com.atstone.algorithm.leetcode.sort;

import javax.script.AbstractScriptEngine;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
            if (nums1[i] != 0) {
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

    /**
     * 21.合并两个链表
     * https://leetcode-cn.com/problems/merge-two-sorted-lists/
     */
    public static ListNode mergeTwoLists(ListNode li, ListNode l2) {
        ListNode root = null, cur = null;
        ListNode cur1 = li;
        ListNode cur2 = l2;
        if (cur1 == null) return cur2;
        if (cur2 == null) return cur1;
        while (cur1 != null && cur2 != null) {
            if (cur1.val < cur2.val) {
                if (root == null) {
                    root = new ListNode(cur1.val);
                    cur = root;
                } else {
                    cur.next = new ListNode(cur1.val);
                    cur = cur.next;
                }
                cur1 = cur1.next;
            } else {
                if (root == null) {
                    root = new ListNode(cur2.val);
                    cur = root;
                } else {
                    cur.next = new ListNode(cur2.val);
                    cur = cur.next;
                }
                cur2 = cur2.next;
            }
        }
        if (cur1 != null && cur2 == null) {
            cur.next = new ListNode(cur1.val, cur1.next);
        }
        if (cur1 == null && cur2 != null) {
            cur.next = new ListNode(cur2.val, cur2.next);
        }

        return root;
    }

    /**
     * 75. 颜色分类
     * https://leetcode-cn.com/problems/sort-colors/
     */
    public static void sortColors(int[] nums) {
        int begin = 0;
        for (int end = nums.length - 1; end >= 0; end--) {
            int maxIndex = begin;
            for (int j = begin; j <= end; j++) {
                if (nums[j] > nums[maxIndex]) maxIndex = j;
            }
            int tmp = nums[maxIndex];
            nums[maxIndex] = nums[end];
            nums[end] = tmp;
        }

    }

    /**
     * 单指针求解
     */
    public static void sortColors2(int[] nums) {
        int n = nums.length;
        int ptr = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                int tmp = nums[i];
                nums[i] = nums[ptr];
                nums[ptr] = tmp;
                ptr++;
            }
        }

        for (int i = ptr; i < n; i++) {
            if (nums[i] == 1) {
                int tmp = nums[i];
                nums[i] = nums[ptr];
                nums[ptr] = tmp;
                ptr++;
            }
        }

    }


    /**
     * 链表插入排序
     */
    public static ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null) return head;
        //处理头节点，便于在一个位置插入的情况
        ListNode newHead = new ListNode(Integer.MIN_VALUE);
        newHead.next = head;
        head = newHead;

        ListNode cur = head.next;//当前需要插入的节点
        ListNode tail = head;//有序链表的尾部节点

        ListNode prev,t;//遍历有序链表时的指针


        ListNode next = null;
        while (cur != null){
            next = cur.next;
            if(cur.val >= tail.val){//当前元素大于尾部元素，直接接在尾部
                tail.next = cur;
                tail = cur;
            }else{//当前元素小于尾部元素，从头开始遍历，找到第一个大于cur的元素。
                prev = head;
                t= head.next;

                while (t != cur && t.val < cur.val){
                    prev = t;
                    t = t.next;
                }
                //找到了插入位置，开始插入cur节点
                prev.next = cur;
                cur.next = t;
            }
            cur = next;
        }
        tail.next = null;
        return head.next;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public static void main(String[] args) {
        /*ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2, new ListNode(4));

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3, new ListNode(4));

        ListNode listNode = mergeTwoLists(l1, l2);
        System.out.println(listNode);*/

      /* int[] nums = {2,0,2,1,1,0};
         sortColors2(nums);
         System.out.println(Arrays.toString(nums));*/


        ListNode l1 = new ListNode(-1);
        l1.next = new ListNode(5, new ListNode(3, new ListNode(4,new ListNode(0))));

        ListNode listNode = insertionSortList(l1);
        System.out.println(listNode);

    }
}
