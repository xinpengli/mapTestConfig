pid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`
echo $pid
kill -9 $pid  
sleep 3  
sh /usr/local/geekplus/tomcat-rms/bin/startup.sh
