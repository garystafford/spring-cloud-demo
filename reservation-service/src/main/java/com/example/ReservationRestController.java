package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
class ReservationRestController {
    @Value("${message}")
    private String message;

    @RequestMapping("/message")
    public String getMessage() {
        return message;
    }
}
