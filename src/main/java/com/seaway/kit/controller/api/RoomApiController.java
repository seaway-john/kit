package com.seaway.kit.controller.api;

import com.seaway.kit.pojo.mongo.GameRoom;
import com.seaway.kit.service.GameRoomService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api/kit/room")
public class RoomApiController {

    private final GameRoomService gameRoomService;

    public RoomApiController(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    @GetMapping("/list")
    public Flux<GameRoom> getList() {
        return gameRoomService.getList();
    }

    @GetMapping("/refresh")
    public Mono<GameRoom> getById(@RequestParam(value = "id") String id) {
        return gameRoomService.getById(id);
    }

    @PostMapping("/add")
    public Flux<GameRoom> add(@RequestBody Flux<GameRoom> rooms) {
        return gameRoomService.add(rooms);
    }

    @PutMapping("update")
    public Mono<Void> update(@RequestParam("id") String id, @RequestBody GameRoom room) {
        Objects.requireNonNull(room);
        room.setId(id);

        return gameRoomService.update(room);
    }

    @DeleteMapping("delete")
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return gameRoomService.deleteById(id);
    }

}
