SELECT l.id, l.local_id, l.name, l.nearest_town, cr.latitude AS "lat", cr.longitude AS "lng", ct.name, s.abbreviation, s.name AS "state"
FROM lake AS l
JOIN lake_fish AS lf ON l.id = lf.lake_id
JOIN fish AS f ON lf.fish_id = f.id
JOIN coordinates AS cr ON l.coordinates_id = cr.id
JOIN county AS ct ON ct.id = l.county_id
JOIN state AS s ON s.id = ct.state_id
WHERE l.name NOT LIKE "%unnamed%"
AND cr.latitude != 0 AND cr.longitude != 0
GROUP BY l.id
ORDER BY COUNT(f.id) DESC;