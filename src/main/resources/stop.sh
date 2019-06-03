pid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`
echo $pid
if [ -n "$pid" ];then
 kill -9 "$pid"
  exit 1
fi
