UPDATE Shopping_Cart
SET qty = ?
WHERE Shopping_Cart.customer_id = ? AND Shopping_Cart.product_id = ?;