# Pizza Store Application

The Pizza Store Application is a web-based system that allows customers to browse and order pizzas online. It provides functionality for customer registration, login, pizza catalog management, and shopping cart management.

## Description

This project implements the online web store's backend that can be integrated with different front-end technologies. The backend system provides a user signup, login, product catalog and cart functionalities, allowing a prospective purchasing consumer to seamlessly create and place "Pizza" orders. While doing so, the backend system also accommodates an exponential increase of workload by using synchronisation.

### Dependencies

Before installing the Pizza Store Application, ensure that you have the following prerequisites:

* Java Development Kit (JDK) 8 or later
* Apache Maven or Gradle (build tools)
* Apache Tomcat or any other supported servlet container

### Technologies Used

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Apache Tomcat

### Executing The Project

* Clone the repository to your local machine.
* Import the project into your preferred Java IDE.
* Add/Adjust relevant dependencies in the pom.xml file.
* Build the project using the build tools of your IDE or using command-line tools.
* Run the PizzaControllerApplication class to start the application.

### API Documentation
The Pizza Store Application provides a RESTful API for various operations. Here are some of the available endpoints:

* POST /api/pizzas/register: Register a new customer.
* POST /api/pizzas/login: Login with customer credentials.
* GET /api/pizzas: Get all available pizzas.
* GET /api/pizzas?category={category}: Get pizzas by category.
* GET /api/pizzas?keyword={keyword}: Search pizzas by keyword.
* POST /api/pizzas/add-to-cart: Add a pizza to the shopping cart.
* POST /api/pizzas/remove-from-cart: Remove a pizza from the shopping cart.
* POST /api/pizzas/update-cart: Update the quantity of a pizza in the shopping cart.
* POST /api/pizzas/logout: Logout the customer.
* POST /api/pizzas/checkout: Checkout the shopping cart.

### The Architectural Patterns
This application makes use of several design patterns:

* 1. MVC (Model-View-Controller): The overall architecture of the project follows the MVC pattern. The PizzaController class is the controller, which receives requests from the frontend, interacts with the services, and returns responses. The Customer, Pizza, and Cart classes can be considered as models that represent the data entities. The frontend applications can be seen as the views that interact with the backend through the controller.
* 2. Singleton Pattern: The PizzaService, CartService, and CustomerService classes are designed as singletons by using the @Service annotation in the Spring framework. This ensures that there is only one instance of these classes throughout the application.

### Testing 
The Pizza Store Application includes a set of JUnit tests in the PizzaControllerTest class. These tests cover various functionalities of the application, including customer registration, login, pizza catalog retrieval, cart management, and more. Run these tests to ensure the correctness of the application's functionality.

### Notes and Remarks
Since this is my first hands-on experience using Java Spring, I followed tutorials and did research on how to efficiently use Spring. I learned that Java Spring is a framework that provides built-in concurrency support and handles thread-safety for many aspects of application development. Spring uses various mechanisms, such as dependency injection and bean scopes, to ensure that components are managed and accessed safely in a multi-threaded environment.
These mechanism can be used to handle exponential increase of workload. However, there may be scenarios where explicit synchronization is required. You might need to use synchronization to ensure thread-safety and prevent race conditions. That is why I choose to integrage synchronisation into this project.

Also, I would like to state that although I am confident in my solution, I came across a configuration problem with my Spring implementation. I have searched and tried to solve the issue next to learning how to use the Spring framework but this is the final version I could provide before the given deadline.
