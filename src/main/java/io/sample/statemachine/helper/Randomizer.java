package io.sample.statemachine.helper;

import org.springframework.stereotype.Component;

@Component
public class Randomizer {

    public Double generateNumber() {
        return (Math.random() * 100);
    }

}
