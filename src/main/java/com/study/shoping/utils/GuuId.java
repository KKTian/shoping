package com.study.shoping.utils;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 全局ID
 * @outhor KK
 * @time 2018-08-15 15:22
 */
public class GuuId {
    private final static int MAX_INTEGER = 999;
    private final static int INIT_INTEGER = 100;
    private final static AtomicInteger ID = new AtomicInteger(INIT_INTEGER);

    public static String produce(){
        return String.format("%d%d",Instant.now().toEpochMilli(),get());
    }

    public static int get(){
        return ID.get() >= MAX_INTEGER ? ID.getAndSet(INIT_INTEGER) : ID.getAndIncrement();
    }

    public static void main(String[] args) {
        System.out.println(produce());
    }
}