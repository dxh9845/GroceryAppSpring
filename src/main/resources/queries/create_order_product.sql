CREATE TABLE Order_Product(order_id int NOT NULL, product_id int NOT NULL, COUNT int, PRIMARY KEY (order_id, product_id),
                           FOREIGN KEY (order_id) REFERENCES Order(order_id)
                           FOREIGN KEY (product_id) REFERENCES Product(product_id));