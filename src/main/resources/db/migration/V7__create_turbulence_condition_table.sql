CREATE SEQUENCE  IF NOT EXISTS turbulence_condition_SEQ START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS turbulence_condition(
   id BIGINT NOT NULL DEFAULT nextval('turbulence_condition_SEQ') PRIMARY KEY,
   turbulence_type VARCHAR(255),
   turbulence_intensity VARCHAR(255),
   turbulence_base_ft_msl INTEGER,
   turbulence_top_ft_msl INTEGER,
   turbulence_freq VARCHAR(255),
   pirep_id BIGINT
);

ALTER TABLE turbulence_condition ADD CONSTRAINT FK_TURBULENCE_CONDITION_ON_PIREP FOREIGN KEY (pirep_id) REFERENCES pirep (id);