package com.seaway.kit.repository;

import com.seaway.kit.pojo.mongo.GameRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends MongoRepository<GameRoom, String> {



}
