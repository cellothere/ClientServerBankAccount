package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }
    @Override
    public boolean transferAllowed(BigDecimal transfer, int userId) {
        BigDecimal currentBalance = userDao.getBalance(userId);

        if (transfer.compareTo(currentBalance) != 1 && (transfer.signum() > 0)) {
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You do not have enough money in your account.");

        }
    }


    @Override
    public boolean createTransfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount){
        String sql = "INSERT into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        Integer newTransferId;
        newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transferTypeId, transferStatusId, accountFrom, accountTo, amount);

        if (newTransferId == null) {
            return false;
        } else {
            return true;
        }
    }



//    String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
//    String password_hash = new BCryptPasswordEncoder().encode(password);
//    Integer newUserId;
//    newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
//
//        if (newUserId == null) return false;


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
    public BigDecimal subtractTransferAmount(BigDecimal transferAmount, int accountId){
        String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";
        jdbcTemplate.update(sql, transferAmount, accountId);

        BigDecimal newBalance = userDao.getBalance(accountId);

        return newBalance;
    }

    @Override
    public BigDecimal addTransferAmount(BigDecimal transferAmount, int accountId){
        String sql = "UPDATE account SET balance = balance + ? WHERE account_id = ?;";

        jdbcTemplate.update(sql, transferAmount, accountId);

        BigDecimal newBalance = userDao.getBalance(accountId);

        return newBalance;
    }

    @Override
    public void fullTransfer(int accountFrom, int accountTo, BigDecimal amount){
        subtractTransferAmount(amount, accountFrom);
        addTransferAmount(amount, accountTo);
    }



    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
//        transfer.setTransferTypeDesc(rs.getString("transfer_type_desc"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
//        transfer.setTransferStatusDesc(rs.getString("transfer_status_desc"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}

