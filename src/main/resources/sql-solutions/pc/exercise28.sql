SELECT COUNT(sub.maker) count
FROM (
    SELECT product.maker
    FROM product
    GROUP BY product.maker
    HAVING COUNT(DISTINCT product.model) = 1
) sub
