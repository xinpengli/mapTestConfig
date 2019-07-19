pid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`
echo $pid
for jarSer in $pid
do
kill -9 $jarSer
done
sleep 3
sh /usr/local/geekplus/tomcat-rms/bin/startup.sh
