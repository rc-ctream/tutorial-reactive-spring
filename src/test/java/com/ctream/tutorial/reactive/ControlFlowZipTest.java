package com.ctream.tutorial.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class ControlFlowZipTest {

    @Test
    void zipTest() {
        var numbers = Flux.just( 1, 2, 3 ).delayElements( Duration.ofMillis( 400 ) );
        var characters = Flux.just( "a", "b", "c" );

        var zip = Flux
                .zip( numbers, characters )
                .map( this::from );

        StepVerifier.create( zip )
                .expectNext( "1:a", "2:b", "3:c" )
                .verifyComplete();
    }

    private String from( Tuple2<Integer, String> tuple ) {
        return String.join( ":", String.valueOf( tuple.getT1() ), tuple.getT2() );
    }

}
