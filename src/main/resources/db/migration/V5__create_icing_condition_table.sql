CREATE SEQUENCE  IF NOT EXISTS icing_condition_SEQ START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS icing_condition (
   id BIGINT NOT NULL DEFAULT nextval('icing_condition_SEQ') PRIMARY KEY,
   icing_type VARCHAR(255),
   icing_intensity VARCHAR(255),
   icing_base_ft_msl INTEGER,
   icing_top_ft_msl INTEGER,
   pirep_id BIGINT
);

ALTER TABLE icing_condition ADD CONSTRAINT FK_ICING_CONDITION_ON_PIREP FOREIGN KEY (pirep_id) REFERENCES pirep (id);