package tacos;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import tacos.messaging.JmsOrderMessagingService;
import tacos.messaging.JmsOrderReceiver;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    @Autowired
    ApplicationRunner applicationRunner(OrderProps orderProps, JmsOrderMessagingService jmsOrderMessagingService,
                                        JmsOrderReceiver jmsOrderReceiver) {
        return args -> {
            System.out.println(orderProps);
//            Order order = new Order();
//            order.setCity("Moscow");
//            jmsOrderMessagingService.sendOrder(order);
            System.out.println(jmsOrderReceiver.receiveOrder());
        };
    }
    
    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }
}
