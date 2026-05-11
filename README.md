# Telemetry Analyzer 🚀

---

## 🛠 Tech Stack
- **Backend:** Java 17, Spring Boot (Web, Data JPA, Security, Actuator)
- **Database:** PostgreSQL (optimized queries, indexes)
- **Security:** Spring Security (RBAC)
- **API:** REST, OpenAPI/Swagger
- **Infrastructure:** Docker, Docker Compose, CI/CD pipelines
- **Monitoring:** Micrometer, Prometheus, Grafana
- **Testing:** JUnit, Mockito
- **Utilities:** Lombok, Logging, Exception Handling
- **Concurrency:** Multi-threading, async processing


---
Event-driven microservice built with **Java 17 + Spring Boot** for processing telemetry data in real time.  
The system integrates **Apache Kafka**, **gRPC** and **PostgreSQL**, and is fully tested with **JUnit5, Mockito and Testcontainers**.

---

## ✨ Features
- ✅ Kafka Producer & Consumer with manual offset commit  
- ✅ Custom deserializers for domain events  
- ✅ gRPC client-server communication  
- ✅ PostgreSQL persistence (Spring Data JPA/Hibernate)  
- ✅ Unit & integration tests (JUnit5, Mockito, Testcontainers)  
- ✅ Docker support for local and production environments  

---

## 🛠️ Tech Stack
- Java 17, Spring Boot, Spring Data JPA  
- Apache Kafka  
- gRPC  
- PostgreSQL  
- Docker  
- JUnit5, Mockito, Testcontainers  

---

## 🚀 Getting Started

```bash
# clone repo
git clone https://github.com/oxSwight/telemetry-analyzer.git
cd telemetry-analyzer

# run with Docker Compose
docker-compose up --build
