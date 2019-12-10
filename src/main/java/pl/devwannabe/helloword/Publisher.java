package pl.devwannabe.helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Publisher implements RabbitConnection {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {

        Publisher publisher = new Publisher();

        Connection connection = publisher.connection().newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        int count = 1;

        while (count < 5000) {
            String message = "Wiadomość o numerze: " + count;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Opublikowano: " + message);

            count++;
            Thread.sleep(2000);
        }
    }
}