package com.debenziumdemo.serviceproduct.runner;

import com.debenziumdemo.serviceproduct.service.DebeziumConnectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebeziumStartupRunner implements ApplicationRunner {

    private final DebeziumConnectorService debeziumConnectorService;

    @Override
    public void run(ApplicationArguments args) {
        debeziumConnectorService.registerOutboxConnector();
    }
}
