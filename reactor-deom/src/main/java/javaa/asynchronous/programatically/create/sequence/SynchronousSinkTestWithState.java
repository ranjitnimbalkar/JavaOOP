package javaa.asynchronous.programatically.create.sequence;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SynchronousSinkTestWithState {

    public static void main(String[] args) {
        Flux<Object> yo_yo = Flux.generate(
                AtomicInteger::new, // initial state
                (atomicInteger, synchronousSink) -> {
                    int currentState = atomicInteger.getAndIncrement();
                    synchronousSink.next("3 x " + currentState + " = " + 3*currentState);
                    if(currentState == 10) synchronousSink.complete();
                    return atomicInteger;
                }, //
                atomicInteger -> {
                    System.out.println("Final State(You can clean or clear state like closing DB Connection, " +
                            "any sort of closeup operations on state) : " + atomicInteger.get());
                }
        );

        yo_yo.subscribe(s -> System.out.println(Thread.currentThread().getName() + ": " +s));
    }
}
