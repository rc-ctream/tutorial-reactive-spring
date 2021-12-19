package com.ctream.tutorial.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FlatMapTest {

    @Test
    void flatMap() {
        var data = Flux.just( Pair.of( 1, 300 ), Pair.of( 2, 200 ), Pair.of( 3, 100 ) )
                .flatMap( this::delayReplyFor );

        StepVerifier.create( data )
                .expectNext( 3, 2, 1 )
                .verifyComplete();

    }

    private Flux<Integer> delayReplyFor( Pair<Integer, Integer> pair ) {
        return Flux.just( pair.getFirst() ).delayElements( Duration.ofMillis( pair.getSecond() ) );
    }

}
