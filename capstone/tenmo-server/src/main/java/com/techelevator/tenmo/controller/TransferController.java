package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.TransferOriginAccount;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferReceiveAccount;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;
    private Principal principal;

    public TransferController(TransferDao transferDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;

    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "transfer", method = RequestMethod.POST)
    public void transferCreated(@RequestBody Transfer transfer) {
        if (!(transferDao.createTransfer(transfer.getTransferStatusId(), transfer.getTransferTypeId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "accounts/{id}/transfer/send", method = RequestMethod.POST)
    public void sendMoney(@PathVariable int id, @RequestBody TransferOriginAccount originAccount, Transfer transfer) {
        if (transferDao.transferAllowed(originAccount.getAmount(), originAccount.getAccountFrom())) {
            transferDao.subtractTransferAmount(originAccount.getAmount(), originAccount.getAccountFrom());
//          TODO  transferCreated(transfer);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }
    }
    @PreAuthorize("permitAll")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "accounts/{id}/transfer/receive", method = RequestMethod.POST)
    public void receiveMoney(@PathVariable int id, @RequestBody TransferOriginAccount originAccount, Transfer transfer) {
        if (transferDao.transferAllowed(originAccount.getAmount(), originAccount.getAccountFrom())) {
            transferDao.addTransferAmount(originAccount.getAmount(), originAccount.getAccountFrom());
//            TODO transferCreated(transfer);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "accounts/{id}/transfer/{id2}", method = RequestMethod.POST)
    public void fullTransfer(@PathVariable int id, @PathVariable int id2, @RequestBody TransferOriginAccount transferOriginAccount, TransferReceiveAccount transferReceiveAccount) {
        if (transferDao.transferAllowed(transferOriginAccount.getAmount(), transferOriginAccount.getAccountFrom())) {
            transferDao.subtractTransferAmount(transferOriginAccount.getAmount(), transferOriginAccount.getAccountFrom());
            transferDao.addTransferAmount(transferOriginAccount.getAmount(), id2);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }

    }

}
