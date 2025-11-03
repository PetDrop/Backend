package com.example.petdrop.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.petdrop.model.Account;
import com.example.petdrop.repository.AccountRepository;

@Service
public class NotificationSharingService {

    private final AccountRepository accountRepo;

    public NotificationSharingService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    /**
     * Finds all push tokens for users who should receive a notification for a given owner.
     * This includes the owner themselves and any users with bidirectional sharing.
     * 
     * @param ownerUsername The username of the pet owner
     * @return List of expo push tokens for all recipients (owner + shared users)
     */
    public List<String> findAllRecipients(String ownerUsername) {
        List<String> pushTokens = new ArrayList<>();

        // Find the owner account
        Optional<Account> ownerOpt = accountRepo.findAccountByUsername(ownerUsername);
        if (!ownerOpt.isPresent()) {
            System.err.println("Owner account not found: " + ownerUsername);
            return pushTokens;
        }

        Account owner = ownerOpt.get();

        // Add owner's push token if it exists
        if (owner.getExpoPushToken() != null && !owner.getExpoPushToken().isEmpty()) {
            pushTokens.add(owner.getExpoPushToken());
        }

        // Find all accounts that have ownerUsername in their sharedUsers array
        List<Account> potentialRecipients = accountRepo.findAccountsWithSharedUser(ownerUsername);

        // Filter to only include bidirectional sharing (owner must also have them in usersSharedWith)
        String[] usersSharedWith = owner.getUsersSharedWith();
        if (usersSharedWith != null) {
            List<String> ownerSharesList = Arrays.asList(usersSharedWith);
            
            for (Account recipient : potentialRecipients) {
                // Check bidirectional: recipient has owner in sharedUsers AND owner has recipient in usersSharedWith
                if (ownerSharesList.contains(recipient.getUsername())) {
                    String token = recipient.getExpoPushToken();
                    if (token != null && !token.isEmpty()) {
                        pushTokens.add(token);
                    }
                }
            }
        }

        return pushTokens;
    }
}

