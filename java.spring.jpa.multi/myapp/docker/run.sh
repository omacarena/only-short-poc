#!/bin/sh

echo "********************************************************"
echo "Wait for mongodb to be available"
echo "********************************************************"

while ! mysql --host=mysql1 --port=3306 --user=myuser1 --password=mypass1 --execute "select 1"; do
    printf 'mysql1 is still not available. Retrying...\n'
    sleep 3
done

while ! mysql --host=mysql2 --port=3306 --user=myuser2 --password=mypass2 --execute "select 1"; do
    printf 'mysql2 is still not available. Retrying...\n'
    sleep 3
done

echo "********************************************************"
echo "Starting myapp"
echo "********************************************************"

java -Dserver.port=$SERVER_PORT \
     -Dspring.profiles.active=docker \
     -jar /usr/local/myapp/myapp.jar