package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
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

        if (transfer.compareTo(currentBalance) != 1 && (transfer.signum() > 0)) {


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

        public BigDecimal subtractTransferAmount(BigDecimal transferAmount){
            String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";

        }

        public BigDecimal addTransferAmount(BigDecimal transferAmount){
            String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";
        }
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
