DROP DATABASE IF EXISTS veganPizzeria;
CREATE DATABASE veganPizzeria;

USE veganPizzeria;

CREATE TABLE statusOfOrder(
orderStatus VARCHAR(10) PRIMARY KEY
);

CREATE TABLE typeOfUser(
userType VARCHAR(10) PRIMARY KEY
);

CREATE TABLE login(
emailAddress VARCHAR(50) PRIMARY KEY,
password VARCHAR(50) NOT NULL,
userType VARCHAR(10) NOT NULL,
CONSTRAINT FOREIGN KEY fk_TypeOfUser_Login
	(userType) REFERENCES typeOfUser (userType)
);

CREATE TABLE customers(
customerId INT PRIMARY KEY AUTO_INCREMENT,
customerName VARCHAR(50) NOT NULL,
customerEmailAddress VARCHAR(50) NOT NULL,
customerFirstLineAddress VARCHAR(100) NOT NULL,
customerPostCode VARCHAR(10) NOT NULL,
customerPhone VARCHAR(50) NOT NULL,
CONSTRAINT FOREIGN KEY fk_LoginCustomers
	(customerEmailAddress) REFERENCES login (emailAddress)
);

CREATE TABLE orders(
orderId INT PRIMARY KEY AUTO_INCREMENT,
customerId INT NOT NULL,
orderPlacedTime TIME NOT NULL,
orderDate DATE NOT NULL,
total DECIMAL(6,2) NOT NULL,
orderStatus VARCHAR(10) NOT NULL,
CONSTRAINT FOREIGN KEY fk_CustomersOrders
	(customerId) REFERENCES customers (customerId),
CONSTRAINT FOREIGN KEY fk_StatusOfOrderOrders
	(orderStatus) REFERENCES statusOfOrder (orderStatus)
);

CREATE TABLE pizzas(
pizzaName VARCHAR(50) PRIMARY KEY,
pizzaPrice DECIMAL(4,2),
pizzaDesc MEDIUMTEXT,
pizzaImgURL TINYTEXT
);

CREATE TABLE orderLines(
lineOrderId INT PRIMARY KEY AUTO_INCREMENT,
orderId INT,
pizzaName VARCHAR(50),
quantity INT,
lineCost DECIMAL(4,2),
CONSTRAINT FOREIGN KEY fk_OrdersOrderLines
	(orderId) REFERENCES orders (orderId),
CONSTRAINT FOREIGN KEY fk_PizzasOrderLines
	(pizzaName) REFERENCES pizzas (pizzaName)
);

CREATE TABLE employees(
employeeId INT PRIMARY KEY AUTO_INCREMENT,
firstName  VARCHAR(20) NOT NULL,
secondName  VARCHAR(20) NOT NULL,
emailAddress  VARCHAR(50) NOT NULL,
CONSTRAINT FOREIGN KEY fk_LoginEmployees
	(emailAddress) REFERENCES login (emailAddress)
);