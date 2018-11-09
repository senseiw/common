package com.ljl.base.cache;

import com.ljl.base.Pair;
import com.ljl.base.TimeUnit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liujialin on 2018/11/9.
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

}
