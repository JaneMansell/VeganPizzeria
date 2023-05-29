USE veganPizzeria;

INSERT INTO pizzas VALUES
('Plain', 6.99),
('BBQ Jackfruit', 8.99),
('Mushroom', 8.99),
('Roasted Veg', 8.99),
('Olive and Sundried Tomato', 8.99);

INSERT INTO statusOfOrder VALUES
('Basket'),
('Ordered'),
('Cooking'),
('Pick up'),
('Picked up'),
('Delivered');

INSERT INTO typeOfUser VALUES
('Customer'),
('Employee'),
('Admin');

INSERT INTO login VALUES
('judd@gmail.com', 'password', 'Customer'),
('neil@btinternet.com','password','Customer'),
('luca@gmail.com','password', 'Customer'),
('mark@plusnet.com','password', 'Customer'),
('jimmy@gmail.com','password', 'Customer'),
('dom@gmail.com','password', 'Customer'),
('yan@btinternet.com','password', 'Customer'),
('marco@gmail.com', 'password', 'Customer'),
('leo@plantizza.com', 'password', 'Employee'),
('micky@plantizza.com', 'password', 'Employee'),
('donna@vplantizza.com', 'password', 'Employee'),
('raph@plantizza.com', 'password', 'Employee'),
('admin', 'admin', 'Admin');

INSERT INTO employees VALUES
(1, 'Leo', 'DaVinci', 'leo@plantizza.com'),
(2, 'Micky', 'Angelo', 'micky@plantizza.com'),
(3, 'Donna', 'Tello', 'donna@vplantizza.com'),
(4, 'Raph', 'Ale', 'raph@plantizza.com');

INSERT INTO customers VALUES
(1,'Judd Trump', 'judd@gmail.com', '1 Crucible Street', 'PO32 5JW', '07777123456'),
(2,'Neil Robertson', 'neil@btinternet.com','3 Crucible Street', 'PO32 5JS', '07777654321'),
(3,'Luca Brecel', 'luca@gmail.com','5 Crucible Street', 'PO32 5JW', '07777111111'),
(4,'Mark Selby', 'mark@plusnet.com','1 Ally Pally Street', 'PO32 6JL', '07777122222'),
(5,'Jimmy White', 'jimmy@gmail.com','3 Ally Pally Street', 'PO32 6JW', '07777123333'),
(6,'Dominic Dale', 'dom@gmail.com','5 Ally Pally Street', 'PO32 6JK', '07777123444'),
(7,'Yan Bingtao', 'yan@btinternet.com','7 Ally Pally Street', 'PO32 6JW', '07777123455'),
(8,'Marco Fu', 'marco@gmail.com','9 Ally Pally Street', 'PO32 6JW', '07777222222');

INSERT INTO orders VALUES
(1, 2, '17:30:05', '2023-05-26', 6.99, 'Delivered'),
(2, 3, '17:40:05', '2023-05-26', 15.98, 'Picked up'),
(3, 5, '17:50:05', '2023-05-26', 24.97, 'Pick up'),
(4, 7, '18:00:05', '2023-05-26', 13.98, 'Cooking'),
(5, 8, '18:10:05', '2023-05-26', 17.98, 'Ordered');

INSERT INTO orderLines VALUES
(1,1,'Plain',1,6.99),
(2,2,'Plain',1,6.99),
(3,2,'Mushroom',1,8.99),
(4,3,'Plain',1,6.99),
(5,3,'Mushroom',1,8.99),
(6,3,'Roasted Veg',1,8.99),
(7,4,'Plain',2,13.98),
(8,5,'BBQ Jackfruit',1,8.99),
(9,5,'Olive and Sundried Tomato',1,8.99);

