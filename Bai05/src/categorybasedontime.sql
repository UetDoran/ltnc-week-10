SELECT
    f.title AS film_title,
    c.name AS category_name,
    f.length,
    -- Cú pháp Window Function: Xếp hạng (Rank), Phân chia theo Thể loại (Partition by), Sắp xếp theo Thời lượng giảm dần (Order by)
    RANK() OVER (PARTITION BY c.name ORDER BY f.length DESC) AS rank_in_category
FROM film f
         JOIN film_category fc ON f.film_id = fc.film_id
         JOIN category c ON fc.category_id = c.category_id
ORDER BY category_name, rank_in_category;