CREATE SEQUENCE  IF NOT EXISTS quality_control_flags_SEQ START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS quality_control_flags (
   id BIGINT NOT NULL DEFAULT nextval('quality_control_flags_SEQ') PRIMARY KEY,
   mid_point_assumed VARCHAR(255),
   no_time_stamp VARCHAR(255),
   flt_lvl_range VARCHAR(255),
   above_ground_level_indicated VARCHAR(255),
   no_flt_lvl VARCHAR(255),
   bad_location VARCHAR(255),
   pirep_id BIGINT
);

ALTER TABLE quality_control_flags ADD CONSTRAINT FK_QUALITY_CONTROL_FLAGS_ON_PIREP FOREIGN KEY (pirep_id) REFERENCES pirep (id);