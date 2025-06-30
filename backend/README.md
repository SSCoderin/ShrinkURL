# ShrinkURL

1. Install Requirements
JDK 11 or above
Download required JARs
-------------------------------------------------------------------------
2. Compile
cd backend/
javac -cp "lib/*" -d out src/com/shrinkurl/*.java src/com/shrinkurl/models/*.java

------------------------------------------------------------------------
3. Run Server
java -cp "out;lib/*" com.shrinkurl.Server

Server started at http://localhost:8080

--------------------------------------------------------------------------------
⚙️ Required Libraries
Download and place the following in backend/lib/:

Library	Version	Download
SLF4J API	2.0.9	slf4j-api
SLF4J Simple	2.0.9	slf4j-simple
Gson	2.10.1	gson
org.json	20231013	json
H2 Database	Latest	h2 (or h2-2.2.224.jar)

