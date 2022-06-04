# 환경 변수 적용
cd
source env.sh
echo host = $MYSQL_HOST, port = $MYSQL_PORT, db = $MYSQL_DATABASE

# 애플리케이션 빌드
cd -
cd ..
./gradlew bootJar

# 애플리케이션 실행
cd build/libs/
nohup java -jar jwp-shopping-cart-0.0.1-SNAPSHOT.jar >> application.log 2>&1 &