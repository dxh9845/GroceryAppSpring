-- SET @storeID=?
-- SELECT
-- 	product.product_id,
-- 	product.name,
-- 	product.price,
-- 	store.name,
-- 	inventory.qty,
-- 	inventory.aisle
-- 	FROM
-- 		inventory, product, store
-- 	WHERE
-- 		inventory.product_id = product.product_id
-- 		AND
-- 		inventory.store_ID = @storeId
-- 		AND
-- 		inventory.store_id = store.store_id
-- 		AND
-- 		lower(product.name) LIKE ?

SELECT *
FROM PRODUCT
WHERE lower(product.name) LIKE ?