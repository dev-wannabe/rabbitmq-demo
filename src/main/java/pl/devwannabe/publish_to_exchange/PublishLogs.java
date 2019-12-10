package pl.devwannabe.publish_to_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PublishLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            int count = 1;
            while (count < 5000) {
                String message = "Log number " + count;

                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" Opublikowano: '" + message + "'");
                count++;
                Thread.sleep(1000);
            }
        }
    }
}
