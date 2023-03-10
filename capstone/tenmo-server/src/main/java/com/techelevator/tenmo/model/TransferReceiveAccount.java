package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransferReceiveAccount {

    @JsonProperty("account_id_to")
    private int accountFrom;
    private BigDecimal amount;

    public TransferReceiveAccount() {
    }

    public void setAccountTo(int accountId) { this.accountFrom = accountId; }
    public int getAccountTo() { return accountFrom; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getAmount() { return amount; }

}



