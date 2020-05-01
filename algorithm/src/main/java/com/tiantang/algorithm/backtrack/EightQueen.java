package com.tiantang.algorithm.backtrack;

/**
 * @author liujinkun
 * @Title: EightQueen
 * @Description: 8皇后问题
 * @date 2020/4/23 11:04 AM
 */
public class EightQueen {

    // 用一个数组存放每一行的皇后的位置，数组的下标表示的是行，元素的值表示的是该行的皇后摆放在哪一列
    public int[] result = new int[8];

    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        eightQueen.putQueen(0);
    }

    /**
     * 往第row行中放入皇后，row最开始从0开始
     * @param row 第几行
     */
    public void putQueen(int row) {
        if (row == 8) {   // 如果等于8表示8个皇后都摆放完了，直接返回即可
            printQueen();   // 打印
            return;
        }
        for (int column = 0; column < 8; column++) {    // 尝试分别将皇后放在第0-7列中
            if (isAccessible(row, column)) {   // 在真正将皇后放进棋盘之前，先判断这个位置能不能摆放皇后
                result[row] = column;   // 放入皇后
                putQueen(row + 1);    // 在下一行中放入皇后
            }
        }
    }

    /**
     * 判断第row行，第column列能不能摆放皇后
     *
     * @param row
     * @param column
     * @return
     */
    private boolean isAccessible(int row, int column) {
        int left = column - 1;  // 对角线左上
        int right = column + 1; // 对角线右上
        // 从当前行的上一行开始，向上遍历 （没有必要判断当前行的下面几行了，因为下面几行肯定没有放皇后啊）
        for (int i = row - 1; i >= 0; i--) {
            if (result[i] == column) return false;   // 当前列上不能有皇后
            if (left >= 0 && result[i] == left) return false;  // 左上对角线上不能有皇后
            if (right < 8 && result[i] == right) return false; // 右上对角线上不能有皇后
            left--;
            right++;
        }
        return true;
    }

    private void printQueen() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (result[row] == column) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println("=========================");
    }
}
