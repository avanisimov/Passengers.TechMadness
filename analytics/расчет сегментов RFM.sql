DROP TABLE IF EXISTS  client_rfm_segments;
CREATE TABLE  client_rfm_segments AS
WITH m_bins AS (
  SELECT
         percentile_disc(0.25) WITHIN GROUP (ORDER BY total_amount) AS M_1
       , percentile_disc(0.5) WITHIN GROUP (ORDER BY total_amount)  AS M_2
       , percentile_disc(0.75) WITHIN GROUP (ORDER BY total_amount) AS M_3
  FROM
    clients
  WHERE
    total_amount > 0
)
, r_bins AS (
    SELECT
        percentile_disc(0.25) WITHIN GROUP (ORDER BY days_from_last_date) AS R_1
       , percentile_disc(0.5) WITHIN GROUP (ORDER BY days_from_last_date)  AS R_2
       , percentile_disc(0.75) WITHIN GROUP (ORDER BY days_from_last_date) AS R_3

    FROM (
         SELECT
            uid
            , (CURRENT_DATE - last_date) AS days_from_last_date
          FROM
           clients
          WHERE
           (CURRENT_DATE - last_date) >1
       ) AS t

)
, f_bins AS (
  SELECT
         percentile_disc(0.25) WITHIN GROUP (ORDER BY orders_count) AS F_1
       , percentile_disc(0.5) WITHIN GROUP (ORDER BY orders_count)  AS F_2
       , percentile_disc(0.75) WITHIN GROUP (ORDER BY orders_count) AS F_3
  FROM
        clients
  WHERE
        orders_count > 0
)
, rfm AS (
  SELECT
        uid
       , CASE
           WHEN (CURRENT_DATE - last_date) <= R_1 THEN 'R_1'
           WHEN (CURRENT_DATE - last_date) > R_1 AND (CURRENT_DATE - last_date) <= R_2 THEN 'R_2'
           WHEN (CURRENT_DATE - last_date) > R_2 AND (CURRENT_DATE - last_date) <= R_3 THEN 'R_3'
           ELSE 'R_4'
    END AS R_class
       , CASE
           WHEN orders_count <= F_1 THEN 'F_4'
           WHEN orders_count > F_1 AND orders_count <= F_2 THEN 'F_2'
           WHEN orders_count > F_2 AND orders_count <= F_3 THEN 'F_3'
           ELSE 'F_1'
    END AS F_class
       , CASE
           WHEN total_amount <= M_1 THEN 'M_1'
           WHEN total_amount > M_1 AND total_amount <= M_2 THEN 'M_2'
           WHEN total_amount > M_2 AND total_amount <= M_3 THEN 'M_3'
           ELSE 'M_4'
    END AS M_class

  FROM
    clients, r_bins, f_bins, m_bins

)
SELECT
  *
  , CASE
      WHEN r_class = 'R_4' THEN 'Ушедшие'
      WHEN r_class IN ('R_3', 'R_2') AND  f_class = 'F_1' THEN 'Уходящие VIP'
      WHEN r_class = 'R_3' AND  f_class IN ('F_3', 'F_2')  THEN 'Уходящие'
      WHEN r_class = 'R_1'AND  f_class = 'F_1' THEN 'VIP'
      WHEN r_class = 'R_3'AND  f_class = 'F_4' THEN 'Одноразовые'
      WHEN r_class = 'R_1'AND  f_class = 'F_4' THEN 'Новички'
      ELSE 'Обычные'
  END AS rfm_segment
FROM
  rfm
;
