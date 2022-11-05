package com.vijayepa.aircraft.v2.ui;

import com.vijayepa.aircraft.v2.model.Aircraft;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;


@Controller
public class PositionController {

    private final RSocketRequester requester;

    public PositionController(
            RSocketRequester.Builder requesterBuilder) {
        this.requester = requesterBuilder.tcp("localhost", 7635);
    }

   @ResponseBody
    @GetMapping(value = "/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentAircraftPositionsStream(){
        return requester.route("acstream").data("Requesting aircraft positions").retrieveFlux(Aircraft.class);
    }


}

