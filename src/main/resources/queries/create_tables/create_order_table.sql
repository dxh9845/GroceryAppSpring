CREATE TABLE Grocery_Order(
  order_id int NOT NULL, 
  order_time SMALLDATETIME, 
  store_id int NOT NULL, 
  user_id int NOT NULL, 
  PRIMARY KEY (order_id), 
  FOREIGN KEY (store_id) REFERENCES Store(store_id),
  FOREIGN KEY (user_id) REFERENCES App_User(user_id));