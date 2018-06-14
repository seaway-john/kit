package com.seaway.kit.pojo.mongo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Users extends BaseMongoEntity {
    private String username;
    private String password;
}
