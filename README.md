# SkillSwap — Java Spring Boot Skill Exchange Platform

SkillSwap helps people exchange skills: teach what you know, learn what you want, and send exchange requests to other members.

## Features
- Account registration and login (Spring Security + BCrypt password hashing)
- Add skills you can teach and skills you want to learn
- Browse and search member skill listings
- Send exchange requests and manage incoming/outgoing requests
- Accept or decline incoming requests
- Responsive Thymeleaf + Bootstrap interface
- H2 database for easy local development; data persists to `./data/skillswap`
- CSRF protection enabled by Spring Security

## Requirements
- Java 21+
- Maven 3.9+ (or use an IDE with Maven support)

## Run locally
```bash
mvn spring-boot:run
```
Open http://localhost:8080. Register an account to start. The H2 database is created automatically.

Build a runnable jar:
```bash
mvn clean package
java -jar target/skillswap-0.0.1-SNAPSHOT.jar
```

## Main routes
- `/` — landing page
- `/register` — create account
- `/login` — sign in
- `/dashboard` — your skills and requests
- `/skills` — browse members' teachable skills
- `/skills/new` — add a skill
- `/requests` — incoming and outgoing exchange requests

## Project structure
```
src/main/java/com/skillswap/
  SkillSwapApplication.java
  config/SecurityConfig.java
  controller/AuthController.java
  controller/DashboardController.java
  controller/SkillController.java
  controller/ExchangeRequestController.java
  model/User.java
  model/Skill.java
  model/ExchangeRequest.java
  repository/UserRepository.java
  repository/SkillRepository.java
  repository/ExchangeRequestRepository.java
  service/CustomUserDetailsService.java
  service/RegistrationService.java
src/main/resources/
  application.properties
  templates/
  static/css/app.css
```

## Notes before production
This is a learning/MVP project, not a production-hardened service. Before public deployment, configure a production database, HTTPS, email verification/password reset, rate limiting, moderation/reporting, privacy policy, and deployment secrets. Never commit real credentials. H2 console is disabled by default.

## License
MIT — see `LICENSE`.
