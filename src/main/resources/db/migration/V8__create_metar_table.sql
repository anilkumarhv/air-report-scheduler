CREATE SEQUENCE IF NOT EXISTS metar_seq_gen START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS metar
(
    id                            BIGINT NOT NULL DEFAULT nextval('metar_seq_gen') PRIMARY KEY,
    raw_text                      VARCHAR(5120),
    station_id                    VARCHAR(255),
    observation_time              TIMESTAMP WITHOUT TIME ZONE,
    latitude                      DOUBLE PRECISION,
    longitude                     DOUBLE PRECISION,
    temp_c                        DOUBLE PRECISION,
    dewpoint_c                    DOUBLE PRECISION,
    wind_dir_degrees              INTEGER,
    wind_speed_kt                 INTEGER,
    wind_gust_kt                  INTEGER,
    visibility_statute_mi         DOUBLE PRECISION,
    altim_in_hg                   DOUBLE PRECISION,
    sea_level_pressure_mb         DOUBLE PRECISION,
    wx_string                     VARCHAR(255),
    flight_category               VARCHAR(255),
    three_hr_pressure_tendency_mb DOUBLE PRECISION,
    maxt_c                        DOUBLE PRECISION,
    mint_c                        DOUBLE PRECISION,
    maxt24hr_c                    DOUBLE PRECISION,
    mint24hr_c                    DOUBLE PRECISION,
    precip_in                     DOUBLE PRECISION,
    pcp3hr_in                     DOUBLE PRECISION,
    pcp6hr_in                     DOUBLE PRECISION,
    pcp24hr_in                    DOUBLE PRECISION,
    snow_in                       DOUBLE PRECISION,
    vert_vis_ft                   INTEGER,
    metar_type                    VARCHAR(255),
    elevation_m                   DOUBLE PRECISION,
    code                          VARCHAR(255),
    type                          VARCHAR(255)
);

create index IF NOT EXISTS idx_metar_code_type on metar(code, type);