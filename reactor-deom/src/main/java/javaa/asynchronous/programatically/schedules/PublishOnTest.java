package javaa.asynchronous.programatically.schedules;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class PublishOnTest {
    public static void main(String[] args) {
        Scheduler myScheduler = Schedulers.newParallel("my-parallel", 4);

        Flux<String> flux = Flux.range(1, 2)
                .map(i -> {
                    System.out.println("1st MAP thread : " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(myScheduler)
                .map(i -> {
                    System.out.println("2nd MAP thread : "+ Thread.currentThread().getName());
                    return "Mul by 2 : " + i;
                });

        new Thread(() -> flux.subscribe(s -> System.out.println(s))).start();
    }
}
