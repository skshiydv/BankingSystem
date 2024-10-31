package io.github.skshiydv.bankingsystem.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    private ObjectId id;
    @NonNull
    private String owner;
    private double balance;
}
