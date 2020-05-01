package com.tiantang.algorithm.backtrack;

/**
 * @author liujinkun
 * @Title: LevenshteinDistance
 * @Description: 回溯法解决莱文斯坦距离
 * @date 2020/4/27 4:40 PM
 */
public class LevenshteinDistance {

    private char[] a;

    private char[] b;

    private int m;

    private int n;

    private int minDistance;

    public LevenshteinDistance(String a,String b){
        this.a = a.toCharArray();
        this.b = b.toCharArray();
        this.m = a.length();
        this.n = b.length();
        this.minDistance = Integer.MAX_VALUE;
    }

    public void calLwstDistance(int i,int j, int distance){
        if(i == m || j == n){
            if(i < m){
                distance += (m-i);
            }
            if(j < n){
                distance += (n-j);
            }
            if(distance < minDistance){
                minDistance = distance;
            }
            return;
        }
        if(a[i] == b[j]){
            calLwstDistance(i+1,j+1,distance);
        }else{
            calLwstDistance(i+1,j,distance+1);
            calLwstDistance(i,j+1,distance+1);
            calLwstDistance(i+1,j+1,distance+1);
        }
    }

    public static void main(String[] args) {
        String a = "meet";
        String b = "tnmt";
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance(a,b);
        levenshteinDistance.calLwstDistance(0,0,0);
        System.out.println(levenshteinDistance.minDistance);
    }
}
