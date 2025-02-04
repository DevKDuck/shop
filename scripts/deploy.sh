#!/bin/bash
# 현재 빌드된 jar 파일의 경로 설정
BUILD_JAR=$(ls /home/ec2-user/shop/duckshop/target/shop-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/shop/duckshop/deploy.log

echo "> build 파일 복사" >> /home/ec2-user/shop/duckshop/deploy.log
DEPLOY_PATH=/home/ec2-user/shop/duckshop/target/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/shop/duckshop/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/shop/duckshop/deploy.log
else
  echo "> kill -15 $CURRENT_PID" >> /home/ec2-user/shop/duckshop/deploy.log
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> $DEPLOY_JAR 배포" >> /home/ec2-user/shop/duckshop/deploy.log
nohup java -jar $DEPLOY_JAR >> /home/ec2-user/shop/duckshop/nohup.out 2>> /home/ec2-user/shop/duckshop/deploy_err.log &
