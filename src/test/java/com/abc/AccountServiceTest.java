package com.abc;

import com.abc.domain.Account;
import com.abc.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;

// 使用Junit提供的一个注解把原有的main方法替换成spring提供的 @RunWith
@RunWith(SpringJUnit4ClassRunner.class)
// 告知spring运行器，spring和ioc创建是基于xml还是注解，并说明位置
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountServiceTest {
    // 配置完了@RunWith和@ContextConfiguration我们就有了IoC容器和容器中的JavaBean
    // 注入accountService对象
    @Resource(name = "proxyAccountService")
    private AccountService accountService;

    @Test
    public void testFindAllAccounts() {
        List<Account> accounts = accountService.findAllAccounts();
        // 打印结果
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testTransfer() {
        // 断言会抛出某种特定的异常
        boolean flag = false;
        try {
            accountService.transfer("aaa", "bbb", 100F);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }
}
