package javaa.asynchronous.programatically.create.sequence;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.stream.IntStream;

public class AsynchronousSinkTestWithPush {
    //Create can Publish in multiple Threads and subscribe in another Thread
    public static void main(String[] args) throws InterruptedException {
        Flux.push(
                fluxSink -> {
                    Schedulers.parallel().schedule(() ->
                    IntStream.range(0, 10)
                            .forEach(i -> fluxSink.next(Thread.currentThread().getName() + ": " + i)
                    ));
                },
                FluxSink.OverflowStrategy.BUFFER)
         .subscribe(s -> {
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println(Thread.currentThread().getName() + ": " + s);

         }, throwable -> {}, () -> {
            System.out.println(Thread.currentThread().getName() + " Completed..");
        });

        Thread.currentThread().join();
    }
}
