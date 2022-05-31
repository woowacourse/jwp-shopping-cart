./gradlew bootJar
cd build/libs/
nohup java -jar jwp-shopping-cart-0.0.1-SNAPSHOT.jar >> application.log 2>&1 &
