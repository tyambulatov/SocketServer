# SocketServer
Simple Java server build on ServerSocket and Socket

## Deployment

1. Clone the repository

```git clone https://github.com/tyambulatov/SocketServer.git```

2. Locate Docker Compose file

```cd docker/```

3. Build a PostgreSQL container

```docker compose up -d```

4. Run a SQL script ```script.sql``` Можно убрать создание схемы в docker-compose.yml

```cd sql/```

5. Start server by running ```Main.java```

```cd ../..```

```cd src/main/java/org/example/```

## Features

* Can process the following requests for Users:
  * show all users - ```localhost:8080/users```
  * show user by id - ```localhost:8080/users/{userId}```