#### Components:

1. **Flutter UI**:
   - Cross-platform mobile app interface developed using Flutter.
   - Allows users to log in, manage permissions for financial products, and check account balances.

2. **Spring Boot RESTful Services**:
   - Backend service layer developed in Java using Spring Boot.
   - Provides RESTful APIs for user authentication, managing permissions, and interacting with the PostgreSQL database.

3. **PostgreSQL Database (Cloud SQL)**:
   - Managed relational database service provided by Google Cloud SQL.
   - Stores user profiles, permissions, financial product details, and transaction records.

4. **Notification Service (Cloud Function)**:
   - Serverless function deployed using Google Cloud Functions.
   - Generates OTPs (One-Time Passwords) and sends email notifications when a user grants permissions to another person.

5. **Cloud Run**:
   - Fully managed serverless container service provided by Google Cloud.
   - Hosts the Spring Boot applications, automatically scaling based on traffic.

6. **Cloud Build**:
   - Continuous integration and delivery service provided by Google Cloud.
   - Automatically builds and deploys changes from GitHub repository to Cloud Run.

#### Interactions:

- **Flutter UI <-> Spring Boot RESTful Services**:
  - Flutter UI sends HTTP requests to Spring Boot APIs hosted on Cloud Run.
  - Spring Boot services handle user authentication, permission management, and data retrieval from Cloud SQL.
  - Responses (JSON format) are returned to Flutter UI for display.

- **Spring Boot RESTful Services <-> PostgreSQL Database (Cloud SQL)**:
  - Spring Boot services interact with the Cloud SQL PostgreSQL database using JDBC.
  - Executes queries and updates to manage user profiles, permissions, financial products, and transactions.

- **Spring Boot RESTful Services <-> Notification Service (Cloud Function)**:
  - Spring Boot services trigger the Notification Service (Cloud Function) to generate OTPs and send email notifications when permissions are granted.

#### Diagram:

```
  [Flutter UI]
      |
 HTTP Requests/Responses
      v
 [Spring Boot RESTful Services]
      |
 Database Queries/Updates
      |
 [Notification Service (Cloud Function)]
      |
 Email Notifications/OTP Generation
      v
 [Cloud SQL (PostgreSQL)]
```

#### Deployment on GCP:

- **Cloud Run**:
  - Hosts the Spring Boot applications as Docker containers.
  - Scales automatically based on incoming traffic.
  - Managed by Google Cloud, ensuring high availability and scalability.

- **Cloud SQL (PostgreSQL)**:
  - Managed service that handles database administration tasks.
  - Offers automated backups, replication, and maintenance.
  - Ensures data integrity and reliability for the banking application.

- **Cloud Function (Notification Service)**:
  - Serverless function for OTP generation and email notifications.
  - Triggered by events from Spring Boot services (via HTTP request or Pub/Sub).
  - Sends OTPs and email notifications using integrated GCP services like Gmail API or SMTP relay.

- **Cloud Build**:
  - Monitors changes in the GitHub repository.
  - Triggers builds and deployments to Cloud Run when changes are detected.
  - Facilitates continuous integration and delivery (CI/CD) for the banking app.

### Explanation:

- **Notification Service (Cloud Function)**: Deployed using Google Cloud Functions, this serverless component generates OTPs and sends email notifications whenever a user grants permissions to another person through the banking app. It enhances security and provides communication capabilities without managing infrastructure.

Including the Notification Service in the architecture enhances the banking app by providing real-time notifications for important actions, such as granting permissions. This ensures users are informed promptly and securely, enhancing overall user experience and security of the application.
