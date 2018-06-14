package com.seaway.kit.repository;

import com.seaway.kit.pojo.mongo.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {

    Users findByUsername(String username);

}
