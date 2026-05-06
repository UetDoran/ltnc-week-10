-- Bước 1: Tạo bảng nháp tính tổng tiền của từng khách hàng
WITH CustomerTotals AS (
    SELECT
        customer_id,
        SUM(amount) AS total_spent
    FROM payment
    GROUP BY customer_id
)
-- Bước 2: Dùng bảng nháp đó để lọc ra những người vượt mức trung bình
SELECT
    c.first_name,
    c.last_name,
    ct.total_spent
FROM customer c
         JOIN CustomerTotals ct ON c.customer_id = ct.customer_id
WHERE ct.total_spent > (
    -- Truy vấn con: Tính mức trung bình từ bảng nháp
    SELECT AVG(total_spent) FROM CustomerTotals
)
ORDER BY ct.total_spent DESC;