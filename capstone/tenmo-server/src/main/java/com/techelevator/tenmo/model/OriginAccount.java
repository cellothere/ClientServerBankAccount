package com.techelevator.tenmo.model;

import javax.lang.model.util.Elements;
import java.math.BigDecimal;

public class OriginAccount {

    private int accountFrom;
    private BigDecimal amount;

    public OriginAccount() {

    }

    public void setAccountFrom(int accountId) { this.accountFrom = accountId; }
    public int getAccountFrom() { return accountFrom; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getAmount() { return amount; }



}
