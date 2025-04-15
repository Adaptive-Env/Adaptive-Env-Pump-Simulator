package com.adaptive.environments.pump_simulator.model;

import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceData {
    private String deviceId;
    private String sensorId;
    private String location;
    private String type;
    private Long timestamp;
    private String authKey;
    private Map<String, Object> data;

    @Override
    public String toString() {
        return "DeviceData{" +
                "deviceId='" + deviceId + '\'' +
                ", sensorId='" + sensorId + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", authKey='" + authKey + '\'' +
                ", data=" + data +
                '}';
    }
}