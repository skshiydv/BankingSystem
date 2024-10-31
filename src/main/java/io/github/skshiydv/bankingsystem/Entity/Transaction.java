package io.github.skshiydv.bankingsystem.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Transaction {
    @Id
    private ObjectId id;

    @NonNull
    private String sender;

    @NonNull
    private String receiver;

    @NonNull
    private double amount;

    @NonNull
    private TransactionType transactionType; // Enum type for transaction type

    private LocalDateTime date = LocalDateTime.now(); // Initialize date with current time

    public enum TransactionType {
        CREDIT, DEBIT, COMPLETE
    }
}
