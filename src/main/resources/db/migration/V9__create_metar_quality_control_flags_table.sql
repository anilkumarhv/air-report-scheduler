CREATE SEQUENCE IF NOT EXISTS metar_quality_control_flags_SEQ START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS metar_quality_control_flags
(
    id                         BIGINT NOT NULL DEFAULT nextval('metar_quality_control_flags_SEQ') PRIMARY KEY,
    corrected                  VARCHAR(255),
    auto                       VARCHAR(255),
    auto_station               VARCHAR(255),
    maintenance_indicator_on   VARCHAR(255),
    no_signal                  VARCHAR(255),
    lightning_sensor_off       VARCHAR(255),
    freezing_rain_sensor_off   VARCHAR(255),
    present_weather_sensor_off VARCHAR(255),
    metar_id                   BIGINT
);

ALTER TABLE metar_quality_control_flags
    ADD CONSTRAINT FK_METAR_QUALITY_CONTROL_FLAGS_ON_METAR FOREIGN KEY (metar_id) REFERENCES metar (id);