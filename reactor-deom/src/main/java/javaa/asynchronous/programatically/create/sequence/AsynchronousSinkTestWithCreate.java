package javaa.asynchronous.programatically.create.sequence;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class AsynchronousSinkTestWithCreate {
    //Create can Publish in multiple Threads and subscribe in another Thread
    public static void main(String[] args) throws InterruptedException {
        Flux.create(
                fluxSink -> {
                    IntStream.range(0, 10)
                            .forEach(i -> {
                                Disposable disposable = Schedulers.parallel().schedule(() -> fluxSink.next(Thread.currentThread().getName() + ": " + i));
                            }

                    );
                },
                FluxSink.OverflowStrategy.IGNORE)
        .subscribe(s -> System.out.println(Thread.currentThread().getName() +": " +s), throwable -> {}, () -> {
            System.out.println(Thread.currentThread().getName() + " Completed..");
        });

        Thread.currentThread().join();
    }
}
