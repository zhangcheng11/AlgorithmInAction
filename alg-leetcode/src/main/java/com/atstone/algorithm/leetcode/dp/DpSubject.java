package com.atstone.algorithm.leetcode.dp;

public class DpSubject {

    /**
     * 300. 最长递增子序列:https://leetcode-cn.com/problems/longest-increasing-subsequence/
     *
     * @param nums
     * @return
     */
    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            System.out.println("dp[" + i + "] =" + dp[i]);
            max = Math.max(dp[i], max);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] lit = {10, 2, 2, 5, 1, 7, 101, 18};
        int max = lengthOfLIS(lit);
        System.out.println("最长上升子序列的长度：" + max);

    }
}
