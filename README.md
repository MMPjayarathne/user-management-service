
# User Management Service
Auther: Masith Pramuitha
This is a simple User Management application built using **React** for the frontend and **Spring Boot** for the backend. The application allows users to:

- Admin login.
- View a list of users.
- Add new users.
- Update users.
- Delete users.

The frontend uses **Redux Toolkit** to manage the application state and interacts with the Spring Boot backend for fetching, adding, updating and deleting user data.

## Features

- **React Frontend**: Displays users, adds, updates and deletes users.
- **Spring Boot Backend**: Provides APIs for user management (CRUD operations) and authentication.
- **Redux Toolkit**: Handles state management for user data in the frontend.

## Prerequisites

Before you begin, ensure you have the following installed on your local machine:

- **Node.js** (>=14.x)
- **npm** (package managers for JavaScript)
- **Java 23** (for running Spring Boot)
- **Maven** (for building Spring Boot)
- **Postman or any API client** (for testing API endpoints)

## Frontend Setup (React)

### 1. Clone the repository

```bash
git clone <repository-url>
cd frontend
```

### 2. Install dependencies

Run the following command to install the required npm packages:

```bash
npm install
```

### 3. Run the React application

To start the React development server, run:

```bash
npm start
```

The application should now be running on [http://localhost:3000](http://localhost:3000).

## Backend Setup (Spring Boot)

### 1. Clone the repository

If not already done, clone the backend part of the repository:

```bash
git clone <repository-url>
cd backend
```

### 2. Configure application properties

Update the `application.properties` file in the `src/main/resources` directory with your database and other necessary configurations. For example:

```properties
# Example database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build the Spring Boot application

Run the following command to build the Spring Boot application:

```bash
mvn clean install
```

### 4. Run the Spring Boot application

After building the project, run the Spring Boot application with:

```bash
mvn spring-boot:run
```

The backend API should now be running on [http://localhost:8080](http://localhost:8080).

### 5. Test the API

You can test the following API endpoints using Postman or any other API client:

- `GET /api/users`: Fetch all users.
- `POST /api/users`: Add a new user (Request body: `{ "firstName": "New first name","lastName": "New last name" }`).
- `DELETE /api/users/{id}`: Delete a user by ID.
- `PUT /api/users/{id}`: Update a user by ID and Request body: `{ "firstName": "New first name","lastName": "New last name" }`

---

## Application Workflow

1. **Frontend (React)**: The React frontend is responsible for displaying the list of users, adding new users, update users, and deleting existing ones with the admin auth functionality. It communicates with the backend API to perform these operations.
2. **Backend (Spring Boot)**: The Spring Boot backend provides the necessary REST APIs to manage users. It stores user data in a database and handles requests from the frontend.

---



## Thank You!
- Masith Pramuditha
