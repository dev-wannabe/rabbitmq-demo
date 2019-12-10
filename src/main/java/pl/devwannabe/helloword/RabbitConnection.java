package pl.devwannabe.helloword;


import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface RabbitConnection {

    String QUEUE_NAME = "moja.kolejka";

    default ConnectionFactory connection() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://guest:guest@localhost");
        connectionFactory.setConnectionTimeout(100000);
        return connectionFactory;
    }
}