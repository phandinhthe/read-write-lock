package org.example.topic;

import org.example.subscriber.Subscriber;
import org.example.thread.ManualThread;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class TopicImpl implements Topic, ManualThread {
	private static final int DEFAULT_SIZE = 1000;
	private final Set<Subscriber> subscribers;
	private final Deque<String> messages;
	private AtomicBoolean stop;
	private final ReentrantLock lock = new ReentrantLock();

	public TopicImpl() {
		subscribers = new HashSet<>(DEFAULT_SIZE);
		messages = new ArrayDeque<>(1);
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

			if (!lock.tryLock()) continue;
			if (messages.isEmpty()) {
				lock.unlock();
				continue;
			}
			String message = messages.peek();
			subscribers.parallelStream().forEach(
					subscriber -> subscriber.process(message)
			);
			messages.poll();
			lock.unlock();
		}
	}

	@Override
	public void pushMessage(String message) {
		lock.lock();
		messages.offer(message);
		lock.unlock();
	}
}
