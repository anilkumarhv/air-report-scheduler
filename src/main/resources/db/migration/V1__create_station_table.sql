CREATE SEQUENCE  IF NOT EXISTS station_seq_gen START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS station (
   id BIGINT NOT NULL DEFAULT nextval('station_seq_gen') PRIMARY KEY,
   city VARCHAR(255),
   country VARCHAR(255),
   elevation_ft VARCHAR(255),
   elevation_m VARCHAR(255),
   gps VARCHAR(255),
   iata VARCHAR(255),
   icao VARCHAR(255),
   latitude VARCHAR(255),
   local VARCHAR(255),
   longitude VARCHAR(255),
   name VARCHAR(255),
   note VARCHAR(255),
   reporting VARCHAR(255),
   state VARCHAR(255),
   type VARCHAR(255),
   website VARCHAR(255),
   wiki VARCHAR(255)
);

CREATE INDEX idx_station_icao_iata_local ON station (icao, iata, local);