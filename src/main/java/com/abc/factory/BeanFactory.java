package com.abc.factory;

import com.abc.domain.Account;
import com.abc.service.AccountService;
import com.abc.util.TransactionManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用于创建AccountService的代理对象的工厂类。
 *
 * @author Yuqing Duan
 * @version 1.0
 */
@Component("beanFactory")
public class BeanFactory {
    // 注入AccountService对象
    @Resource(name = "accountService")
    private AccountService accountService;

    // 注入TransactionManager对象
    @Resource(name = "transactionManager")
    private TransactionManager transactionManager;

    /**
     * 获取Service代理对象。
     */
    public AccountService getAccountService() {
        return (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            /**
             * 对AccountService对象添加事物的支持。
             * @param proxy 被代理的AccountService对象。
             * @param method 被代理的AccountService对象中的所有方法。
             * @param args 方法的所有参数。
             * @return 增强后的代理对象。
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object returnVal = null;
                try {
                    // 1.开启事务
                    transactionManager.beginTransaction();
                    // 2.执行操作（对AccountServiceImpl类中的所有方法进行增强，添加事务）
                    returnVal = method.invoke(accountService, args);
                    // 3.提交事务
                    transactionManager.commit();
                } catch (ArithmeticException ae) {
                    // 4.回滚操作
                    transactionManager.rollback();
                    throw new ArithmeticException();
                } finally {
                    // 5.释放连接
                    transactionManager.release();
                }
                return returnVal;
            }
        });
    }
}
