package com.test.starter.test;

import org.apache.ibatis.reflection.ArrayUtil;

import java.text.SimpleDateFormat;

/**
 * @author: xq
 * @Date: 2021/03/01 15:09
 */
public class BubbleAndInsertionSort {
    public static void main(String[] args) {
        int[] array = {3,2,1,5,33,45,6,8,4};
//        bubbleSort(array);
        insertionSort(array);
        System.out.println(ArrayUtil.toString(array));

        // 1百万个数组，每个数组10个元素
        int[][] bubbleArray = new int[1000000][10];
        int[][] insertionArray = new int[1000000][10];

        // 随机生成数据
        int max = 1000;
        int min = 1;
        for (int l = 0; l < 1000000; l++) {
            for (int j = 0; j < 10; j++) {
                int num = (int) (((max-min) * Math.random()) + min);
                bubbleArray[l][j] = num;
                insertionArray[l][j] = num;
            }
//            System.out.println(ArrayUtil.toString(bubbleArray[l]));
//            System.out.println(ArrayUtil.toString(insertionArray[l]));
        }

        // 验证排序时间耗时
        long currentOfBubble = System.currentTimeMillis();
        for (int l = 0; l < 1000000; l++) {
            bubbleSort(bubbleArray[l]);
        }
        long endOfBubble = System.currentTimeMillis();
        long finishOfBubble = endOfBubble - currentOfBubble;

        long currentOfInsertion = System.currentTimeMillis();
        for (int l = 0; l < 1000000; l++) {
            insertionSort(insertionArray[l]);
        }
        long endOfInsertion = System.currentTimeMillis();
        long finishOfInsertion = endOfInsertion - currentOfInsertion;


//        SimpleDateFormat sdf = new SimpleDateFormat(String.valueOf(finishOfBubble));

        System.out.println("bubbleSort 速度：" + finishOfBubble);
        System.out.println("insertionSort 速度：" + finishOfInsertion);
    }

    /**
     * 冒泡排序
     * @param array
     */
    public static void bubbleSort(int[] array){
        if (array.length <= 0) return;

        for (int i = 0; i < array.length; i++) {
            // 提前结束冒泡的标志位
            boolean flag = false;
            for (int j = 0; j < array.length-i-1; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                    // 表示有数据交换
                    flag = true;
                }
            }
            // 表示没有数据交换，提前退出
            if (!flag) break;
        }
    }


    /**
     * 插入排序
     * @param array
     */
    public static void insertionSort(int[] array){
        if (array.length <= 1) return;

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (array[j] > value) {
                    // 数据移动
                    array[j+1] = array[j];
                } else {
                    break;
                }
            }
            // 插入数据
            array[j+1] = value;
        }
    }
}
