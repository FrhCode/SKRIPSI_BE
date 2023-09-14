#!/bin/bash

chmod 777 wait-for-it.sh
dos2unix wait-for-it.sh

echo "Waiting for db"
./wait-for-it.sh db:3306 -t 100
echo "db connedted"

java -jar target/skripsi-be-0.0.1-SNAPSHOT.jar