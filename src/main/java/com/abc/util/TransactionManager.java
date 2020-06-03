package com.abc.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事务管理工具类。
 *
 * @author Yuqing Duan
 */
@Component("transactionManager")
public class TransactionManager {
    // 注入connectionUtils对象
    @Resource(name = "connectionUtils")
    private ConnectionUtils connectionUtils;

    /**
     * 开启事务。
     */
    public void beginTransaction() {
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务。
     */
    public void commit() {
        try {
            connectionUtils.getThreadConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务。
     */
    public void rollback() {
        try {
            connectionUtils.getThreadConnection().rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放连接。
     */
    public void release() {
        try {
            // 将连接和线程解绑
            connectionUtils.removeConnection();
            // 将连接还回连接池中
            connectionUtils.getThreadConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
