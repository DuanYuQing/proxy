package com.abc.service.impl;

import com.abc.dao.AccountDao;
import com.abc.domain.Account;
import com.abc.service.AccountService;
import com.abc.util.TransactionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    // 注入AccountDao对象
    @Resource(name = "accountDao")
    private AccountDao accountDao;

    // 注入transactionManager对象
    // @Resource(name = "transactionManager")
    // private TransactionManager transactionManager;

    /**
     * 查询所有账户。
     *
     * @return 所有账户的集合。
     */
    public List<Account> findAllAccounts() {
        return accountDao.findAllAccounts();
    }

    /**
     * 根据id查询某个账户。
     *
     * @param accountId 要查询的账户的id。
     * @return 某个账户。
     */
    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    /**
     * 保存账户。
     *
     * @param account 要保存的账户。
     */
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    /**
     * 更新账户信息。
     *
     * @param account 更新后的账户。
     */
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    /**
     * 根据id删除账户。
     *
     * @param accountId 要删除的账户的id。
     */
    public void deleteAccount(Integer accountId) {
        accountDao.deleteAccount(accountId);
    }

    /**
     * 模拟银行转账。
     * 问题：事务的控制应该在业务层而非持久层。QueryRunner对象每进行一次CRUD操作就会从数据库连接池对象中取出一个connection连接对象。
     * 解决：使用ThreadLocal类，将一个connection绑定到一个thread上
     * 详见：com.abc.util.ConnectionUtils类，com.abc.util.TransactionManager类。
     *
     * @param srcAccountName 转出账户名。
     * @param trgAccountName 转入账户名。
     * @param money          转账金额。
     */
    public void transfer(String srcAccountName, String trgAccountName, Float money) {
        try {
            // 1.开启事务
            // transactionManager.beginTransaction();

            // 2.执行操作
            // 2.1根据名称查询转出账户 connection01
            Account srcAccount = accountDao.findAccountByName(srcAccountName);
            // 2.2根据名称查询转入账户 connection02
            Account trgAccount = accountDao.findAccountByName(trgAccountName);
            // 2.3转出账户减钱
            srcAccount.setMoney(srcAccount.getMoney() - money);
            // 2.4转入账户加钱
            trgAccount.setMoney(trgAccount.getMoney() + money);
            // 2.5更新转出账户 connection03
            accountDao.updateAccount(srcAccount);
            // 模拟异常
            int i = 1 / 0;
            // 2.6更新转入账户 connection04
            accountDao.updateAccount(trgAccount);

            // 3.提交事务
            // transactionManager.commit();
        } catch (ArithmeticException ae) {
            // 4.回滚操作
            // transactionManager.rollback();
            throw new ArithmeticException();
        } finally {
            // 5.释放连接
            // transactionManager.release();
        }
    }
}
