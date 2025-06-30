# ShrinkURL

ShrinkURL/
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   └── script/
│       ├── shrinkurl.js
│       └── auth.js
│
├── backend/
│   ├── lib/                     # JAR files (SLF4J, Gson, H2, org.json)
│   ├── out/                     # Compiled classes
│   └── src/
│       └── com/
│           └── shrinkurl/
│               ├── Server.java
│               ├── Database.java
│               ├── RegisterHandler.java
│               ├── LoginHandler.java
│               ├── CheckTokenHandler.java
│               ├── ShortenHandler.java
│               ├── RedirectHandler.java
│               └── models/
│                   └── User.java






{Backend}

Java SE with HttpServer (com.sun.net.httpserver.HttpServer)

SLF4J for logging

H2 Database (embedded DB)

Gson & org.json for JSON handling

JDBC for DB interaction
===============================================================================

{Frontend}

HTML, TailwindCSS via CDN

Vanilla JavaScript (no frameworks)