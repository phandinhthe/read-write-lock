package org.example.subscriber;

import org.example.common.Color;
import org.example.topic.Topic;

public class SubscriberImpl implements Subscriber {
	private final String name;
	private final Color color;

	public SubscriberImpl(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	@Override
	public void process(String message) {
		try {
			Thread.sleep(1000L);
			System.out.printf(color.encodedColor() + "... %s has subscribed '%s' ...\n\n" + Color.BLACK.encodedColor(), name, message);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	@Override
	public void register(Topic topic) {
		topic.add(this);
	}

	@Override
	public void deregister(Topic topic) {
		topic.remove(this);
	}
}
