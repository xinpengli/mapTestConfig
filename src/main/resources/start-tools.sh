pid=`ps aux |grep athenatest.jar |grep -v grep  | awk '{print $2}'`
echo $pid
for jarSer in $pid
do
kill -9 $jarSer
done
sleep 3
nohup  java -jar /home/test-tools/maptest/maptest.jar &

