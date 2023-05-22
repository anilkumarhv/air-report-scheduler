ALTER TABLE metar DROP COLUMN IF EXISTS isIcingType;

ALTER TABLE metar DROP COLUMN IF EXISTS isSkyType;

ALTER TABLE metar DROP COLUMN IF EXISTS isTurbulenceType;

ALTER TABLE metar DROP COLUMN IF EXISTS isQualityControlFlagType;

-- deleting duplicate columns
ALTER TABLE metar ADD IF NOT EXISTS is_icing_type BOOLEAN;

ALTER TABLE metar ADD IF NOT EXISTS is_sky_type BOOLEAN;

ALTER TABLE metar ADD IF NOT EXISTS is_turbulence_type BOOLEAN;

ALTER TABLE metar ADD IF NOT EXISTS is_quality_control_flag_type BOOLEAN;
