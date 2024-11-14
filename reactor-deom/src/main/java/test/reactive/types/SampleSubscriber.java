package test.reactive.types;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    public void hookOnNext(T value) {

        System.out.println(Thread.currentThread().getName() +": "+ value);
        request(1);
    }
}