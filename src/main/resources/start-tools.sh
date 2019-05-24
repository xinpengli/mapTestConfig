BACK_DATE=`date +%Y-%m-%d" "%H:%M:%S`
pid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`  
rmsToolsPid=`ps aux |grep athenatest |grep -v grep  | awk '{print $2}'`  


    echo $pid  
    
     
            echo $BACK_DATE =====kill tomcat ==========  >> /home/axjsms/shell/retomcatLog.txt
            kill -9 $pid  
            sleep 3  
            echo $BACK_DATE =====start tomcat ==========  >> /home/axjsms/shell/retomcatLog.txt
            /home/axjsms/tomcat_9007/bin/startup.sh 

  
    
   
    echo $BACK_DATE =========startup.sh 
