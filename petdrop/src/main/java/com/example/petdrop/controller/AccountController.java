package com.example.petdrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.model.Account;
import com.example.petdrop.repository.AccountRepository;

@RestController
public class AccountController {
    
    @Autowired 
    private AccountRepository accountRepo;
    
    // Save method is predefine method in Mongo Repository
    // with this method we will save user in our database
    @PostMapping("/addAccount")
    public Account addAccount(@RequestBody Account account) {
        return accountRepo.save(account);
    }
    
    // findAll method is predefine method in Mongo Repository 
    // with this method we will all user that is save in our database
    @GetMapping("/getAllAccounts")
    public List<Account> getAllAccounts(){
        return accountRepo.findAll(); 
    }
}
