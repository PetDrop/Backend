package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.Account;
import com.example.petdrop.repository.AccountRepository;

@RestController
public class AccountController {
    
    @Autowired 
    private AccountRepository accountRepo;
    
    // save account to db
    @PostMapping("/add-account")
    public Account addAccount(@RequestBody Account account) {
        return accountRepo.save(account);
    }

    // update all fields of account
    @PutMapping("/update-account")
    public ResponseEntity<Account> updateAccount(@RequestBody Account updatedAccount) {
        Account savedAccount = accountRepo.save(updatedAccount);
        return ResponseEntity.ok(savedAccount);
    }

    // get account from db using its username
    @GetMapping("/get-account-by-username/{username}")
    public Optional<Account> getAccountByUsername(@PathVariable String username) {
        return accountRepo.findAccountByUsername(username);
    }

    // get account from db using its email
    @GetMapping("/get-account-by-email/{email}")
    public Optional<Account> getAccountByEmail(@PathVariable String email) {
        return accountRepo.findAccountByEmail(email);
    }

    // get account from db using its id
    @GetMapping("/get-account-by-id/{id}")
    public Optional<Account> getAccountById(@PathVariable String id) {
        return accountRepo.findById(id);
    }

    // get all accounts from db
    @GetMapping("/get-all-accounts")
    public List<Account> getAllAccounts() {
        return accountRepo.findAll(); 
    }
}
