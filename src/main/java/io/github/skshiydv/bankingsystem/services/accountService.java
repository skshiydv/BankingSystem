package io.github.skshiydv.bankingsystem.services;

import io.github.skshiydv.bankingsystem.Entity.BankAccount;
import io.github.skshiydv.bankingsystem.Entity.User;
import io.github.skshiydv.bankingsystem.Repositories.AccountRepository;
import io.github.skshiydv.bankingsystem.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class accountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public void saveAccount(BankAccount bankAccount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            if(user.getBankAccount()!=null)  throw new IllegalStateException("Bank account already exists");
            bankAccount.setOwner(username);
            accountRepository.save(bankAccount);
            user.setBankAccount(bankAccount);
            userRepository.save(user);
        }

    }
}