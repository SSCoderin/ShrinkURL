# ShrinkURL
Run Frontend (surrest to open frontend folder in vscode )
cd frontend/
start index.html

----------------------------------------------------------------------
API Endpoints

POST /register
Registers a new user

Request Body: { "username": "test", "password": "pass123" }
===================================================================
POST /login
Authenticates user and returns dummy token

Request Body: { "username": "test", "password": "pass123" }

Response: { "token": "test" }
=====================================================================
POST /checktoken
Dummy check if token is valid (token must match username)

Header: Authorization: Bearer <token>
====================================================================
POST /shorten
Shortens a URL

Anonymous: { "longUrl": "https://example.com" }

Logged-in (custom): { "longUrl": "https://example.com", "customAlias": "my-alias" }

Response: { "shortUrl": "http://localhost:8080/abc123" }
========================================================================
GET /:shortcode
Redirects to original long URL

----------------------------------------------------------------------------------------


Features :

 Anonymous URL shortening

 User registration & login

 Custom short URLs for logged-in users

 Token-based dummy auth

 Local H2 database (file-based)

 SLF4J logging

 Basic redirection support

