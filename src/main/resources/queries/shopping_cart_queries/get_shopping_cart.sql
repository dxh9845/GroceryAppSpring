SELECT
  Product.product_id,
	Product.name,
	Product.price,
	Shopping_Cart.qty

FROM
	Shopping_Cart, Product
WHERE
	Shopping_Cart.customer_id = ?
	AND
	Shopping_Cart.product_id = Product.product_id;
