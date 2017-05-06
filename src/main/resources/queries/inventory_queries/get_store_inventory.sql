SELECT 
	product.product_id,
	product.name,
	product.price,
	inventory.qty,
	inventory.aisle,
	inventory.store_id,
	store.store_id
	FROM 
		inventory, product, store
	WHERE 
		inventory.product_id = product.product_id
		AND
		inventory.store_id = ?
		AND 
		inventory.store_id = store.store_id
