package pl.devwannabe.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PublishLogs {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            int count = 1;

            Map<Integer, String> severity = new HashMap<>();
            severity.put(0, "info.general.logs");
            severity.put(1, "warning.critical.logs");
            severity.put(2, "error.critical.logs");

            while (count < 5000) {
                String routingKey = severity.get(new Random().nextInt(3));
                String message = "Log message number: " + count + ", type: " + routingKey;

                channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                System.out.println(" Opublikowano: '" + message + "'");
                count++;
                Thread.sleep(1000);
            }
        }
    }
}