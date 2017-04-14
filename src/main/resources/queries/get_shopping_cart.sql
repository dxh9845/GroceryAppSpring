CREATE VIEW Shopping_Cart_View AS
SELECT
	Product.name,
	Product.price,
	Shopping_Cart.qty,
	Customer.user_id
FROM
	Shopping_Cart, Customer, Product
WHERE
	Shopping_Cart.customer_id = Customer.user_id
	AND
	Shopping_Cart.product_id = Product.product_id;
	
CREATE PROCEDURE get_shopping_cart(
	@search_id int
)	
AS
BEGIN
	SELECT
		Shopping_Cart_View.name,
		Shopping_Cart_View.price,
		Shopping_Cart_View.qty
	FROM
		Shopping_Cart_View
	WHERE
		Shopping_Cart_View.User_id = @search_id
END
	

	
