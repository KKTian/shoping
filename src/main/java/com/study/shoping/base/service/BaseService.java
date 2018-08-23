package com.study.shoping.base.service;

/**
 * 描述: Service父类，所有service接口继承
 *
 * @outhor gaobitian
 * @create 2018-06-01 10:37
 */
public abstract class BaseService implements IBaseService {

    @Override
    public void test() {
        System.out.println("test");
    }

}