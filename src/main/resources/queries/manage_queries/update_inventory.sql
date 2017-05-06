UPDATE Inventory
SET Inventory.aisle=?,
    Inventory.qty=?
WHERE Inventory.product_id=? AND  Inventory.store_id=?;
