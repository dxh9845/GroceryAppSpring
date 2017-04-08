CREATE PROCEDURE search_products(
	@searchTerm varchar(50),
	@preferredStoreID INT
)
AS 
BEGIN 
	SELECT 
		product.product_id
		product.name,
		product.price,
		store.name,
		inventory.count,
		inventory.aisle
		FROM 
			inventory, product, store
		WHERE 
			inventory.product_id = product.product_id
			AND
			inventory.store_ID = @preferredStoreID
			AND 
			store.store_id = store.store_id
			AND
			@searchTerm LIKE product.name

END