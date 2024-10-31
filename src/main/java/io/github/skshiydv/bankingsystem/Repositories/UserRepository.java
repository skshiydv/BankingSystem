package io.github.skshiydv.bankingsystem.Repositories;

import io.github.skshiydv.bankingsystem.Entity.User;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    User findByEmail(@NonNull String email);
}
