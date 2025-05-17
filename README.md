# CodePioneers Career Portal – Spring Boot Backend (API)

**AI-Powered Recruitment Platform**  
Applicants submit their CVs, and recruiters compare candidates side by side, aided by an AI-powered FastAPI microservice that scores every applicant in real time using a semantic BERT model and custom matching algorithms.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Features by Role](#features-by-role)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
    - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Author](#author)

---

## Project Overview

CodePioneers' Career Portal is a demo recruitment platform. Applicants submit their CVs, and recruiters compare candidates side by side, helped by an AI-powered FastAPI micro-service that scores every applicant in real time.

The micro-service uses a semantic BERT model plus custom matching algorithms to generate a score on how much each candidate fits the job.

Fast API Microservice Github Repo: [job-eval-backend-fastapi ](https://github.com/rigelHadushiDev/job-eval-backend-fastapi)

---

## Features

- **Applicant Profile Management**  
  Candidates can build and update profile sections (education, experience, skills, languages).

- **Job Posting & Application Management**  
  Enables recruiters to create, edit, and close job listings; filter and review incoming applications with AI scores; update statuses; and notify candidates.

- **AI-Based Evaluation**  
  Offloads semantic matching to a FastAPI microservice that leverages advanced algorithms and the `all-MiniLM-L6-v2` model to compute a normalized “semantic fit” score.

- **Real-Time Notifications**  
  Sends email alerts to applicants upon changes in their application status.

- **Role-Based Access Control**  
  Defines three roles: User, Recruiter, Admin with scoped permissions.

- **Secure Authentication**  
  Implements JWT Bearer tokens for all protected endpoints.

---

## Features by Role

### User

- Build and update profile sections (education, experience, skills, languages)
- Apply for job postings
- Receive email notifications on application status changes

### Recruiter

- Create, edit, and close job postings
- Review AI-generated candidate scores and profiles
- Approve or reject applications

### Admin

- All Recruiter capabilities
- Manage Recruiter and Admin user accounts

---

## Technologies

- **Backend Framework**: Spring Boot 3.4.3
- **Language**: Java 17
- **Database**: PostgreSQL (Dockerized)
- **Authentication**: JWT Bearer Token
- **AI Evaluation**: FastAPI microservice (SentenceTransformer)
- **Email Service**: Spring Boot Mail
- **API Documentation**: Springdoc OpenAPI
- **Build Tool**: Maven

---

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker & Docker Compose

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/rigelHadushiDev/job-eval-backend-spring.git
   cd job-eval-backend-spring
   ```
2. **Start PostgreSQL in Docker**

   ```bash 
   docker-compose up -d
   ```
3. **Configuration**

Update `src/main/resources/application.properties` with your database credentials and email settings:

   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:your_port/your_db
   spring.datasource.username=your_user
   spring.datasource.password=your_password

   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```
4. Running the Application

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
5. API Documentation

Once running, explore all endpoints via Swagger UI at:

   ```bash
   http://localhost:8080/swagger-ui.html
   ```

6. Running Tests

Unit and integration tests will be added in the next update.

7. Project Structure

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


8. Related Repositories

- **FastAPI Microservice (AI Scoring)**:  [job-eval-backend-fastapi](https://github.com/rigelHadushiDev/job-eval-backend-fastapi)

- **React Frontend (User Interface)**:  [job-eval-frontend-react](https://github.com/rigelHadushiDev/job-eval-frontend-react) (work in progress)



---

9. Author

Developed by [@rigelHadushiDev](https://github.com/rigelHadushiDev)

