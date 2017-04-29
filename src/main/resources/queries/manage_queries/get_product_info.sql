SELECT Product.product_id,
  Inventory.store_id,
  Product.name,
  Product.price,
  Inventory.qty,
  Inventory.aisle
FROM Product,
  Inventory
WHERE Product.product_id = Inventory.product_id
      AND  Inventory.store_id = ?;
