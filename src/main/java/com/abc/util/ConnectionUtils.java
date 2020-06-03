package com.abc.util;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 连接的工具类，它用于从数据源获取一个链接，并且实现和当前线程的绑定。
 *
 * @author Yuqing Duan
 * @version 1.0
 */
@Component("connectionUtils")
public class ConnectionUtils {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    // 注入dataSource数据源对象
    @Resource(name = "dataSource")
    private DataSource dataSource;

    /**
     * 获取当前线程上的连接.
     */
    public Connection getThreadConnection() {
        try {
            // 先从ThreadLocal上获取
            Connection connection = threadLocal.get();
            // 判断当前线程是否有连接
            if (connection == null) {
                // 从数据源中获取一个连接，并且存入ThreadLocal中
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把连接与线程解绑.
     */
    public void removeConnection() {
        threadLocal.remove();
    }
}
