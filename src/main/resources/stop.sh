pid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`
echo $pid
for process in $pid
do
kill -9 $process
done

