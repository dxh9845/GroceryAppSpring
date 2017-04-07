CREATE TABLE Inventory(
  product_id varchar(14) NOT NULL,
  store_id(14) NOT NULL,
  qty integer, aisle integer,
  PRIMARY KEY(product_id, store_id),
  FOREIGN KEY (product_id) REFERENCES Product(product_id),
  FOREIGN KEY (store_id) REFERENCES Store(store_id)
);
