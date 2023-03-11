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
    public void transferCreated(Transfer transfer) {
        if (!(transferDao.createTransfer(transfer.getTransferStatusId(), transfer.getTransferTypeId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "accounts/{id}/transfer/send", method = RequestMethod.POST)
    public void sendMoney(@PathVariable int id, @RequestBody TransferOriginAccount originAccount) {
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
    public void receiveMoney(@PathVariable int id, @RequestBody TransferReceiveAccount originAccount) {
        if (transferDao.transferAllowed(originAccount.getAmount(), originAccount.getAccountTo())) {
            transferDao.addTransferAmount(originAccount.getAmount(), originAccount.getAccountTo());
//            TODO transferCreated(transfer);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "accounts/{id}/transfer/{id2}", method = RequestMethod.POST)
    public void fullTransfer(@PathVariable int id, @PathVariable int id2, @RequestBody TransferOriginAccount transferOriginAccount, TransferReceiveAccount transferReceiveAccount) {
        Transfer transfer = new Transfer(1,1, transferOriginAccount.getAccountFrom(), id2, transferOriginAccount.getAmount());
        Transfer transfer1 = new Transfer(1, 2, id2, transferOriginAccount.getAccountFrom(), transferOriginAccount.getAmount());
        if (transferDao.transferAllowed(transferOriginAccount.getAmount(), transferOriginAccount.getAccountFrom())) {
            transferDao.subtractTransferAmount(transferOriginAccount.getAmount(), transferOriginAccount.getAccountFrom());
            transferDao.addTransferAmount(transferOriginAccount.getAmount(), id2);
            transferCreated(transfer);
            transferCreated(transfer1);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer failed.");
        }

    }

}

