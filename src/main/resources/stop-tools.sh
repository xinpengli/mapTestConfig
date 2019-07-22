pid=`ps aux |grep maptest.jar |grep -v grep  | awk '{print $2}'`
echo $pid
for process in $pid
do
kill -9 $process
done

