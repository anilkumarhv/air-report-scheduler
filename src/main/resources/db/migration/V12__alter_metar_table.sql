ALTER TABLE metar ADD IF NOT EXISTS isIcingType BOOLEAN;

ALTER TABLE metar ADD IF NOT EXISTS isSkyType BOOLEAN;

ALTER TABLE metar ADD IF NOT EXISTS isTurbulenceType BOOLEAN;

ALTER TABLE metar ADD IF NOT EXISTS isQualityControlFlagType BOOLEAN;