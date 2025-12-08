Expense Tracker – Spring Boot Project

A simple REST API-based Expense Tracker built using Spring Boot, MySQL, JPA, and Hibernate.
This project demonstrates a backend application structured using Controller → Service → Repository → Database layers.
It supports full CRUD operations (Create, Read, Update, Delete) on expenses.
The goal is to help anyone understand how a real backend service is structured using Controller → Service → Repository → Database, while also learning API creation, database integration, and CRUD operations.
The Expense Tracker allows users to store, view, update, and delete daily expenses through clean REST API endpoints.

Features:

1.Add a new expense
2.Get all expenses
3.Get an expense by ID
4.Update an expense
5.Delete an expense
6.Auto-creates tables using Hibernate
7.Easy testing using Postman

Tech Stack:

1.Java
2.Spring Boot (3.5.x)
3.MySQL
4.Hibernate / Spring Data JPA
5.Maven
6.Postman

Project Structure:

expense-tracker/
├── src/main/java/com/example/expensetracker
│ ├── controller/ExpenseController.java
│ ├── service/ExpenseService.java
│ ├── repository/ExpenseRepository.java
│ ├── model/Expense.java
│ └── ExpenseTrackerApplication.java
│
├── src/main/resources/application.properties
└── pom.xml
