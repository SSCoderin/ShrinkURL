# ShrinkURL - URL Shortener Service

## Prerequisites
- JDK 11 or above

## Quick Start Guide

### 1. Setup Dependencies
First, download and place the required JAR files in the `backend/lib/` directory.

### 2. Compile the Project
```bash
cd backend/
javac -cp "lib/*" -d out src/com/shrinkurl/*.java src/com/shrinkurl/models/*.java
```

### 3. Run the Server
```bash
java -cp "out;lib/*" com.shrinkurl.Server
```
Once started, the server will be available at: http://localhost:8080

## Required Dependencies
Place these JAR files in the `backend/lib/` directory:

| Library | Version | Download Link |
|---------|---------|---------------|
| SLF4J API | 2.0.9 | [slf4j-api](https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar) |
| SLF4J Simple | 2.0.9 | [slf4j-simple](https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar) |
| Gson | 2.10.1 | [gson](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar) |
| org.json | 20231013 | [json](https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar) |
| H2 Database | 2.2.224 | [h2](https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar) |

## Features
- Shorten long URLs to manageable links
- Track URL visits
- Persistent storage using H2 Database
- RESTful API endpoints

## System Requirements
- Minimum 256MB RAM
- 100MB free disk space
- Network connectivity
