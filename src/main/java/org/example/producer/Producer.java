package org.example.producer;

import org.example.topic.Topic;

public interface Producer {
	void produce(String message);
}
