package org.roxyfy.test_tasks.bank_transactions;

import java.math.BigDecimal;

public class Account {

    private String owner;
    private BigDecimal balance;

    public Account(String owner, BigDecimal initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
