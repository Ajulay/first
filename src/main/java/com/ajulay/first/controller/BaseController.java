package com.ajulay.first.controller;

import com.ajulay.first.model.Message;
import com.ajulay.first.service.Producer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class BaseController {

    @Value("${kafka.enabled:true}")
    private boolean canSend;

private final Producer producer;

    @GetMapping("/hello")
    public String sayHello(@RequestParam(required = false) String name) throws ExecutionException, InterruptedException {
        String content = String.format("Hello, %s!", name);
        if(canSend) {
            ListenableFuture<SendResult<String, String>> future = producer.sendMessage("SECOND", "IN_KEY", new Message().setContent(content).setTime(new Date()).toString());
            System.out.println(future.get());
        }

        return content;
    }
}
