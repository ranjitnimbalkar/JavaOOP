package test.reactive.types;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class TestReactiveTypes {

    public static void main(String[] args) throws InterruptedException {

        SampleSubscriber<Integer> integerCustomSubscriber = new SampleSubscriber<>();

        Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(3))
                .subscribe(integerCustomSubscriber);

        Thread.currentThread().join();

    }
}
