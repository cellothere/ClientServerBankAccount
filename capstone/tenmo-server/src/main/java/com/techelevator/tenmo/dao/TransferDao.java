package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    BigDecimal transferAmount(BigDecimal transfer);

    BigDecimal subtractTransferAmount(BigDecimal transferAmount);

    BigDecimal addTransferAmount(BigDecimal transferAmount);
}
