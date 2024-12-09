SELECT laptop.model, laptop.ram, laptop.screen
FROM laptop
         INNER JOIN product
                    ON ((product.model = laptop.model) AND (product."type" = 'Laptop') AND (laptop.price > 1000))
