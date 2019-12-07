./gradlew build
scp build/libs/web-api-1.0.jar root@167.71.48.207:/var/tech-madness/web-api/web-api-1.0.jar
ssh root@167.71.48.207 'systemctl restart web-api'