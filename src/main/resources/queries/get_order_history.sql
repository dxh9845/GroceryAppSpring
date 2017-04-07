-- Gets all the dates of order in desc order

SELECT Order.order_time
FROM Order
GROUP BY Order.order_time
ORDER BY Order.order_time DESC;


CREATE VIEW Order_Info AS
SELECT Order_Product.count,
       Product.price,
       Product.name,
       Order.order_time
FROM Order_Product
JOIN Product ON Order_Product.product_id = Product.product_id
JOIN Order ON Order_Product.order_id = Order.order_id
ORDER BY Order.order_time DESC;

-- Gets all order items for a certain date

SELECT Product.Name,
       Order_Product.qty,
FROM Order_Info
WHERE Order_Info.order_time = ?;
