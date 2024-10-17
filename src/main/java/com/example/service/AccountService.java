package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepositorymp;

@Service
public class AccountService {
    private AccountRepositorymp accountRepository;

    @Autowired
    public AccountService(AccountRepositorymp accountRepository){
        this.accountRepository = accountRepository;
    }

    public Optional<Account> registerAccount(Account account){
        if(account.getUsername().isBlank() || account.getPassword().length() < 4 || accountRepository.findByUsername(account.getUsername()) != null) return Optional.empty();
        return Optional.of(accountRepository.save(account));
    }

    public Account findByUsername(String username){
        return accountRepository.findByUsername(username); //test
    }

    public Optional<Account> loginAccount(Account account){
        Account loginAccount = accountRepository.findByUsername(account.getUsername());
        if(loginAccount != null && loginAccount.getPassword().equals(account.getPassword())){
            return Optional.of(loginAccount);
        }
        return Optional.empty();
    }

}
