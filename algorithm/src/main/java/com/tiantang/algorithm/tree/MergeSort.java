package com.tiantang.algorithm.tree;

import java.util.Arrays;

/**
 * @author liujinkun
 * @Title: MergeSort
 * @Description: 归并排序
 * @date 2020/3/31 11:05 AM
 */
public class MergeSort {

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        int[] data = {4, 9, 5, 6, 8, 0, 3, 7, 1, 20};
        mergeSort.mergeSort(data, 0, data.length - 1);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 时间复杂度：n*lgn
     * @param data
     * @param left
     * @param right
     */
    public void mergeSort(int[] data, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 拆分左边
            mergeSort(data, left, mid);
            // 拆分右边
            mergeSort(data, mid + 1, right);

            // 合并
            merge(data, left, right, mid);
        }
    }

    private void merge(int[] data, int left, int right, int mid) {
        int point1 = left;  // 左指针
        int point2 = mid + 1;   // 右指针
        int[] tmp = new int[data.length];
        int loc = left;     // 用来标记我们当前比较出来的数保存到tmp数组中的哪个位置
        while (point1 <= mid && point2 <= right) {
            if (data[point1] < data[point2]) {
                tmp[loc++] = data[point1];
                point1++;
            } else {
                tmp[loc++] = data[point2];
                point2++;
            }
        }
        while (point1 <= mid) {
            tmp[loc++] = data[point1];
            point1++;
        }
        while (point2 <= right) {
            tmp[loc++] = data[point2];
            point2++;
        }
        for (int j = left; j <= right; j++) {
            data[j] = tmp[j];
        }
    }

}
