# URL Shortener Backend Service

This is the backend service for a **URL Shortener** application developed using **Spring Boot**. The service includes several key features such as rate limiting, URL storage in a SQL database, and caching for high-performance redirection.

## Table of Contents

1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Setup and Installation](#setup-and-installation)

## Features

- **URL Shortening**: Converts long URLs into shorter versions.
- **Redirection**: Redirects users to the original URL when accessing the shortened link.
- **Analytics**: Tracks the number of clicks on each shortened URL.
- **Rate Limiting**: Restricts excessive use of the service to ensure fair access and prevent abuse.
- **Caching**: Boosts performance for frequent redirects by storing commonly accessed URLs in memory.

## Technologies Used

- **Spring Boot**: A framework used for developing the backend service.
- **Resilience4j**: Implements rate-limiting to control the number of requests per user or IP address.
- **SQL Database (MySQL/PostgreSQL)**: Stores the mapping of original URLs to shortened URLs (used for demonstration purposes).
- **Redis**: In-memory caching for frequently accessed URLs to reduce database load (optional).
- **JPA/Hibernate**: Provides an interface to interact with the SQL database.

## Prerequisites

Before setting up and running the project, make sure you have the following installed:

- **Java 17** or higher
- **Maven**
- **PostgreSQL** (based on your chosen configuration)
- **IntelliJ IDEA**

## Setup and Installation

### 1. Install Java

Ensure **Java 17** (or later) is installed on your system. You can download it from the official [Java website](https://adoptopenjdk.net/).

### 2. Install IntelliJ IDEA

For an enhanced development experience, install **IntelliJ IDEA** from the official [IntelliJ IDEA website](https://www.jetbrains.com/idea/download/).

- Open IntelliJ IDEA and import the project as a Maven
- Ensure that all required dependencies are downloaded.

### 3. Clone the Repository

Clone the repository to your local machine using Git.

