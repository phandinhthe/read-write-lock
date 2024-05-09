package org.example.producer;

import org.example.topic.Topic;

public class ProducerImpl implements Producer {
	private final Topic topic;

	public ProducerImpl(Topic topic) {
		this.topic = topic;
	}

	@Override
	public  void produce(String message) {
		topic.pushMessage(message);
	}

}
