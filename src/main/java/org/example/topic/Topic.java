package org.example.topic;

import org.example.producer.Producer;
import org.example.subscriber.Subscriber;

import java.util.List;

public interface Topic {

	void add(Subscriber subscriber);

	void remove(Subscriber subscriber);

	void process();

	void pushMessage(String message);
}
