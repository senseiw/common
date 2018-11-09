package com.ljl.base;
/**
 * Created by liujialin on 2018/11/9.
 */
public class Pair<L,R>{
    private L left;
    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public L getKey() {
        return left;
    }

    public R getValue() {
        return right;
    }
}
