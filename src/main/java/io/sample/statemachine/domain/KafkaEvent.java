package io.sample.statemachine.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KafkaEvent {

    private String id;
    private LocalDateTime date;
    private String state;

}
