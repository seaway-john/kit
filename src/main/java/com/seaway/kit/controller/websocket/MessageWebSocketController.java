package com.seaway.kit.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {

    @MessageMapping("/user/send")
    public void userMessage(String message) {
    }

    @MessageMapping("/admin/send")
    @SendTo("/topic/broadcast")
    public String broadcast(String message) {
        return "Admin: " + message;
    }

    @MessageMapping("/turing/send")
    @SendToUser("/topic/turing")
    public String turing(String message) {
        return "Turing: " + message;
    }

}
