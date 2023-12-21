# How to run this application?

- Step 1: Install intelliJ, JDK 17 and docker
- Step 2: Add env variables bellow:
  - REDIS_HOST=localhost
  - REDIS_PORT=6379
  - SQL_USERNAME=postgres
  - SQL_PASSWORD=postgres
  - SQL_DATABASE_URL=jdbc:postgresql://localhost:54322/postgres
- Step 3: Run docker-compose.yml
```
  docker-compose up
```
Step 4: Enjoy :)
