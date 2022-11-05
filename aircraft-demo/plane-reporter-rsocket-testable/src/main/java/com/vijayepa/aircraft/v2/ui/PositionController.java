package com.vijayepa.aircraft.v2.ui;

import com.vijayepa.aircraft.v2.PositionService;
import com.vijayepa.aircraft.v2.model.Aircraft;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;


@Controller
public class PositionController {

    private final RSocketRequester requester;
    private final PositionService positionService;

    public PositionController(
            RSocketRequester requester,
            PositionService positionService) {
        this.requester = requester;
        this.positionService = positionService;
    }

    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(Model model){
        model.addAttribute("currentPositions", positionService.getAllAircraft());
        return "positions";
    }
    @ResponseBody
    @GetMapping(value = "/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentAircraftPositionsStream(){
        return requester.route("acstream").data("Requesting aircraft positions").retrieveFlux(Aircraft.class);
    }

    @ResponseBody
    @GetMapping("/acpos/search")
    public Publisher<Aircraft> searchForACPosition(@RequestParam Map<String, String> searchParams){

        if(searchParams.isEmpty()){
            return Mono.empty();
        }

        Map.Entry<String, String> setToSearch = searchParams.entrySet().iterator().next();

        if(setToSearch.getKey().equalsIgnoreCase("id")){
            return positionService.getAircraftById(setToSearch.getValue());
        }

        return positionService.getAircraftByReg(setToSearch.getValue());
    }

}

