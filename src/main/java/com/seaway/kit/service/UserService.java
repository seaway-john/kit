package com.seaway.kit.service;

import com.seaway.kit.exception.NotFoundException;
import com.seaway.kit.pojo.mongo.Users;
import com.seaway.kit.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserService {

    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Users> getByUsername(final String username) {
        Users user = userRepository.findByUsername(username);

        return Mono.justOrEmpty(user).switchIfEmpty(Mono.error(new NotFoundException()));
    }

    public Mono<Void> add(Users user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            userRepository.save(user);
        }

        return Mono.when();
    }

}
