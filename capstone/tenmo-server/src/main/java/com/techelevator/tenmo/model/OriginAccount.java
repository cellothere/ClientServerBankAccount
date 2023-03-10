package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.lang.model.util.Elements;
import java.math.BigDecimal;

public class OriginAccount {

    @JsonProperty("account_id")
    private int accountFrom;
    private BigDecimal amount;

    public OriginAccount() {

    }

    public void setAccountFrom(int accountId) { this.accountFrom = accountId; }
    public int getAccountFrom() { return accountFrom; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getAmount() { return amount; }



}
