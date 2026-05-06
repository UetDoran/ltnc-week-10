SELECT
    c.first_name,
    c.last_name,
    c.email,
    COUNT(r.rental_id) AS total_rentals
FROM customer c
         JOIN rental r ON c.customer_id = r.customer_id
-- Gom nhóm theo từng khách hàng
GROUP BY c.customer_id, c.first_name, c.last_name, c.email
-- LỌC SAU KHI GOM NHÓM: Chỉ lấy ai có tổng số lượt thuê > 30
HAVING total_rentals > 30
ORDER BY total_rentals DESC;