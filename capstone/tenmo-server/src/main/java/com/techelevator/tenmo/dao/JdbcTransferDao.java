package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public class JdbcTransferDao implements TransferDao {

    private UserDao userDao;
    private Principal principal;


    public BigDecimal transferAmount(BigDecimal transfer) {
        BigDecimal currentBalance = userDao.getBalance(userDao.findIdByUsername(principal.getName()));

        if(transfer.compareTo(currentBalance) != 1 && (transfer.signum() > 0)) {


        }


//        String sql = "select * FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE account.user_id = ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//        if (results.next()) {
//            User userbalance = mapRowToUser(results);
//            Double balance = userbalance.getBalance();
//            BigDecimal bigDecimalBalance = BigDecimal.valueOf(balance);
//            return bigDecimalBalance;
//        } else {
//            return null;
//        }
//    }

    public BigDecimal subtractTransferAmount(BigDecimal transferAmount);

    public BigDecimal addTransferAmount(BigDecimal transferAmount);
    }
}
