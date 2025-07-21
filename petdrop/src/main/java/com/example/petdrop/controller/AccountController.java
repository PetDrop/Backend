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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.Account;
import com.example.petdrop.model.Pet;
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

    // update an account's shared users
    @PatchMapping("/updateaccount/sharedusers/{id}")
    public long updateAccountSharedUsers(@PathVariable String id, @RequestBody String[] sharedUsers) {
        return accountRepo.updateAccountSharedUsers(id, sharedUsers);
    }

    // update an account's users they shared with
    @PatchMapping("/updateaccount/userssharedwith/{id}")
    public long updateAccountUsersSharedWith(@PathVariable String id, @RequestBody String[] usersSharedWith) {
        return accountRepo.updateAccountUsersSharedWith(id, usersSharedWith);
    }

    // update an account's Pets
    @PatchMapping("/updateaccount/pets/{id}")
    public long updateAccountPets(@PathVariable String id, @RequestBody Pet[] pets) {
        return accountRepo.updateAccountPets(id, pets);
    }

    // update an account's image
    @PatchMapping("/updateaccount/image/{id}")
    public long updateAccountImage(@PathVariable String id, @RequestBody String image) {
        return accountRepo.updateAccountImage(id, image);
    }

    // get all accounts from db
    @GetMapping("/getallaccounts")
    public List<Account> getAllAccounts() {
        return accountRepo.findAll(); 
    }

    // get account from db using its username
    @GetMapping("/getaccountbyusername/{username}")
    public Optional<Account> getAccountByUsername(@PathVariable String username) {
        return accountRepo.findAccountByUsername(username);
    }

    // get account from db using its email
    @GetMapping("/getaccountbyemail/{email}")
    public Optional<Account> getAccountByEmail(@PathVariable String email) {
        return accountRepo.findAccountByEmail(email);
    }

    // get account from db using its id
    @GetMapping("/getaccountbyid/{id}")
    public Optional<Account> getAccountById(@PathVariable String id) {
        return accountRepo.findById(id);
    }
    
}
