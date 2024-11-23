# Personal Account API

This project is a RESTful API for managing user profiles. It provides endpoints for creating, retrieving, updating profile information, and uploading profile avatars.

## Features

- Create and manage user profiles
- Upload and update profile avatars
- Validate profile data
- Secure data storage

## Technologies Used

- Kotlin 1.9.25
- Spring Boot 3.3.5
- MySQL 8
- Maven
- Java 21

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 21
- Maven 3.6+
- MySQL 8

## Setup and Installation

1. Clone the repository:
   ```
   git clone https://github.com/alenushka0585/personal_account
   cd personal_account
   ```

2. Configure the database:
   - Create a MySQL database named `profiledb`
   - Update `src/main/resources/application.properties` with your database credentials

3. Create a `credentials.properties` file in `src/main/resources/` with the following content:
   ```
   db.username=your_mysql_username
   db.password=your_mysql_password
   ```

4. Build the project:
   ```
   mvn clean install
   ```

5. Run the application:
   ```
   mvn spring-boot:run
   ```

The server will start on `http://localhost:8080`.

## API Endpoints

- `POST /profile`: Create profile data
- `GET /profile`: Retrieve profile data
- `PUT /profile`: Update profile data
- `POST /upload-avatar`: Upload profile avatar

For detailed API documentation, visit `http://localhost:8080/swagger-ui.html` after starting the server.

## Data Validation

The API performs server-side validation for all fields:

- Name and Surname: 2-50 characters, letters and spaces only
- Job Title: Up to 100 characters, letters, numbers, and spaces
- Phone: Format +XXXXXXXXXXX, 10-15 digits
- Address: Up to 200 characters
- Interests: Up to 10 tags, each max 30 characters
- Links: Valid URL, up to 200 characters
- Avatar: JPG, JPEG, or PNG, max 5MB

## Contributing

Contributions to this project are welcome. Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the [MIT License](LICENSE).