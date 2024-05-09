#!/bin/sh
mvn clean package && clear && java -jar target/*.jar
