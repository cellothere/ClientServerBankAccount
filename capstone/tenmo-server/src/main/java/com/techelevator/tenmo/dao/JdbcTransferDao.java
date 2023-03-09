package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private UserDao userDao;
    private Principal principal;
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean transferAllowed(BigDecimal transfer) {
        BigDecimal currentBalance = userDao.getBalance(userDao.findIdByUsername(principal.getName()));

        if (transfer.compareTo(currentBalance) != 1 && (transfer.signum() > 0)) {
            return true;
        } else {
            return false;
            //throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You do not have enough money in your account.");
        }
    }


    @Override
    public boolean createTransfer(Transfer transfer){
        String sql = "INSERT into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?);";
        return jdbcTemplate.update(sql,transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount()) == 1;
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
        @Override
        public BigDecimal subtractTransferAmount(BigDecimal transferAmount, int account_id){
            String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";

            BigDecimal newBalance = userDao.getBalance(userDao.findIdByUsername(principal.getName()));

            return newBalance;
        }

        @Override
        public BigDecimal addTransferAmount(BigDecimal transferAmount, int accountId){
            String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";

            BigDecimal newBalance = userDao.getBalance(userDao.findIdByUsername(principal.getName()));

            return newBalance;
        }



    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferTypeDesc(rs.getString("transfer_type_desc"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setTransferStatusDesc(rs.getString("transfer_status_desc"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
