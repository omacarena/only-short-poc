Docker + Spring Boot + Mongo
-

A simple example which does:
- creates a docker container for Mongo
- creates a docker container for a SpringBoot REST application which connects to the Mongo database
- ensure that the SpringBoot app is started after the Mongo has started successfully

Building and running the project
-

- build the project - `./gradlew build`
- build the images - `./docker-compose build`
- start containers - `./docker-compose up`
- go to - `http://localhost:8888` - it should display "works :)" if the app can connect to Mongo

Structure
-

`docker-compose.yml`:
- SpringBoot app has internally port `8080` (by `SERVER_PORT`) which is exposed to the host via `8888`
- Mongo `command: ["mongod", "--rest"]` enables the REST interface, which can be used to query the health of the instance
- Mongo has internally port `27017` for `mongo://` protocol connections. This is exposed to the host via `12345`
- Mongo has internally port `28017` for REST interface `http://` protocol connections. This is exposed to the host via `23456`

`myapp/docker/Dockerfile`:
- source file for building the SpringBoot app image
- is based on Alpine Linux which has OpenJdk 8 installed
- is installing `netcat-openbsd` in order to check if Mongo is up and running via its REST interface
- execute `run.sh` to start the app

`myapp/docker/run.sh`
- uses `nc` (`netcat-openbsd`) in order to wait until Mongo is up and running
- uses JRE from the OpenJdk 8 in order to start the app