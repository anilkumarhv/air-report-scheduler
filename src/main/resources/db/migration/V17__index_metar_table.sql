create index IF NOT EXISTS idx_observation_time ON metar (observation_time);
create index IF NOT EXISTS idx_visibility_statute_mi ON metar (visibility_statute_mi);
create index IF NOT EXISTS idx_wind_gust_kt ON metar (wind_gust_kt);
create index IF NOT EXISTS idx_wx_string ON metar (wx_string);
create index IF NOT EXISTS idx_sky_condition_id ON sky_condition (id);