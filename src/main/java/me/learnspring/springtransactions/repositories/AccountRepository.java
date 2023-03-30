package me.learnspring.springtransactions.repositories;

import me.learnspring.springtransactions.model.Account;
import me.learnspring.springtransactions.model.AccountMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbc;

    public AccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Account getAccountById(long id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        return jdbc.queryForObject(sql, new AccountMapper(), id);
    }

    public void changeBalance(long id, BigDecimal balance) {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        jdbc.update(sql, balance, id);
    }

    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM account";
        return jdbc.query(sql, new AccountMapper());
    }

    public List<Account> findAccountsByName(String name) {
        String sql = "SELECT * FROM account WHERE name = ?";
        return jdbc.query(sql, new AccountMapper(), name);
    }
}
