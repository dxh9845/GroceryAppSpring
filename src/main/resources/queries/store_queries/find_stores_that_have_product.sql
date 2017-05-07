SELECT
  store.store_id,
  store.name,
  store.location
FROM
  inventory, product, store
WHERE
  product.product_id = ?
  AND
  inventory.product_id = product.product_id
  AND
  inventory.store_id = store.store_id

