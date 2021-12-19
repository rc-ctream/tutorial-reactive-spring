package com.ctream.tutorial.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

public class ReplayProcessorTest {

    @Test
    void replayProcessor() {
        int historySize = 2;

        ReplayProcessor<String> processor = ReplayProcessor.create( historySize, false );
        processor.sink().next( "1" );
        processor.sink().next( "2" );
        processor.sink().next( "3" );
        processor.sink().complete();

        consume( processor );
    }

    private void consume( Flux<String> publisher ) {
        IntStream.range( 0, 5 ).forEach( index -> {
            StepVerifier.create( publisher )
                    .expectNext( "1" )
                    .expectNext( "2" )
                    .expectNext( "3" )
                    .verifyComplete();
        } );
    }
}
