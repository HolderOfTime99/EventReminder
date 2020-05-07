#!/bin/bash

python3 xlsx2json.py
javac -cp ./library/json-simple-1.1.1.jar:./library/mail.jar:./library/activation.jar:. ./api/*.java
java -cp ./library/json-simple-1.1.1.jar:./library/mail.jar:./library/activation.jar:. api.EventReminderMain
