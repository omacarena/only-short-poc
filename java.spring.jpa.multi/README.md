Spring Boot + Multiple datasources on same entity + Docker
-

Using multiple data sources for the same entity in a Spring Boot project. 

Running
-

Current project is managed with `Gradle 3.5`.
Gradle wrapper exists within the project.
Therefore no prior installation of Gradle is needed.

1. **Build:** ./gradlew build
2. **Docker, build:** docker-compose build
3. **Docker, run containers:** docker-compose up
4. A REST API is exposed by the SpringBoot app in order to interact with the data sources manually:
- For source 1:
    - GET http://localhost:8888/v1/organization/src1
    - POST http://localhost:8888/v1/organizationsrc1
- For source 2:
    - GET http://localhost:8888/v1/organization/src2
    - POST http://localhost:8888/v1/organizationsrc2
    
- check the `OrganizationController` for the actual implementation

**NOTE:** `MySQL` containers will create a data bound volumes where the actual data reside on `./_data`.

Structure
-

`docker-compose.yml` defines:
- two `MySQL v5.7` database containers
- one `myapp` container running the main SpringBoot application

`myapp` defines:
- two configurations: `application.yml` and `application-docker.yml`
- `application.yml` is the `default` profile configuration
- `application-docker.yml` is the `docker` profile configuration that will be run inside the Docker container using:

        java -Dserver.port=$SERVER_PORT \
             -Dspring.profiles.active=docker \
             -jar /usr/local/myapp/myapp.jar
             
`myapp/docker` defines:
- dockerfile used to build the `myapp` image
- is based on OpenJDK-alpine 8
- adds the `mysql-client` package in order to check if the `MySQL` containers are up and running by doing:

        while ! mysql --host=mysql1 --port=3306 --user=myuser1 --password=mypass1 --execute "select 1"; do
            printf 'mysql1 is still not available. Retrying...\n'
            sleep 3
        done

- executes `run.sh` to start the SpringBoot application from jar
