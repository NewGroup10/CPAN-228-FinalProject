# CPAN-228-FinalProject

This application manages distribution centres and items for a warehouse system.

## Spring Profiles

The application supports two different profiles:

### Development Profile (dev)

- Runs on port 8080
- Uses H2 in-memory database named "devdb"
- Contains basic distribution centres and items

To run the application in development mode:

```bash
# Using the batch file (Windows)
run-dev.bat

# Using the shell script (Linux/Mac)
./run-dev.sh

# Using Maven with environment variable (Windows)
set SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run

# Using Maven with environment variable (Linux/Mac)
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run

# Using Java
java -jar target/CPAN-228-Assignment3-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### QA Profile (qa)

- Runs on port 8081
- Uses H2 in-memory database named "qadb"
- Contains additional items and different distribution centre names

To run the application in QA mode:

```bash
# Using the batch file (Windows)
run-qa.bat

# Using the shell script (Linux/Mac)
./run-qa.sh

# Using Maven with environment variable (Windows)
set SPRING_PROFILES_ACTIVE=qa
mvn spring-boot:run

# Using Maven with environment variable (Linux/Mac)
export SPRING_PROFILES_ACTIVE=qa
mvn spring-boot:run

# Using Java
java -jar target/CPAN-228-Assignment3-0.0.1-SNAPSHOT.jar --spring.profiles.active=qa
```

## Accessing the Application

- Development: http://localhost:8080
- QA: http://localhost:8081

## H2 Console

You can access the H2 console to view the database:

- Development: http://localhost:8080/h2-console
- QA: http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:devdb (for dev) or jdbc:h2:mem:qadb (for qa)
Username: sa
Password: password # CPAN-228-FinalProject
