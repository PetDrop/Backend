package com.example.petdrop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.Account;
import com.example.petdrop.repository.AccountRepository;

@RestController
public class AccountController {
    
    @Autowired 
    private AccountRepository accountRepo;
    
    // save account to db
    @PostMapping("/addaccount")
    public Account addAccount(@RequestBody Account account) {
        return accountRepo.save(account);
    }

    // use PUT for updating whole accounts
    // updatedAccount must have all fields, not just ones to be updated
    @PutMapping("/updateaccount")
    public ResponseEntity<Account> updateAccount(@RequestBody Account updatedAccount) {
        Account savedAccount = accountRepo.save(updatedAccount);
        return ResponseEntity.ok(savedAccount);
    }

    // update an account's username
    @PatchMapping("/updateaccount/username/{id}")
    public long updateAccountUsername(@PathVariable String id, @RequestBody String username) {
        return accountRepo.updateAccountUsername(id, username);
    }

    // update an account's email
    @PatchMapping("/updateaccount/email/{id}")
    public long updateAccountEmail(@PathVariable String id, @RequestBody String email) {
        return accountRepo.updateAccountEmail(id, email);
    }

    // update an account's password
    @PatchMapping("/updateaccount/password/{id}")
    public long updateAccountPassword(@PathVariable String id, @RequestBody String password) {
        return accountRepo.updateAccountPassword(id, password);
    }

    // update an account's phone number
    @PatchMapping("/updateaccount/phone/{id}")
    public long updateAccountPhone(@PathVariable String id, @RequestBody String phone) {
        return accountRepo.updateAccountPhone(id, phone);
    }

    // update an account's address
    @PatchMapping("/updateaccount/address/{id}")
    public long updateAccountAddress(@PathVariable String id, @RequestBody String address) {
        return accountRepo.updateAccountAddress(id, address);
    }

    // update an account's emergency contacts
    @PatchMapping("/updateaccount/emergencycontacts/{id}")
    public long updateAccountEmergencyContacts(@PathVariable String id, @RequestBody String[] emergencyContacts) {
        return accountRepo.updateAccountEmergencyContacts(id, emergencyContacts);
    }
    
    // get all accounts from db
    @GetMapping("/getallaccounts")
    public List<Account> getAllAccounts() {
        return accountRepo.findAll(); 
    }

    // get account from db using its username
    @GetMapping("/getaccountbyusername")
    public Account getAccountByUsername(@RequestBody String username) {
        return accountRepo.findAccountByUsername(username);
    }

    // get account from db using its email
    @GetMapping("/getaccountbyemail")
    public Account getAccountByEmail(@RequestBody String email) {
        return accountRepo.findAccountByEmail(email);
    }

    // get account from db using its phone number
    @GetMapping("/getaccountbyphone")
    public Account getAccountByPhone(@RequestBody String phone) {
        return accountRepo.findAccountByPhone(phone);
    }
}
