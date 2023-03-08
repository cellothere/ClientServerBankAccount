package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TenmoController {

    private JdbcUserDao jdbcUserDao;
    private UserDao userDao;

    public TenmoController(JdbcUserDao jdbcUserDao, UserDao userDao) {
        this.userDao = userDao;
        this.jdbcUserDao = jdbcUserDao;
    }

    //    TODO does this work?\
//    @PreAuthorize("hasRole('user')")
    @RequestMapping(path = "accounts/{id}/balance", method = RequestMethod.GET)
    public Double getBalance(@PathVariable("id") int accountId){
        User user = userDao.getBalance(accountId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return user.getBalance();
        }
    }

}
