
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS App_User;
DROP TABLE IF EXISTS Store;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Inventory;
DROP TABLE IF EXISTS Grocery_Order;
DROP TABLE IF EXISTS Order_Product;
DROP TABLE IF EXISTS Shopping_Cart;


CREATE TABLE IF NOT EXISTS Role(
  role_id int auto_increment,
  role_desc varchar(255),
  PRIMARY KEY(role_id)
);

CREATE TABLE IF NOT EXISTS App_User(
	user_id int auto_increment,
	username varchar(25) UNIQUE,
	name varchar(25),
	password varchar(16),
	phone char(10),
	address varchar(100),
	role_id int,
	PRIMARY KEY(user_id),
	FOREIGN KEY(role_id) REFERENCES Role(role_id));

CREATE TABLE IF NOT EXISTS Store(
  store_id int auto_increment,
  name varchar(25),
  location varchar(50),
  PRIMARY KEY (store_id));

CREATE TABLE IF NOT EXISTS Customer(
	user_id int,
	pref_store_id int,
	primary key(user_id),
	foreign key(user_id) references App_User(user_id),
	foreign key(pref_store_id) references Store(store_id));

CREATE TABLE IF NOT EXISTS Employee(
	user_id int,
	schedule varchar(600),
	salary numeric(10,2),
	work_store_id int,
	PRIMARY KEY(user_id),
	FOREIGN KEY(work_store_id) REFERENCES Store(store_id));

CREATE TABLE IF NOT EXISTS Product(
  product_id char(14),
  name varchar(100),
  price numeric(5, 2),
  PRIMARY KEY (product_id));

CREATE TABLE IF NOT EXISTS Inventory(
  product_id char(14),
  store_id int,
  qty int,
  aisle int,
  PRIMARY KEY(product_id, store_id),
  FOREIGN KEY (product_id) REFERENCES Product(product_id),
  FOREIGN KEY (store_id) REFERENCES Store(store_id)
);

CREATE TABLE IF NOT EXISTS Grocery_Order(
  order_id int auto_increment,
  order_time DATETIME,
  store_id int NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY (order_id),
  FOREIGN KEY (store_id) REFERENCES Store(store_id),
  FOREIGN KEY (user_id) REFERENCES App_User(user_id));

CREATE TABLE IF NOT EXISTS Order_Product(
	order_id int auto_increment,
	product_id char(14),
	qty int,
	PRIMARY KEY (order_id, product_id),
	FOREIGN KEY (order_id) REFERENCES Grocery_Order(order_id),
  FOREIGN KEY (product_id) REFERENCES Product(product_id));


CREATE TABLE IF NOT EXISTS Shopping_Cart(
  customer_id int,
  product_id char(14),
  qty int,
  PRIMARY KEY(customer_id, product_id),
  FOREIGN KEY(customer_id) REFERENCES Customer(user_id),
  FOREIGN KEY(product_id) REFERENCES Product(product_id)
);
