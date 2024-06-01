# SocketServer
Simple Java server build on ServerSocket and Socket

## Deployment

1. Clone the repository

```git clone https://github.com/tyambulatov/SocketServer.git```

2. Build a Docker Compose 

```cd docker/```
```docker compose up -d```

3. Start server by running ```Main.java``` located in directory

```cd ../src/main/java/org/example/```

## Features

* Can process the following requests for Users:
  * show all users - ```localhost:8080/users```
  * show user by id - ```localhost:8080/users/{userId}```