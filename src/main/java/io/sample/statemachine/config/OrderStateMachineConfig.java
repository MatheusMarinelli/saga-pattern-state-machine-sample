package io.sample.statemachine.config;

import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters1;
import io.sample.statemachine.domain.KafkaEvent;
import io.sample.statemachine.function.ExecutionFunction;
import io.sample.statemachine.function.InitialFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class OrderStateMachineConfig {

    private StateMachineConfig<EventStates, EventActions> stateMachineConfig;
    private TriggerWithParameters1<KafkaEvent, EventActions> trigger;

    @Autowired
    private InitialFunction initialFunction;

    @Autowired
    private ExecutionFunction executionFunction;


    @Bean
    @DependsOn("createConfig")
    public TriggerWithParameters1<KafkaEvent, EventActions> createTrigger() {
        return this.trigger;
    }

    @Bean
    public StateMachineConfig<EventStates, EventActions> createConfig() {
        stateMachineConfig = new StateMachineConfig<>();

        // definindo o trigger que será usado para inicializar as function definidas abaixo
        // quando tivermos a ação RUN com o objeto KafkaEvent será executado uma das functions abaixo mediante ao estado do objeto KafkaEvent.state
        trigger = stateMachineConfig.setTriggerParameters(EventActions.RUN, KafkaEvent.class);


        // caso a máquina esteja no estado INITIAL será execitado o método da function InitialFunction para alterar o estado da máquina
        stateMachineConfig.configure(EventStates.INITIAL).permitDynamicIf(trigger,initialFunction, () -> true);
        stateMachineConfig.configure(EventStates.EXECUTION).permitDynamicIf(trigger,executionFunction, () -> true);

        // caso o evento lido esteja no estado FINISHED/ERROR a máquina não fará nada
        stateMachineConfig.configure(EventStates.FINISHED).ignore(EventActions.RUN);
        stateMachineConfig.configure(EventStates.ERROR).ignore(EventActions.RUN);

        return stateMachineConfig;
    }




}
