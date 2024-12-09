SELECT DISTINCT p1.maker
FROM product p1
WHERE NOT EXISTS (SELECT p2.maker FROM product p2 WHERE (p2."type" = 'Laptop') AND (p1.maker = p2.maker))
  AND (p1."type" = 'PC')
