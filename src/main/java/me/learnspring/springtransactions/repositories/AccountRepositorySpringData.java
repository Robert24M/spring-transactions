package me.learnspring.springtransactions.repositories;

import me.learnspring.springtransactions.model.Account;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepositorySpringData extends CrudRepository<Account, Long> {

    //Let the Spring Data to build the query based on the method name
    List<Account> findAccountByName(String name);

    //Defining the query by myself by using the @Query annotation
    @Query("SELECT * FROM account WHERE balance BETWEEN :low AND :high")
    List<Account> findAccountByBalance(BigDecimal low, BigDecimal high);

    //Use the modifying annotation to mark that the method changes data
    @Modifying
    @Query("UPDATE account SET balance = :balance WHERE id = :id")
    void changeBalance(long id, BigDecimal balance);
}
