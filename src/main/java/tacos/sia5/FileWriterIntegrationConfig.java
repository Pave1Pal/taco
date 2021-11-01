package tacos.sia5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    @Autowired
    private Transformer transformer;

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(fileReader(), spec -> spec.poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform")
                .handle(fileWriter())
                .get();
    }

    @Bean
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(
                new File("src/main/java/tacos/sia5/destination"));
        handler.setExpectReply(false);
        return handler;
    }


    @Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("src/main/java/tacos/sia5/files"));
        return source;
    }
}
