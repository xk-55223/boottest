package com.keith.test.boottest;

import com.keith.test.boottest.entity.BaseEntity;

import java.util.ArrayList;

/**
 * 循环测试
 *
 * @author keith
 * @since 2020-11-25
 */
public class HeapTest {



    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> arrayList = new ArrayList<>();
        while (true) {
            BaseEntity baseEntity = new BaseEntity();
            arrayList.add(baseEntity);
            Thread.sleep(1000);
        }
    }
}
