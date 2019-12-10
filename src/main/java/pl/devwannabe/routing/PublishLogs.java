package pl.devwannabe.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PublishLogs {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            int count = 1;

            Map<Integer, String> severity = new HashMap<>();
            severity.put(0, "info");
            severity.put(1, "warning");
            severity.put(2, "error");

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