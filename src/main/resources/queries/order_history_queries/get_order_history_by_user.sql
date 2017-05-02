SELECT Product.product_id,
       Product.name,
       Product.price,
       Order_Product.qty,
       Grocery_Order.order_time,
       Grocery_Order.store_id
FROM Order_Product,
     Product,
     Grocery_Order
WHERE Order_Product.product_id = Product.product_id
    AND Order_Product.order_id = Grocery_Order.order_id
    AND Grocery_Order.user_id = ?
ORDER BY Grocery_Order.order_time DESC;