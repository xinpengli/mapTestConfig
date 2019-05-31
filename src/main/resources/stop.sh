BACK_DATE=`date +%Y-%m-%d" "%H:%M:%S`
pid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`  
rmsToolsPid=`ps aux |grep athenatest |grep -v grep  | awk '{print $2}'`  


    echo $pid  
    if [ -n "$pid" ]  
    then  
    {  
            echo $BACK_DATE =====kill tomcat ==========  >> /home/axjsms/shell/retomcatLog.txt
            kill -9 $pid  
            sleep 3  
           
  
    }  
    else
    echo $BACK_DATE =========startup.sh 
