package com.abc.service;

import com.abc.domain.Account;

import java.util.List;

/**
 * 账户的业务层接口。
 *
 * @author Yuqing Duan
 * @version 1.0
 */
public interface AccountService {
    /**
     * 查询所有账户。
     *
     * @return 所有账户的集合。
     */
    List<Account> findAllAccounts();

    /**
     * 根据id查询某个账户。
     *
     * @param accountId 要查询的账户的id。
     * @return 某个账户。
     */
    Account findAccountById(Integer accountId);

    /**
     * 保存账户。
     *
     * @param account 要保存的账户。
     */
    void saveAccount(Account account);

    /**
     * 更新账户信息。
     *
     * @param account 更新后的账户。
     */
    void updateAccount(Account account);

    /**
     * 根据id删除账户。
     *
     * @param accountId 要删除的账户的id。
     */
    void deleteAccount(Integer accountId);

    /**
     * 模拟银行转账。
     *
     * @param srcAccountName 转出账户名。
     * @param trgAccountName 转入账户名。
     * @param money          转账金额。
     */
    void transfer(String srcAccountName, String trgAccountName, Float money);
}
