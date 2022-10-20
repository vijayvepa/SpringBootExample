package com.vijayepa.aircraft.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.function.Consumer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public String getLastSeenTime() {
        return lastSeenTime.toString();
    }

    public void setLastSeenTime(String lastSeenTime) {
        setInstantOrDefault(lastSeenTime, this::setLastSeenTimeInstant);
    }

    public void setLastSeenTimeInstant(Instant lastSeenTime){
        this.lastSeenTime = lastSeenTime;
    }

    private void setInstantOrDefault(
            String lastSeenTime,
            Consumer<Instant> lastSeenTimeInstantConsumer) {
        if (null != lastSeenTime) {
            lastSeenTimeInstantConsumer.accept(Instant.parse(lastSeenTime));
        } else {
            lastSeenTimeInstantConsumer.accept(Instant.ofEpochSecond(0));
        }
    }

    public String getPosUpdateTime() {
        return posUpdateTime.toString();
    }

    public void setPosUpdateTime(String posUpdateTime) {
        setInstantOrDefault(posUpdateTime, this::setPosUpdateTimeInstant);
    }

    public void setPosUpdateTimeInstant(Instant posUpdateTime){
        this.posUpdateTime = posUpdateTime;
    }

    public String getBds40SeenTime() {
        return bds40SeenTime.toString();
    }

    public void setBds40SeenTime(String posUpdateTime) {
        setInstantOrDefault(posUpdateTime, this::setBds40SeenTimeInstant);
    }

    public void setBds40SeenTimeInstant(Instant bds40SeenTime){
        this.bds40SeenTime = bds40SeenTime;
    }


}
