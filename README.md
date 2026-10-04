OOP Major Assignment
Name: Amit Narayan Satpathy
ID Number: 2021AAPS2127H
email:f20212127@hyderabad.bits-pilani.ac.in

#  Social Media Application — Spring Boot REST API

> A fully functional backend REST API for a Social Media platform, built using **Java** and **Spring Boot**, developed on **IntelliJ IDEA** and tested via **Postman**.

---

##  Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Data Model & Entity Relationships](#data-model--entity-relationships)
- [API Endpoints](#api-endpoints)
  - [User Endpoints](#-user-endpoints)
  - [Post Endpoints](#-post-endpoints)
  - [Comment Endpoints](#-comment-endpoints)
  - [Feed Endpoint](#-feed-endpoint)
- [Request & Response Reference](#request--response-reference)
- [Error Handling](#error-handling)
- [Key Design Decisions](#key-design-decisions)
- [How to Run](#how-to-run)

---

##  Overview

This project implements the **backend** of a social media application with full CRUD (Create, Read, Update, Delete) support for **Users**, **Posts**, and **Comments**. The system supports:

- User registration (signup) and authentication (login)
- Creating, editing, and deleting posts
- Creating, editing, and deleting comments on posts
- Fetching a global feed of all posts, sorted by date (newest first)
- Fetching user, post, and comment details individually
- Structured JSON responses with a consistent `ResponseWrapper` and `ErrorResponse` pattern

---

##  Tech Stack

| Layer              | Technology                          |
|--------------------|--------------------------------------|
| Language           | Java 17+                             |
| Framework          | Spring Boot                          |
| ORM                | JPA / Hibernate (Jakarta Persistence)|
| Database           | Relational DB (auto-configured)      |
| API Testing        | Postman                              |
| IDE                | IntelliJ IDEA                        |
| Build Tool         | Maven / Gradle (Spring Boot default) |

---

##  Project Structure

```
com.example.newtrial24/
│
├── Newtrial24Application.java        # Spring Boot entry point
│
├──  Entities (JPA-mapped DB tables)
│   ├── User.java                     # User entity (mapped to app_user table)
│   ├── Post.java                     # Post entity
│   └── Comment.java                  # Comment entity
│
├──  Repositories (Data Access Layer)
│   ├── UserRepository.java           # JPA queries for User
│   ├── PostRepository.java           # JPA queries for Post (with date sorting)
│   └── CommentRepository.java        # JPA queries for Comment
│
├──  Services (Business Logic Layer)
│   ├── UserService.java              # Core service — orchestrates all operations
│   ├── PostService.java              # Post-specific business logic
│   └── CommentService.java           # Comment-specific business logic
│
├──  Controller (API Layer)
│   └── UserController.java           # Single REST controller for all endpoints
│
├──  Request DTOs
│   ├── LoginRequest.java
│   ├── SignupRequest.java
│   ├── PostRequest.java
│   ├── EditPostRequest.java
│   ├── PostIDRequest.java
│   ├── CommentRequest.java
│   ├── CommentEditRequest.java
│   └── CommentIDRequest.java
│
├──  Response DTOs
│   ├── UserResponse.java
│   ├── PostResponse.java
│   ├── CommentResponse.java
│   └── CommentCreatorResponse.java
│
└──  Utilities
    ├── ResponseWrapper.java          # Generic wrapper for success/error responses
    └── ErrorResponse.java            # Standardized error response object
```

---

##  Data Model & Entity Relationships

```
┌──────────────┐         ┌──────────────┐         ┌──────────────────┐
│     User     │         │     Post     │         │     Comment      │
│──────────────│         │──────────────│         │──────────────────│
│ userID (PK)  │ 1────── │ postID (PK)  │ 1────── │ commentID (PK)   │
│ name         │    N    │ postBody     │    N    │ commentBody      │
│ email        │         │ date         │         │ post_id (FK)  ───┘
│ password     │         │ user_id (FK) │         │ user_id (FK)  ───┐
└──────────────┘         └──────────────┘         └──────────────────┘
       │                                                    │
       └────────────────────────────────────────────────────┘
                         (Author of comment)
```

**Relationships:**
- `User` → `Post` : **One-to-Many** (`@OneToMany` — one user can create many posts)
- `Post` → `Comment` : **One-to-Many** (`@OneToMany` — one post can have many comments)
- `User` → `Comment` : **Many-to-One** (`@ManyToOne` — each comment is authored by one user)
- The `User` entity is mapped to the table `app_user` to avoid conflicts with SQL reserved keywords

---

##  API Endpoints

###  User Endpoints

| Method | Path       | Description                   | Request Body       | Response            |
|--------|------------|-------------------------------|---------------------|---------------------|
| `POST` | `/signup`  | Register a new user           | `SignupRequest`     | Success string or `ErrorResponse` |
| `POST` | `/login`   | Authenticate an existing user | `LoginRequest`      | Success string or `ErrorResponse` |
| `GET`  | `/user`    | Get user details by ID        | `?userID={id}`      | `UserResponse` or `ErrorResponse` |
| `GET`  | `/users`   | Get all registered users      | —                   | `List<UserResponse>` |

---

###  Post Endpoints

| Method   | Path     | Description                  | Request Body / Param    | Response            |
|----------|----------|------------------------------|--------------------------|---------------------|
| `POST`   | `/post`  | Create a new post            | `PostRequest`            | Success string or `ErrorResponse` |
| `GET`    | `/post`  | Get a post by ID             | `?postID={id}`           | `PostResponse` (with nested comments) or `ErrorResponse` |
| `PATCH`  | `/post`  | Edit an existing post's body | `EditPostRequest`        | Success string or `ErrorResponse` |
| `DELETE` | `/post`  | Delete a post and its comments | `?postID={id}`         | Success string or `ErrorResponse` |

---

###  Comment Endpoints

| Method   | Path       | Description                     | Request Body / Param      | Response            |
|----------|------------|---------------------------------|----------------------------|---------------------|
| `POST`   | `/comment` | Add a comment to a post         | `CommentRequest`           | Success string or `ErrorResponse` |
| `GET`    | `/comment` | Get a comment by ID             | `?commentID={id}`          | `CommentResponse` or `ErrorResponse` |
| `PATCH`  | `/comment` | Edit an existing comment's body | `CommentEditRequest`       | Success string or `ErrorResponse` |
| `DELETE` | `/comment` | Delete a comment                | `?commentID={id}`          | Success string or `ErrorResponse` |

---

###  Feed Endpoint

| Method | Path | Description                                              | Response                  |
|--------|------|----------------------------------------------------------|---------------------------|
| `GET`  | `/`  | Returns all posts sorted by date (newest first), with nested comments and comment authors | `List<PostResponse>` |

---

##  Request & Response Reference

### `SignupRequest`
```json
{
  "email": "user@example.com",
  "name": "John Doe",
  "password": "secret123"
}
```

### `LoginRequest`
```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

### `PostRequest`
```json
{
  "postBody": "Hello, this is my first post!",
  "userID": 1
}
```

### `EditPostRequest`
```json
{
  "postID": 3,
  "postBody": "Updated post content here."
}
```

### `CommentRequest`
```json
{
  "commentBody": "Great post!",
  "postID": 3,
  "userID": 2
}
```

### `CommentEditRequest`
```json
{
  "commentID": 5,
  "commentBody": "Edited comment text."
}
```

### `PostResponse` (sample)
```json
{
  "postID": 3,
  "postBody": "Hello, this is my first post!",
  "date": "2024-06-01",
  "comments": [
    {
      "commentID": 5,
      "commentBody": "Great post!",
      "commentCreator": {
        "userID": 2,
        "name": "Jane Smith"
      }
    }
  ]
}
```

### `ErrorResponse` (sample)
```json
{
  "Error": "Post does not exist"
}
```

---

##  Error Handling

The application uses a **`ResponseWrapper<T>`** generic class and a dedicated **`ErrorResponse`** class to ensure all API responses are consistent and structured.

| Scenario                          | Error Message Returned                  |
|-----------------------------------|-----------------------------------------|
| Login with unknown email          | `User does not exist`                   |
| Login with wrong password         | `Username/Password Incorrect`           |
| Signup with existing email        | `Forbidden, Account already exists`     |
| Creating a post for unknown user  | `User does not exist`                   |
| Editing/deleting nonexistent post | `Post does not exist`                   |
| Commenting on nonexistent post    | `Post does not exist`                   |
| Editing/deleting nonexistent comment | `Comment does not exist`             |

All error responses are returned with HTTP **200 OK** and the `ErrorResponse` JSON body (using `UpperCamelCase` naming via `@JsonNaming`).

---

##  Key Design Decisions

1. **Layered Architecture**: Clean separation of `Controller` → `Service` → `Repository` layers ensures testability and maintainability.
2. **Single Controller**: All routes are handled in `UserController.java` for simplicity, delegating all business logic to `UserService`.
3. **Cascading Deletes**: When a `Post` is deleted, all its associated `Comment` entities are deleted first to avoid orphaned records (handled manually in `UserService.deletePost()`).
4. **Date Stamping**: Posts are automatically date-stamped with `new Date()` at creation time. Feed queries use `findAllByOrderByDateDescPostIDDesc()` for tie-breaking.
5. **Generic Response Wrapper**: `ResponseWrapper<T>` cleanly separates data payloads from errors at the controller level without throwing exceptions.
6. **DTO Pattern**: Dedicated Request and Response DTOs decouple the API contract from internal JPA entities, preventing accidental data leakage.
7. **`app_user` Table Name**: The `User` entity is explicitly mapped to `app_user` via `@Table(name = "app_user")` to avoid collisions with the SQL reserved keyword `USER`.

---

## How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/amitnarayansatpathy/<repo-name>.git
   cd <repo-name>
   ```

2. **Configure your database** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/socialmedia_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build and run**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or run `Newtrial24Application.java` directly from **IntelliJ IDEA**.

4. **Test APIs using Postman**
   - Import the endpoints listed above
   - Set `Content-Type: application/json` for all `POST` and `PATCH` requests
   - Base URL: `http://localhost:8080`

> Built with Java + Spring Boot

