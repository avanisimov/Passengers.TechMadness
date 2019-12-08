
WITH bins AS (
SELECT
  percentile_disc(0.1) WITHIN GROUP (ORDER BY  total_amount) AS percentile_10
  , percentile_disc(0.2) WITHIN GROUP (ORDER BY  total_amount) AS percentile_20
  , percentile_disc(0.3) WITHIN GROUP (ORDER BY  total_amount) AS percentile_30
  , percentile_disc(0.4) WITHIN GROUP (ORDER BY  total_amount) AS percentile_40
  , percentile_disc(0.5) WITHIN GROUP (ORDER BY  total_amount) AS percentile_50
  , percentile_disc(0.6) WITHIN GROUP (ORDER BY  total_amount) AS percentile_60
  , percentile_disc(0.7) WITHIN GROUP (ORDER BY  total_amount) AS percentile_70
  , percentile_disc(0.8) WITHIN GROUP (ORDER BY  total_amount) AS percentile_80
  , percentile_disc(0.9) WITHIN GROUP (ORDER BY  total_amount) AS percentile_90
  , percentile_disc(1) WITHIN GROUP (ORDER BY  total_amount) AS percentile_100
FROM
  clients
WHERE
  total_amount >0
)

SELECT
    percentile_10
  , count(*) FILTER (WHERE total_amount <= percentile_10) AS users_count_10
  , percentile_20
  , count(*) FILTER (WHERE total_amount between percentile_10 AND percentile_20 ) AS users_count_20
  , percentile_30
  , count(*) FILTER (WHERE total_amount between percentile_20 AND percentile_30 ) AS users_count_30
  , percentile_40
  , count(*) FILTER (WHERE total_amount between percentile_30 AND percentile_40 ) AS users_count_40
  , percentile_50
  , count(*) FILTER (WHERE total_amount between percentile_40 AND percentile_50 ) AS users_count_50
  , percentile_60
  , count(*) FILTER (WHERE total_amount between percentile_50 AND percentile_60 ) AS users_count_60
  , percentile_70
  , count(*) FILTER (WHERE total_amount between percentile_60 AND percentile_70 ) AS users_count_70
  , percentile_80
  , count(*) FILTER (WHERE total_amount between percentile_70 AND percentile_80 ) AS users_count_80
  , percentile_90
  , count(*) FILTER (WHERE total_amount between percentile_90 AND percentile_100 ) AS users_count_90
  , percentile_100
  , count(*) FILTER (WHERE total_amount >= percentile_100 ) AS users_count_100
FROM
  clients
JOIN
  bins
ON TRUE
GROUP BY
  percentile_10
  , percentile_20
  , percentile_30
  , percentile_40
  , percentile_50
  , percentile_60
  , percentile_70
  , percentile_80
  , percentile_90
  , percentile_100
