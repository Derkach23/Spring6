package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.EnableIntegration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileExistsMode;
import org.springframework.integration.file.dsl.Files;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public MessageChannel requestChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow fileWritingFlow() {
        return IntegrationFlow
                .from("requestChannel")
                .handle(Files.outboundAdapter(new File("logs/requests"))
                        .appendNewLine(true)
                        .fileExistsMode(FileExistsMode.APPEND))
                .get();
    }
}
