ALTER TABLE sky_condition
    ADD metar_id BIGINT;

ALTER TABLE sky_condition
    ADD CONSTRAINT FK_SKY_CONDITION_ON_METAR FOREIGN KEY (metar_id) REFERENCES metar (id);