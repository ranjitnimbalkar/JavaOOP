package javaa.asynchronous.programatically.create.sequence;

import reactor.core.publisher.Flux;

import java.asynchronous.programming.service.UserService;

import static java.lang.Thread.sleep;

public class SynchronousSinkTest {

    public static void main(String[] args) {
        Flux<Object> yo_yo = Flux.generate(sink -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sink.next("Yo yo");
            sink.complete();
        });

        yo_yo.subscribe(s -> System.out.println(Thread.currentThread().getName() + ": " +s));
    }
}
