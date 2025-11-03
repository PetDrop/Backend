package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.petdrop.model.Account;
import com.example.petdrop.repository.AccountRepository;

@RestController
public class AccountController {
    
    @Autowired 
    private AccountRepository accountRepo;
    
    // Email regex pattern for validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Password must have 8-30 chars, at least 1 number, 1 lowercase, 1 uppercase
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}$"
    );
    
    // Username must start with a letter
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[A-Za-z].*"
    );
    
    // Validate signup data
    @PostMapping("/validate-signup")
    public ResponseEntity<Map<String, Object>> validateSignup(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {
        
        Map<String, Object> response = new HashMap<>();
        boolean isValid = true;
        Map<String, String> errors = new HashMap<>();
        
        // Validate username
        if (username != null && !username.isEmpty()) {
            if (!USERNAME_PATTERN.matcher(username).matches()) {
                errors.put("username", "Username must begin with a letter");
                isValid = false;
            } else if (accountRepo.findAccountByUsername(username).isPresent()) {
                errors.put("username", "Username is already taken");
                isValid = false;
            }
        }
        
        // Validate email
        if (email != null && !email.isEmpty()) {
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                errors.put("email", "Invalid email address");
                isValid = false;
            } else if (accountRepo.findAccountByEmail(email).isPresent()) {
                errors.put("email", "Email is already registered");
                isValid = false;
            }
        }
        
        // Validate password
        if (password != null && !password.isEmpty()) {
            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                errors.put("password", "Password must be 8-30 characters with at least one number, one lowercase letter, and one uppercase letter");
                isValid = false;
            }
        }
        
        response.put("valid", isValid);
        if (!isValid) {
            response.put("errors", errors);
        }
        
        return ResponseEntity.ok(response);
    }
    
    // save account to db with validation
    @PostMapping("/add-account")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        Map<String, String> errors = new HashMap<>();
        
        // Validate username
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            errors.put("username", "Username is required");
        } else if (!USERNAME_PATTERN.matcher(account.getUsername()).matches()) {
            errors.put("username", "Username must begin with a letter");
        } else if (accountRepo.findAccountByUsername(account.getUsername()).isPresent()) {
            errors.put("username", "Username is already taken");
        }
        
        // Validate email
        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!EMAIL_PATTERN.matcher(account.getEmail()).matches()) {
            errors.put("email", "Invalid email address");
        } else if (accountRepo.findAccountByEmail(account.getEmail()).isPresent()) {
            errors.put("email", "Email is already registered");
        }
        
        // Validate password
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            errors.put("password", "Password is required");
        } else if (!PASSWORD_PATTERN.matcher(account.getPassword()).matches()) {
            errors.put("password", "Password must be 8-30 characters with at least one number, one lowercase letter, and one uppercase letter");
        }
        
        if (!errors.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        Account savedAccount = accountRepo.save(account);
        return ResponseEntity.ok(savedAccount);
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
