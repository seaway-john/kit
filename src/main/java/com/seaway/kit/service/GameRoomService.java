package com.seaway.kit.service;

import com.seaway.kit.exception.NotFoundException;
import com.seaway.kit.pojo.mongo.GameRoom;
import com.seaway.kit.repository.GameRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;

    public GameRoomService(GameRoomRepository gameRoomRepository) {
        this.gameRoomRepository = gameRoomRepository;
    }

    public Flux<GameRoom> getList() {
        List<GameRoom> rooms = gameRoomRepository.findAll();

        return Flux.fromIterable(rooms);
    }

    public Mono<GameRoom> getById(String id) {
        Optional<GameRoom> room = gameRoomRepository.findById(id);

        return Mono.justOrEmpty(room).switchIfEmpty(Mono.error(new NotFoundException()));
    }

    public Flux<GameRoom> add(Flux<GameRoom> rooms) {
        return rooms.doOnNext(room -> {
            room.setId(null);
            room.setScanTimestamp(LocalDateTime.now());

            gameRoomRepository.save(room);
        });
    }

    public Mono<Void> update(GameRoom room) {
        if (gameRoomRepository.existsById(room.getId())) {
            room.setScanTimestamp(LocalDateTime.now());
            gameRoomRepository.save(room);
        }

        return Mono.when();
    }

    public Mono<Void> deleteById(String id) {
        gameRoomRepository.deleteById(id);

        return Mono.when();
    }

}
