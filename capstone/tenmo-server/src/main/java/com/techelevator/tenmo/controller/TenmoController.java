package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    private JdbcUserDao jdbcUserDao;
    private UserDao userDao;

    public TenmoController(JdbcUserDao jdbcUserDao, UserDao userDao) {
        this.userDao = userDao;
        this.jdbcUserDao = jdbcUserDao;
    }
    
    @RequestMapping(path = "accounts/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable("id") int accountId){
        BigDecimal balance = userDao.getBalance(accountId);
        if (balance == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return balance;
        }
    }
    @RequestMapping(path = "/whoAmI")
    public String whoAmI(Principal principal) {
        return principal.getName();

    }

}
