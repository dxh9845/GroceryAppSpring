UPDATE Shopping_Cart
SET qty = ?
WHERE ShoppingCart.user_id = ? AND ShoppingCart.product_id = ?;