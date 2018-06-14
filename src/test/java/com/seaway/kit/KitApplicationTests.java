package com.seaway.kit;

import com.seaway.kit.pojo.mongo.GameRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KitApplicationTests {

    @Test
    public void contextLoads() {
        WebClient webClient = WebClient.builder().baseUrl("http://172.0.1.163:8080").build();

//        getRoomList(webClient);
//        getRoomById(webClient);
//        addRoom(webClient);
//        updateRoom(webClient);

    }

    private void getRoomList(WebClient webClient) {
        System.out.println("getRoomList");

        Flux<GameRoom> flux = webClient.get().uri("/seaway-kit/api/kit/room/list")
                .retrieve().bodyToFlux(GameRoom.class);

        List<GameRoom> gameRooms = flux.buffer().blockFirst();
        if (gameRooms == null) {
            System.err.println("gameRooms is null");
            return;
        }

        flux.buffer().blockFirst().forEach(gameRoom -> {
            System.out.println("first name: " + gameRoom.getName());
        });
    }

    private void getRoomById(WebClient webClient) {
        System.out.println("getRoomById");

        Mono<GameRoom> mono = webClient.get().uri("/seaway-kit/api/kit/room/refresh?id=5b03b45dad9ef5662a507ae7")
                .retrieve().bodyToMono(GameRoom.class);
        GameRoom gameRoom = mono.block();
        System.out.println("name: " + gameRoom.getName());
    }

    private void addRoom(WebClient webClient) {
        System.out.println("addRoom");

        GameRoom addGameRoom = new GameRoom();
        addGameRoom.setName("Happy MaJiang");
        addGameRoom.setType("majiang");
        addGameRoom.setMaxMember(4);

        ArrayList<GameRoom> addGameRooms = new ArrayList<>();
        addGameRooms.add(addGameRoom);

        Flux<GameRoom> flux = webClient.post().uri("/seaway-kit/api/kit/room/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(addGameRooms))
                .retrieve().bodyToFlux(GameRoom.class);
        if (flux == null) {
            System.err.println("flux is null");
            return;
        }

        flux.buffer().blockFirst().forEach(gameRoom -> {
            System.out.println("first name: " + gameRoom.getName());
        });
    }

    private void updateRoom(WebClient webClient) {
        System.out.println("updateRoom");

        GameRoom updateGameRoom = new GameRoom();
        updateGameRoom.setName("Happy SiChuan MaJiang");
        updateGameRoom.setType("sichuanmajiang");
        updateGameRoom.setMaxMember(4);

        Mono<GameRoom> mono = webClient.put().uri("/seaway-kit/api/kit/room/update?id=5b03b45dad9ef5662a507ae7")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateGameRoom), GameRoom.class)
                .retrieve().bodyToMono(GameRoom.class);
        if (mono == null) {
            System.err.println("mono is null");
            return;
        }

        GameRoom gameRoom = mono.block();
        System.out.println("name: " + gameRoom.getName());
    }

}
