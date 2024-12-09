SELECT pc.model, pc.speed, pc.hd
FROM pc
         INNER JOIN product ON product.model = pc.model
WHERE (product.type = 'PC')
  AND (pc.price < 500)
