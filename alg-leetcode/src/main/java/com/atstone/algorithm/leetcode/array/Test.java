package com.atstone.algorithm.leetcode.array;

public class Test {
    public static void main(String[] args) {
    }

    /**
     * 83. 删除排序链表中的重复元素
     * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        ListNode pre = head;
        ListNode cur = head.next;
        while (pre != null && cur != null){
            if(pre.val == cur.val){
                pre.next = cur.next;
            }else{
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }
}
