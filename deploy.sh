#!/bin/bash

user=/root/lecture-user-0.0.1-SNAPSHOT.jar
lecture=/root/lecture-lecture-0.0.1-SNAPSHOT.jar
order=/root/lecture-order-0.0.1-SNAPSHOT.jar
oss=/root/lecture-oss-0.0.1-SNAPSHOT.jar

userLog=user.log
lectureLog=lecture.log
orderLog=order.log
ossLog=oss.log

nohup java -jar $user > $userLog &
sleep 30s
#echo "$user: start success!"

nohup java -jar $lecture > $lectureLog &
sleep 30s
#echo "$lecture: start success!"

nohup java -jar $order > $orderLog &
#echo "order: start success!"

nohup java -jar $oss > $ossLog &
#echo "$oss: start success!"