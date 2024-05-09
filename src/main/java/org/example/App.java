package org.example;

import org.example.common.Color;
import org.example.producer.Producer;
import org.example.producer.ProducerImpl;
import org.example.subscriber.Subscriber;
import org.example.subscriber.SubscriberImpl;
import org.example.thread.ManualThread;
import org.example.topic.Topic;
import org.example.topic.TopicImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.example.common.Color.BLACK;
import static org.example.common.Color.ORANGE;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) {
		Topic topic = new TopicImpl("interview-result");
		Producer producer = new ProducerImpl(topic);

		Subscriber subscriber = new SubscriberImpl("phandinhthe1991@gmail.com", Color.GREEN);
		subscriber.register(topic);
		Subscriber redSubscriber = new SubscriberImpl("terry_phan1991@gmail.com", Color.RED);
		redSubscriber.register(topic);
		Subscriber yellowSubscriber = new SubscriberImpl("phandinhthe1991@gmail.com", Color.YELLOW);
		yellowSubscriber.register(topic);
		Subscriber magentaSubscriber = new SubscriberImpl("phandinhthe1991@gmail.com", Color.MAGENTA);
		magentaSubscriber.register(topic);


		String[] messages = new String[]{
				"Hello Phan!",
				"You made the incredible performance in the interviews",
				"Your skills and knowledge, experience are what we need",
				"Here is our offer letter for you..."};

		demoTopicPubSub(topic, messages, producer);


	}

	private static void demoTopicPubSub(Topic topic, String[] messages, Producer producer) {
		System.out.println(ORANGE.encodedColor() +
				"================== Message System - Java Multithreading using BlockingQueue ===============\n"
				+ BLACK.encodedColor()
		);
		long start = System.currentTimeMillis();
		try (ExecutorService service = Executors.newFixedThreadPool(2)) {
			service.submit(topic::process);
			service.submit(() -> Stream.of(messages).forEach(producer::produce));

			if (!service.awaitTermination(6000L, TimeUnit.MILLISECONDS)) {
				ManualThread.stop((ManualThread) topic);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		} finally {
			long end = System.currentTimeMillis();
			System.out.printf(ORANGE.encodedColor()
					+ "================== Finished in %.3f seconds, See you later ===============\n"
					+ BLACK.encodedColor(), (end - start) * 1.0f / 1000);
		}
	}
}
