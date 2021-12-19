package com.ctream.tutorial.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class SwitchMapTest {

    @Test
    void flatMap() {
        var data = Flux
                .just( "re", "rea", "reac", "react", "reactive" )
                .delayElements( Duration.ofMillis( 100 ) )
                .switchMap( this::lookup );

        StepVerifier.create( data )
                .expectNext( "reactive -> reactive" )
                .verifyComplete();
    }

    private Flux<String> lookup( String word ) {
        return Flux.just( word + " -> reactive" )
                .delayElements( Duration.ofMillis( 200 ) );
    }


}
