package io.github.skshiydv.bankingsystem.Repositories;

import io.github.skshiydv.bankingsystem.Entity.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface TransactionRepository extends MongoRepository<Transaction, ObjectId> {
}
