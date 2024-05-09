package org.example.topic;

import org.example.subscriber.Subscriber;
import org.example.thread.ManualThread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class TopicImpl implements Topic, ManualThread {
	private String name;
	private static final int DEFAULT_SIZE = 1000;
	private final Set<Subscriber> subscribers;
	private final BlockingQueue<String> messages;
	private AtomicBoolean stop;

	public TopicImpl(String name) {
		this.name = name;
		subscribers = new HashSet<>(DEFAULT_SIZE);
		messages = new ArrayBlockingQueue<>(1);
		stop = new AtomicBoolean(false);
	}

	public void stop() {
		stop.set(true);
	}

	@Override
	public void add(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	@Override
	public void remove(Subscriber subscriber) {
		subscribers.remove(subscriber);
	}

	@Override
	public void process() {
		while (!stop.get()) {
			try {
				if (Thread.currentThread().isInterrupted()) return;
				if (messages.isEmpty()) continue;
				String message = messages.peek();
				subscribers.parallelStream().forEach(
						subscriber -> subscriber.process(message)
				);
				messages.take();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

	@Override
	public void pushMessage(String message) {
		try {
			messages.put(message);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}
