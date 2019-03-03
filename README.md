# Aggregation
Aggregate table by extracting selected information.

Run instructions
$ java Aggregate sum population 2001-2011_census_population.csv year
$ java Aggregate sum population 2001-2011_census_population.csv province year
$ java Aggregate count population 2001-2011_census_population.csv province year

$ java Aggregate count duration_minutes ferries_FebMarApr2017.csv vessel_name
$ java Aggregate avg duration_minutes ferries_FebMarApr2017.csv route
$ java Aggregate avg duration_minutes ferries_FebMarApr2017.csv route year month day

$ java Aggregate avg temperature vwsn_Feb2017_data.csv year month day hour minute
$ java Aggregate avg temperature vwsn_Feb2017_data.csv station_name day hour
$ java Aggregate avg temperature vwsn_Feb2017_data.csv station_name day