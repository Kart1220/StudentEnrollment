#!/bin/sh

cd ./enrollmate-core || exit
./gradlew clean build publishToMavenLocal
cd ..


cd ./enrollmate-course || exit
./gradlew clean build
cd ..

cd ./enrollmate-students || exit
./gradlew clean build
cd ..

docker-compose down --rmi all --volumes --remove-orphans enrollmate-course enrollmate-students
docker-compose build
docker-compose up -d
