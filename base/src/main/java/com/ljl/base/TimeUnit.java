package com.ljl.base;

/**
 * Created by liujialin on 2018/11/9.
 */
public enum TimeUnit{
    DAY(null,null),HOUR(24l,DAY),MINUTE(60l,HOUR),SECOND(60l,MINUTE),MS(1000l,SECOND);

    private Long currentReversalValue;
    private TimeUnit next;

    TimeUnit(Long currentReversalValue,TimeUnit next){
        this.currentReversalValue = currentReversalValue;
        this.next = next;
    }

    public Long getCurrentReversalValue() {
        return currentReversalValue;
    }

    public TimeUnit getNext() {
        return next;
    }

    public boolean nextInclude(TimeUnit target){
        TimeUnit current = this;
        while (current != null){
            if (current == target){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public static double scale(TimeUnit from, TimeUnit to){
        if (!from.nextInclude(to)){
            return 1/scale(to,from);
        }
        TimeUnit current = from;
        long result = 1;
        while (current != null){
            if (current == to){
                break;
            }
            result *= current.currentReversalValue;
            current = current.next;
        }
        return result;
    }
}
