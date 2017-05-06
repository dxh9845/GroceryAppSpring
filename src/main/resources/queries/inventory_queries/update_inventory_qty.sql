UPDATE Inventory
SET qty = Inventory.qty - ?
WHERE store_id = ? AND product_id = ?;