package com.example.learn2020;

public class Search {

    public static void main(String[] args) {
        int[] arr = {4,5,6,7,0,1,2};
        System.out.println(search(arr,0));
    }


    public static int search(int[] nums, int target) {
        int point = 0;
        for (int i = 0;i < nums.length;i ++) {
            if (nums[i] > nums[i + 1]) {
                point = i;
                break;
            }
        }
        int position;
        position = besearch(nums,0,point,target);
        if (-1 != position) return position;
        return besearch(nums,point + 1,nums.length - 1,target);

    }

    private static int besearch (int[] nums,int lo,int hi,int target) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) {
                lo = mid + 1;
            } else if (nums[mid] > target) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;

    }
}
