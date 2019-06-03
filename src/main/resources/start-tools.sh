pid=`ps aux |grep athenatest.jar |grep -v grep  | awk '{print $2}'`
echo $pid
kill -9 $pid
sleep 3
nohup java -jar  /home/test-tools/athena-test-3.1/athenatest.jar &
