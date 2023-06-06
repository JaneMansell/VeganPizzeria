# VeganPizzeria

Collaborators: [Jane Mansell](https://github.com/JaneMansell) (JM) & [Victoria Daramy-Williams](https://github.com/victoriadw) (VDW)

## Contents
- Introduction
- Installation
  - Technology Stack
  - Instructions
- Usage
  - General
  - Customer usage
  - Employee usage
- Future Work

## Introduction
This project is a pizza ordering programme for a vegan pizzeria that allows customers to place pizza orders, track the status of their orders, and additionally provide employees with the ability to manage order details and update order statuses. 
The programme also incorporates an API to showcase cute animal images, enhancing the overall user experience. 

Objectives:
1. Provide a user-friendly interface for customers to browse pizza options, customise their orders, and place orders seamlessly.
2. Implement a tracking system that allows customers to monitor the status of their orders in real-time, providing updates on order preparation, baking, and delivery.
3. Develop an employee portal that enables authorised staff members to access order details and manage order statuses.
4. Integrate an API to fetch and display adorable animal images, creating an engage and enjoyable experience for users.

This project uses the following technologies: Java 17, Maven, Jar, Spring Boot (v 3.1.0), Thymeleaf (v 3.0.4), mySQL, JDBC Template, HTML, CSS, and JavaScript.

Both collaborators contributed equally across all aspects of the project, with the following responsibilities:

| Lead | 	Task                                                          |
|------|----------------------------------------------------------------|
| VDW	 | GUI Styling                                                    |                                              
| VDW	 | Placing orders                                                 |                                              
| VDW	 | New user signup                                                |
| JM	  | Customer check status                                          |
| JM	  | Cook check status and get list of pizzas to cook               |
| JM	  | Cook update status for pickup                                  |
| JM	  | Login and access to relevant screens for employee and customer |


## Installation
### Technology Stack
For ease of use, we recommend using an IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [NetBeans](https://netbeans.apache.org/) to run the programme. 
You will also need [MySQL](https://dev.mysql.com/downloads/mysql/), and we recommend using MySQL Workbench to view schemas and data more easily.

### Instructions
You may install the programme by either:
1. Downloading the .zip file and extracting it; or
2. Cloning the repository to their own device.

Once downloaded, open the programme in your chosen IDE.

Before running the programme, the database must be accessible and set up correctly. 
Locate the `application.properties` file (path: `VeganPizzeria > src > main > resources > application.properties`).
Update the properties as necessary to match the database specifications (e.g. update `pizza` in `spring.datasource.password=pizza` with the correct database password). 
Now open the `schema.sql` and `data.sql` files in `resources` in MySQL Workbench and execute them. The databases should now be accessible to the programme. 

Repeat these steps for the `application.properties` file and databases in the test directory (path: `VeganPizzeria > src > test > resources`).

## Usage
### General
The programme should now be ready to run. Locate the `VeganPizzeriaApplication` class (path `VeganPizzeria > src > main > java > com.Plantizza.VeganPizzeria`) and build and run the programme.

Now navigate to http://localhost:8080/ on your browser. You should now see the homepage:

![img.png](img.png)

Click on "View Menu" to access the menu. You should see a page that looks like this:

![img_1.png](img_1.png)

In order to access most of the programme, the user must be signed in. Currently, there are two types of user: customer and employee, so we will go through the different type of usage for both in the following sections.

### Customer usage


### Employee usage



# Future Work

Users will note that there is no payment functionality. In the future, we plan to integrate the PayPal API to support this.

Also differentiate employees between admin user, cook, delivery person.
