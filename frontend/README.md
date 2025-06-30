# ShrinkURL - URL Shortener Service

## Getting Started

### Running Frontend
(Suggested to open frontend folder in VS Code)
```
cd frontend/
start index.html
```

## API Documentation

### Authentication Endpoints

#### Register User
```
POST /register
```
Register a new user account
- Request Body: 
```json
{ 
    "username": "test", 
    "password": "pass123" 
}
```

#### Login
```
POST /login
```
Authenticate user and receive access token
- Request Body:
```json
{
    "username": "test",
    "password": "pass123"
}
```
- Response:
```json
{
    "token": "test"
}
```

#### Validate Token
```
POST /checktoken
```
Verify if token is valid (token must match username)
- Header: `Authorization: Bearer <token>`

### URL Shortening Endpoints

#### Create Short URL
```
POST /shorten
```
Generate shortened URL

For anonymous users:
```json
{
    "longUrl": "https://example.com"
}
```

For authenticated users (with custom alias):
```json
{
    "longUrl": "https://example.com",
    "customAlias": "my-alias"
}
```

- Response:
```json
{
    "shortUrl": "http://localhost:8080/abc123"
}
```

#### Access Short URL
```
GET /:shortcode
```
Redirect to original long URL

## Features

- ✨ Anonymous URL shortening
- 🔐 User registration & login
- 🎯 Custom short URLs for logged-in users
- 🔑 Token-based authentication
- 💾 Local H2 database (file-based)
- 📝 SLF4J logging
- 🔄 Basic redirection support
