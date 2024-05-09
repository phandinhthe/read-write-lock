package org.example.subscriber;

import org.example.topic.Topic;

public interface Subscriber {
	void process(String message);

	void register(Topic topic);

	void deregister(Topic topic);

}
