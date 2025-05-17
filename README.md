

# CodePioneers Career Portal – Spring Backend (API)

**AI-Powered Recruitment Platform**

CodePioneers' Career Portal is a demo recruitment platform. Applicants submit their CVs, and recruiters compare candidates side by side, helped by an AI-powered FastAPI micro-service that scores every applicant in real time.

The micro-service uses a semantic BERT model plus custom matching algorithms to quantify how closely each candidate fits the job.

Fast API Microservice Github Repo: [job-eval-backend-fastapi ](https://github.com/rigelHadushiDev/job-eval-backend-fastapi)

---

## Project Overview

The backend facilitates:

* **Applicant Profile Management**: Candidates can input their education, work experience, projects, skills, and languages.
* **AI-Based Evaluation**: Utilizes a FastAPI microservice which uses all-MiniLM-L6-v2 NLP model form SentenceTransformer to assess the similarity between applicant profiles and job requirements.
* **Real-Time Notifications**: Sends email alerts to applicants upon changes in application status.
* **Role-Based Access Control**: Defines three role with specific permissions : User, Recruiter, and Admin.
* **Secure Authentication**: Implements JWT Bearer Token for authentication and authorization.


## Features by Role

* **User Role**:

    * Build and update profile sections (education, experience, skills, languages)
    * Apply for job postings.
    * Receive email notifications on application status changes

* **Recruiter Role**:

    * Create, edit, and close job postings
    * Review AI-generated candidate scores and details
    * Approve or reject applications

* **Admin Role**:

    * All recruiter capabilities
    * Manage recruiter and admin user accounts

---

## Technologies Used

* **Backend Framework**: Spring Boot 3.4.3
* **Programming Language**: Java 17
* **Database**: PostgreSQL (Dockerized)
* **Authentication**: JWT Bearer Token
* **AI Evaluation**: FastAPI microservice
* **Email Service**: Spring Boot Mail
* **API Documentation**: Springdoc OpenAPI
* **Build Tool**: Maven

---

## Installation & Setup

### Prerequisites

* Java 17
* Maven
* Docker & Docker Compose

### Steps

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/rigelHadushiDev/job-eval-backend-spring.git
   cd job-eval-backend-spring
   ```

2. **Start PostgreSQL with Docker**:

   ```bash
   docker-compose up -d
   ```

3. **Configure Application Properties**:

   Update `src/main/resources/application.properties` with your database and email configurations.

4. **Build and Run the Application**:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

---

## Email Configuration

Ensure the following properties are set in `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

##  Authentication & Authorization

* **Register**: `/auth/register`
* **Login**: `/auth/login`
* **Refresh token**: `/auth/refresh-token`

Include the JWT token in the `Authorization` header for secured endpoints:

```http
Authorization: Bearer <token>
```

---

## API Documentation

Access the Swagger UI for API exploration at:

```
http://localhost:8080/swagger-ui.html
```

---

## Running Tests

Unit and integration tests will be added in the next update.

---

## Project Structure

```
src/
├── main/
│   ├── java/com/codepionners/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── security/
│   │   ├── service/
│   │   └── JobEvalApplication.java
│   └── resources/
│       ├── application.properties
│       └── templates/
└── test/
```


---


## Related Repositories

- **FastAPI Microservice (AI Evaluation Engine)**:  [job-eval-backend-fastapi](https://github.com/rigelHadushiDev/job-eval-backend-fastapi)

- **React Frontend (User Interface)**:  [job-eval-frontend-react](https://github.com/rigelHadushiDev/job-eval-frontend-react)



---

## Author

Developed by [@rigelHadushiDev](https://github.com/rigelHadushiDev)

