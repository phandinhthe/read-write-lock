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

	public static final long DEFAULT_TIMEOUT = 11_000L;

	public static void main(String[] args) {
		Topic interviewResultTopic = new TopicImpl();
		Producer producer = new ProducerImpl(interviewResultTopic);

		Subscriber subscriber = new SubscriberImpl("Terry", Color.GREEN);
		subscriber.register(interviewResultTopic);
		Subscriber redSubscriber = new SubscriberImpl("The", Color.VIOLET);
		redSubscriber.register(interviewResultTopic);
		Subscriber yellowSubscriber = new SubscriberImpl("Phan", Color.YELLOW);
		yellowSubscriber.register(interviewResultTopic);
		Subscriber magentaSubscriber = new SubscriberImpl("Frank", Color.RED);
		magentaSubscriber.register(interviewResultTopic);


		String[] messages = new String[]{
				"Hello ... my everything!",
				"How are you?",
				"I want to say some greetings...",
				"I miss you... my everything..."};

		demoTopicPubSub(interviewResultTopic, messages, producer);


	}

	private static void demoTopicPubSub(Topic topic, String[] messages, Producer producer) {
		System.out.println(ORANGE.encodedColor() +
				"================== Message System - Java Multithreading using BlockingQueue ===============\n"
				+ BLACK.encodedColor()
		);
		long start = System.currentTimeMillis();
		try (ExecutorService service = Executors.newFixedThreadPool(4)) {
			service.submit(topic::process);
			service.submit(() -> Stream.of(messages).forEach(producer::produce));

			if (!service.awaitTermination(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)) {
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
