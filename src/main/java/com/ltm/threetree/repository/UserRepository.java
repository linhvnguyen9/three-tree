package com.ltm.threetree.repository;

import com.ltm.threetree.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Player, String> {
}
