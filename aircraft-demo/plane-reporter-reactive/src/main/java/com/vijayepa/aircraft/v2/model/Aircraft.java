package com.vijayepa.aircraft.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.util.function.Consumer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@RedisHash
public class Aircraft {

    private String callsign, squawk, reg, flightno, route, type, category;
    private int altitude, heading, speed;
    private double lat, lon, barometer;

    @Id
    private Long id;


    @JsonProperty("vert_rate")
    private int vertRate;

    @JsonProperty("selected_altitude")
    private int selectedAltitude;

    @JsonProperty("polar_distance")
    private double polarDistance;

    @JsonProperty("polar_bearing")
    private double polarBearing;

    @JsonProperty("is_adsb")
    private boolean isADSB;

    @JsonProperty("is_on_ground")
    private boolean isOnGround;

    @JsonProperty("last_seen_time")
    private Instant lastSeenTime;

    @JsonProperty("pos_update_time")
    private Instant posUpdateTime;

    @JsonProperty("bds40_seen_time")
    private Instant bds40SeenTime;


}
