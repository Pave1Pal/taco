package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import tacos.Order;

@Component
public class JmsOrderReceiver {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Order receiveOrder() {
        return (Order) jmsTemplate.receiveAndConvert();
    }
}
