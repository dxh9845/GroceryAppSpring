-- Gets all the dates of order in desc order
SELECT Grocery_Order.order_time
FROM Grocery_Order
GROUP BY Grocery_Order.order_time
ORDER BY Grocery_Order.order_time DESC;

-- Combines Order_Product with its foreign keys
CREATE VIEW Order_Info AS
SELECT Order_Product.count,
       Product.price,
       Product.name,
       Grocery_Order.order_time
FROM Order_Product
JOIN Product ON Order_Product.product_id = Product.product_id
JOIN Grocery_Order ON Order_Product.order_id = Grocery_Order.order_id
ORDER BY Grocery_Order.order_time DESC;

-- Gets all order items for a certain date
SELECT Product.Name,
       Order_Product.qty,
FROM Order_Info
WHERE Order_Info.order_time = ?;
