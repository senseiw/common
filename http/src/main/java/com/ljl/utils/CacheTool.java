package com.ljl.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujialin on 2018/3/26.
 */
public class CacheTool<K,V> {
    private Map<K,Pair<Long,V>> cache;
    private long invalidTime;//毫秒

    public CacheTool(long invalidTime) {
        this(invalidTime,TimeUnit.MS);
    }

    public CacheTool(long invalidTime,TimeUnit timeUnit) {
        cache = new ConcurrentHashMap<>();
        this.invalidTime = (long) (invalidTime * TimeUnit.scale(TimeUnit.MS,timeUnit));
    }

    public V get(K key){
        Pair<Long,V> pair = cache.get(key);
        if (pair == null){
            return null;
        }
        if ( (System.currentTimeMillis() - pair.getKey()) > invalidTime){
            //cache.remove(key);
            return null;
        }

        return pair.getValue();
    }

    public void put(K key, V value){
        cache.put(key, new Pair<Long, V>(System.currentTimeMillis(),value));
    }

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

    class Pair<L,R>{
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

}
