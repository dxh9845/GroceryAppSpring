INSERT INTO ROLE VALUES
(0, 'Customer'),
(1, 'Employee'),
(2, 'Manager');

INSERT INTO STORE VALUES (NULL, 'Mega Bytes','586 Corry Trail,Jacksonville,Florida,32255'),
(NULL, 'Deez Bytes', '56 Truax Alley,Roanoke,Virginia,24048'),
(NULL, 'Giga Bytes', '391 Rusk Center,Kalamazoo,Michigan,49048'),
(NULL, 'Kilo Bytes', '8275 Scott Way,Fort Pierce,Florida,34949'),
(NULL, 'Meag Bytes', '1875 Sunfield Park,New Haven,Connecticut,06538'),
(NULL, 'Deez Bytes', '40327 Marcy Crossing,Olympia,Washington,98516'),
(NULL, 'Terra Bytes', '1447 Lawn Court,Winston Salem,North Carolina,27150');

INSERT INTO APP_USER(User_ID, Name, Username, Password, Phone, Address, Role_ID) VALUES
(NULL, 'Peter Sullivan', 'psullivan', 'LhhkyEZigIT', '4066410484', '77 Dahle Road,Bozeman,Montana,59771', 0),
(NULL, 'Heather White', 'hwhite', 'qLAs64o8', '4042276915', '82 Hermina Avenue,Atlanta,Georgia,30392', 0),
(NULL, 'Carl Fox','cfox', 'GAufXlzdV', '8082543085', '03916 Corben Street,Honolulu,Hawaii,96840', 1),
(NULL, 'Jerry Kennedy', 'jkennedy', '9zEgSAT', '9176884300', '96 Talisman Drive,New York City,New York,10155', 0),
(NULL, 'Brandon Harper','bharper', 'FnoSr6JLFP', '7143314786', '2248 Morrow Street,Garden Grove,California,92645', 1),
(NULL, 'Kathleen Johnson','kjohnson', 'zVMmT2diq', '9176511325', '762 Almo Alley,New York City,New York,10039', 2),
(NULL, 'Chris Cruz','ccruz', '9uWEQEzP', '3029577899', '446 Loftsgordon Terrace,Wilmington,Delaware,19886', 0);

INSERT INTO CUSTOMER VALUES
(1, 2),
(2, 4),
(4, 7),
(7, 5);

INSERT INTO EMPLOYEE(User_ID, Schedule, Salary, work_store_id) VALUES
(3, '<?xml version="1.0" encoding="UTF-8"?> <schedule> <shift> <date>Sun March 5, 2017</date> <start>7:00</start> <end>15:00</end> <job>Service Clerk</job> <hours>9</hours> </shift> <shift> <date>Mon March 6, 2017</date> <start>14:00</start> <end>20:00</end> <job>Service Clerk</job> <hours>6</hours> </shift> <shift> <date>Sun March 7, 2017</date> <start>7:00</start> <end>11:00</end> <job>Cashier</job> <hours>4</hours> </shift> <shift> <date>Sun March 5, 2017</date> <start>12:00</start> <end>20:00</end> <job>Service Clerk</job> <hours>8</hours> </shift> </schedule>', 7.25, 1),
(5, '<?xml version="1.0" encoding="UTF-8"?> <schedule> <shift> <date>Sun March 5, 2017</date> <start>7:00</start> <end>15:00</end> <job>Service Clerk</job> <hours>9</hours> </shift> <shift> <date>Mon March 6, 2017</date> <start>14:00</start> <end>20:00</end> <job>Service Clerk</job> <hours>6</hours> </shift> <shift> <date>Sun March 7, 2017</date> <start>7:00</start> <end>11:00</end> <job>Cashier</job> <hours>4</hours> </shift> <shift> <date>Sun March 5, 2017</date> <start>12:00</start> <end>20:00</end> <job>Service Clerk</job> <hours>8</hours> </shift> </schedule>', 8.75, 4),
(6, '<?xml version="1.0" encoding="UTF-8"?> <schedule> <shift> <date>Sun March 5, 2017</date> <start>7:00</start> <end>15:00</end> <job>Manager</job> <hours>9</hours> </shift> <shift> <date>Mon March 6, 2017</date> <start>14:00</start> <end>20:00</end> <job>Manager</job> <hours>6</hours> </shift> <shift> <date>Sun March 7, 2017</date> <start>7:00</start> <end>11:00</end> <job>Manager</job> <hours>4</hours> </shift> <shift> <date>Sun March 5, 2017</date> <start>12:00</start> <end>20:00</end> <job>Manager</job> <hours>8</hours> </shift> </schedule>', 17.75, 2);

INSERT INTO PRODUCT(Product_ID, Name, Price) VALUES
(00036632035769, 'Dannon Activia Peach And Strawberry Lowfat Yogurt - 12 Ct', 7.29),
(00077782008135, 'Johnsonville Italian Sausage Hot', 6.99),
(00038000596582, 'Kellogg''s Cereal Frosted Flakes', 2.33),
(00021000028238, 'Kraft Macaroni & Cheese Dinner Spongebob Shapes', 2.50),
(00028400164948, 'Lay''s Wavy Potato Chips', 1.00),
(00021131501013, 'Marie Callender''s Chicken Pot Pie', 2.00),
(00072945601338, 'Sara Lee Bread White', 4.45);

INSERT INTO INVENTORY(Product_ID, Store_ID, qty, Aisle) VALUES
(00036632035769, 2, 19, 10),
(00077782008135, 5, 23, 10),
(00038000596582, 7, 6, 3),
(00021000028238, 1, 7, 3),
(00028400164948, 4, 27, 2),
(00021131501013, 7, 35, 11),
(00072945601338, 3, 3, 1);

INSERT INTO GROCERY_ORDER(Order_ID, Order_Time, Store_ID, User_ID) VALUES
(NULL, '2016-04-26 16:48:18', 4, 1),
(NULL, '2016-04-27 05:47:29', 3, 2),
(NULL, '2016-11-29 18:58:53', 3, 4),
(NULL, '2016-04-27 00:26:37', 6, 7),
(NULL, '2016-11-29 04:23:17', 7, 2),
(NULL, '2016-10-10 07:56:09', 1, 2),
(NULL, '2016-12-17 17:08:09', 5, 1);

INSERT INTO ORDER_PRODUCT(Order_ID, Product_ID, qty) VALUES
(1, 00036632035769, 2),
(2, 00077782008135, 2),
(3, 00038000596582, 1),
(4, 00021000028238, 1),
(5, 00028400164948, 1),
(6, 00021131501013, 4),
(7, 00072945601338, 1);


INSERT INTO SHOPPING_CART(Customer_ID, Product_ID, qty) VALUES
(1, 00021131501013, 1),
(4, 00028400164948, 2),
(4, 00072945601338, 1),
(7, 00038000596582, 1),
(7, 00028400164948, 1),
(1, 00072945601338, 3);