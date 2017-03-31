CREATE TABLE Order(order_id int NOT NULL, order_time datetime, store_id int, user_id int, PRIMARY KEY (order_id),
                   FOREIGN KEY (store_id) REFERENCES Store(store_id),
                   FOREIGN KEY (user_id) REFERENCES User(user_id));