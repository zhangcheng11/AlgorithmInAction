package com.atstone.algorithm.leetcode.linkedList;

public class LinkedListSubject {
    public static void main(String[] args) {

    }



    /**
     * 206. 反转链表
     * 解题思路：双指针 + 临时节点
     * https://leetcode-cn.com/problems/reverse-linked-list/
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null){
            ListNode tmpNode = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmpNode;
        }
        return prev;
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
