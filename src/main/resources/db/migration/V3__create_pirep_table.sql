create sequence IF NOT EXISTS pirep_seq_gen START with 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

create TABLE IF NOT EXISTS pirep (
    id BIGINT NOT NULL DEFAULT nextval('pirep_seq_gen') PRIMARY KEY,
    code VARCHAR(255),
    type VARCHAR(255),
    aircraft_ref VARCHAR(255),
    altitude_ft_msl INTEGER,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    observation_time TIMESTAMP(6) WITH TIME ZONE,
    raw_text VARCHAR(5120),
    receipt_time TIMESTAMP(6) WITH TIME ZONE,
    report_type VARCHAR(255),
    tempc DOUBLE PRECISION,
    vert_gust_kt VARCHAR(255),
    visibility_statute_mi INTEGER,
    wind_dir_degrees INTEGER,
    wind_speed_kt INTEGER,
    wx_string VARCHAR(255)
);

create index IF NOT EXISTS idx_pirep_code_type on pirep(code, type);