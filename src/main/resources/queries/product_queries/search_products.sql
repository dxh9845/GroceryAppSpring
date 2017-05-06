SELECT *
FROM PRODUCT
WHERE regexp_replace(lower(product.name), '''+', '') LIKE ?