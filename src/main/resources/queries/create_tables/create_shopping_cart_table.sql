CREATE TABLE Shopping_Cart(
  customer_id integer NOT NULL,
  product_id integer NOT NULL,
  qty int,
  PRIMARY KEY(customer_id, product_id),
  FOREIGN KEY(customer_id) REFERENCES Customer(user_id),
  FOREIGN KEY(product_id) REFERENCES Product(product_id)
);
