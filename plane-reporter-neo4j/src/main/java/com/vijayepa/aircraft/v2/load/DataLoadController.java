package com.vijayepa.aircraft.v2.load;

import com.vijayepa.aircraft.v2.load.DataLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLoadController {

    private final DataLoader dataLoader;

    public DataLoadController(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @GetMapping("/load")
    String loadData(){
        dataLoader.loadData();
        return "load";
    }
}
