package com.abc.dao.impl;

import com.abc.dao.AccountDao;
import com.abc.domain.Account;
import com.abc.util.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    // 注入dbutils的核心类的对象
    @Resource(name = "queryRunner")
    private QueryRunner queryRunner;

    // 注入ConnectionUtils对象
    @Resource(name = "connectionUtils")
    private ConnectionUtils connectionUtils;

    /**
     * 查询所有账户。
     *
     * @return 所有账户的集合。
     */
    public List<Account> findAllAccounts() {
        try {
            return queryRunner.query(connectionUtils.getThreadConnection(), "SELECT * FROM t_account", new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id查询某个账户。
     *
     * @param accountId 要查询的账户的id。
     * @return 某个账户。
     */
    public Account findAccountById(Integer accountId) {
        try {
            return queryRunner.query(connectionUtils.getThreadConnection(), "SELECT * FROM t_account WHERE `id` = ?", new BeanHandler<Account>(Account.class), accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存账户。
     *
     * @param account 要保存的账户。
     */
    public void saveAccount(Account account) {
        try {
            queryRunner.update(connectionUtils.getThreadConnection(), "INSERT INTO t_account(`name`, `money`) VALUES (?, ?)", account.getName(), account.getMoney());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新账户信息。
     *
     * @param account 更新后的账户。
     */
    public void updateAccount(Account account) {
        try {
            queryRunner.update(connectionUtils.getThreadConnection(), "UPDATE t_account SET `name` = ?, `money` = ? WHERE `id` = ?", account.getName(), account.getMoney(), account.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id删除账户。
     *
     * @param accountId 要删除的账户的id。
     */
    public void deleteAccount(Integer accountId) {
        try {
            queryRunner.update(connectionUtils.getThreadConnection(), "DELETE FROM t_account WHERE `id` = ?", accountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据name查询某个账户。
     *
     * @param accountName 要查询的账户的name。
     * @return 某个账户。
     */
    public Account findAccountByName(String accountName) {
        try {
            List<Account> accounts = queryRunner.query(connectionUtils.getThreadConnection(), "SELECT * FROM t_account WHERE `name` = ?", new BeanListHandler<Account>(Account.class), accountName);
            if (accounts == null || accounts.size() == 0) {
                return null;
            }
            if (accounts.size() > 1) {
                throw new RuntimeException("结果集不唯一，数据有问题!");
            }
            return accounts.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
