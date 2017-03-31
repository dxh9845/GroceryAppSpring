CREATE TABLE Customer(
	user_id int, 
	pref_store_id int,
	PRIMARY KEY(user_id)
	FOREIGN KEY(user_id) REFERENCES App_User(user_id),
	FOREIGN KEY(pref_store_id) REFERENCES Store(store_id));