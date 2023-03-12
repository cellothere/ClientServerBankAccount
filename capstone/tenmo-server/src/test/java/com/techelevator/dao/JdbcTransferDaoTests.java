package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class JdbcTransferDaoTests extends BaseDaoTests {

    //TODO add transferID
    protected static final Transfer Transfer_1 = new Transfer(2, 1, 2001, 2002, BigDecimal.valueOf(500));
    protected static final Transfer Transfer_2 = new Transfer(2, 1, 2002, 2001, BigDecimal.valueOf(2000));
    protected static final Transfer Transfer_3 = new Transfer(2, 1, 2001, 2001, BigDecimal.valueOf(500));

    private JdbcTransferDao sut;


    @Test
    public void getTransferById_given_valid_id_returns_transfer() {
        Transfer actualTransfer = sut.getTransferById(Transfer_1.getTransferId());

        Assert.assertEquals(Transfer_1, actualTransfer);
    }

    //TODO how to test when this involves retrieving account balance?
//    @Test
//    public boolean transferAllowed_allows_valid_transfer(){
//
//    }
//
//
//    @Test
//    public boolean transferAllowed_does_not_allow_invalid_transfer(){
//
//    }

}
