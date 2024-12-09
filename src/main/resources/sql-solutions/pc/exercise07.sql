SELECT sub.model, sub.price
FROM (SELECT laptop.model, laptop.price
      FROM laptop
      UNION ALL
      SELECT pc.model, pc.price
      FROM pc
      UNION
      SELECT printer.model, printer.price
      FROM printer) sub
         INNER JOIN product ON sub.model = product.model
WHERE product.maker = 'B'
