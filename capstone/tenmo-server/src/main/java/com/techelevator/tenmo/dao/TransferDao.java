package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean transferAllowed(BigDecimal transfer);

    Transfer getTransferById(int transferId);

    boolean createTransfer(Transfer transfer);

    BigDecimal subtractTransferAmount(BigDecimal transferAmount, int accountId);

    BigDecimal addTransferAmount(BigDecimal transferAmount, int accountId);
}
