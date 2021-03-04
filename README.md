# training-management-app
An application to manage and facilitate the organization of on-site
training sessions, and the contact between trainer and learners.

Main features (in progress):  
  
Course templates,  
Course edition management,  
Registration of students and trainers,  
Notifications,  
Course Schedule,  
REST API.

## Technologies:

*Java 11  
*SpringBoot  
*Hibernate  
*H2database  
*MySQL  
*Bootstrap

## How to run:

1. Clone the repository onto your own computer.

2. Go to the main folder of the project and run this command:
   
*for the Unix system:
```
./mvnw spring-boot:run
```
   
*for the Windows CMD:

```
mvnw.cmd spring-boot:run
```

3. Go to the following page in your browser: http://localhost:8080

4. You can use following accounts to check all features of the system:

| user  |  password |
| ------------- | ------------- |
| admin  | admin  |
| trainer  | admin  |
| student  | admin  |

By default application runs in dev mode on port 8080.

