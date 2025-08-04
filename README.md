# CodePioneers Career Portal – Spring Boot Backend

**AI-Powered Recruitment Platform**  
Applicants build their profiles by entering details about their education, work experience, projects, skills, and languages. After applying, an AI-powered FastAPI microservice acts as the initial screening layer, scoring each applicant in real time using advanced data science algorithms and a Sentence Transformer model to evaluate their suitability for the job, helping recruiters focus on the most promising candidates first.

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

CodePioneers' Career Portal is a demo recruitment platform where applicants submit detailed profile information. An AI-powered FastAPI microservice acts as an initial filter, identifying the most promising candidates based on their suitability for each job. Recruiters can then review the top-matched applicants, decide who to shortlist, and invite selected candidates for interviews. They can also create, update, and close job postings directly from the platform.

Fast API Microservice Github Repo: [job-eval-backend-fastapi ](https://github.com/rigelHadushiDev/job-eval-backend-fastapi)

---

## Features

- **Applicant Profile Management**  
  Candidates can build and update profile sections (education, experience, skills, languages).

- **Job Posting & Application Management**  
  Enables recruiters to create, edit, and close job listings; filter and review incoming applications with AI scores; update statuses; and notify candidates.

- **AI-Based Evaluation**  
  Offloads semantic matching to a FastAPI microservice that leverages advanced algorithms and the `all-MiniLM-L6-v2` model from Sentence Transformers to compute a general suitability score.

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

### Configuration

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
### Running the Application

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
### API Documentation

Once running, explore all endpoints via Swagger UI at:

   ```bash
   http://localhost:8080/swagger-ui.html
   ```

### Running Tests

Unit and integration tests will be added in the next update.

### Project Structure

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


### Related Repositories

- **FastAPI Microservice (AI Scoring)**:  [job-eval-backend-fastapi](https://github.com/rigelHadushiDev/job-eval-backend-fastapi)

- **React Frontend (User Interface)**:  [job-eval-frontend-react](https://github.com/rigelHadushiDev/job-eval-frontend-react)

---

### Author

Developed by [@rigelHadushiDev](https://github.com/rigelHadushiDev)
