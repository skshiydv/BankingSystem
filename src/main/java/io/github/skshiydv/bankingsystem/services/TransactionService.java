package io.github.skshiydv.bankingsystem.services;

import io.github.skshiydv.bankingsystem.Entity.BankAccount;
import io.github.skshiydv.bankingsystem.Entity.Transaction;
import io.github.skshiydv.bankingsystem.Entity.User;
import io.github.skshiydv.bankingsystem.Repositories.AccountRepository;
import io.github.skshiydv.bankingsystem.Repositories.TransactionRepository;
import io.github.skshiydv.bankingsystem.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    @Transactional
    public void save(Transaction transaction) {
        transaction.setId(new ObjectId());
        String receiverName=transaction.getReceiver();
        User receiver =userRepository.findByUsername(receiverName);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || receiver == null) {
            throw new IllegalArgumentException("Authentication or receiver not found");
        }
        User sender = userRepository.findByUsername(authentication.getName());
        double amount = transaction.getAmount();
        if (sender.getBankAccount().getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance for transaction");
        }
        Transaction senderTransaction = new Transaction(
                transaction.getId(),
                sender.getUsername(),
                receiverName,
                transaction.getAmount(),
                Transaction.TransactionType.CREDIT,
                LocalDateTime.now()
        );
        Transaction recieverTransaction = new Transaction(
                transaction.getId(),
                sender.getUsername(),
                receiverName,
                transaction.getAmount(),
                Transaction.TransactionType.DEBIT,
                LocalDateTime.now()
        );
        sender.getTransactions().add(senderTransaction);
        sender.getBankAccount().setBalance(sender.getBankAccount().getBalance() - transaction.getAmount());
        receiver.getTransactions().add(recieverTransaction);
        receiver.getBankAccount().setBalance(receiver.getBankAccount().getBalance() + transaction.getAmount());
        accountRepository.save(receiver.getBankAccount());
        accountRepository.save(sender.getBankAccount());
        Transaction saveTransaction = new Transaction(
                transaction.getId(),
                sender.getUsername(),
                receiverName,
                transaction.getAmount(),
                Transaction.TransactionType.COMPLETE,
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);
    }
}
