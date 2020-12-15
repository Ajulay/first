package com.ajulay.first.task;

import com.ajulay.first.model.Message;
import com.ajulay.first.service.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class SendMessageTask {


    private final Producer producer;

    public SendMessageTask(Producer producer) {
        this.producer = producer;
    }

    // run every 3 sec
    @Scheduled(fixedRateString = "3000")
    public void send() throws ExecutionException, InterruptedException {

        ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendMessage("INPUT_DATA", "IN_KEY", LocalDate.now().toString());

        SendResult<String, String> result = listenableFuture.get();
        log.info(String.format("Produced:\ntopic: %s\noffset: %d\npartition: %d\nvalue size: %d", result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(), result.getRecordMetadata().serializedValueSize()));
    }

    public void sendMessage(Message message) throws ExecutionException, InterruptedException {

        ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendMessage("INPUT_DATA", "IN_KEY", message.toString());

        SendResult<String, String> result = listenableFuture.get();
        log.info(String.format("Produced:\ntopic: %s\noffset: %d\npartition: %d\nvalue size: %d", result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(), result.getRecordMetadata().serializedValueSize()));
    }
}