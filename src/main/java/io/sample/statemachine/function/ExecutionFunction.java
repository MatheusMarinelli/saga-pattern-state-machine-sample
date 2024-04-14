package io.sample.statemachine.function;

import com.github.oxo42.stateless4j.delegates.Func2;
import io.sample.statemachine.config.EventStates;
import io.sample.statemachine.domain.KafkaEvent;
import io.sample.statemachine.helper.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecutionFunction implements Func2<KafkaEvent, EventStates> {

    @Autowired
    private Randomizer randomizer;

    @Override
    public EventStates call(KafkaEvent event) {
        // mudando de estado baseado em um evento interno (poderia ser uma integração com uma API/Kafka/Banco de dados/etc)
        if (randomizer.generateNumber() < 50) {
            event.setState(EventStates.FINISHED.name());
            return EventStates.FINISHED;
        } else {
            event.setState(EventStates.ERROR.name());
            return EventStates.ERROR;
        }
    }
}
