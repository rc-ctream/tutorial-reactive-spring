package com.ctream.tutorial.reactive;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Log4j2
class ReactiveSpringApplicationTests {

    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    @Test
    void contextLoads() {

        Flux<Integer> flux = Flux.create( emitter -> this.launch( emitter, 5 ) );
        StepVerifier.create( flux.doFinally( signalType -> this.executorService.shutdown() ) )
                .expectNextCount( 5 )
                .verifyComplete();
    }

    private void launch( FluxSink<Integer> emitter, int count ) {
        this.executorService.submit( () -> {
            IntStream.range( 0, count ).forEach( index -> {
                var random = Math.random();
                emitter.next( index );
                System.out.println( count );
                this.sleep( ( long ) ( random * 1_000 ) );
            } );
            System.out.println( "finished" );
            emitter.complete();
        } );

    }

    private void sleep( long l ) {
        try {
            Thread.sleep( l );
        } catch ( Exception e ) {
        }
    }

}
