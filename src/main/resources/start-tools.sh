BACK_DATE=`date +%Y-%m-%d" "%H:%M:%S`
ppid=`ps aux |grep tomcat-rms |grep -v grep  | awk '{print $2}'`  
pid=`ps aux |grep athenatest.jar |grep -v grep  | awk '{print $2}'`  


    echo $pid  
    
     
            echo $BACK_DATE =====athenatest.java==========  >> /home/axjsms/shell/retomcatLog.txt
            kill -9 $pid  
            sleep 3  
            echo $BACK_DATE =====start athenatest.java ==========  >> /home/axjsms/shell/retomcatLog.txt
           java -jar  /home/test-tools/athena-test-3.1/athenatest.jar

  
    
   
    echo $BACK_DATE =========startup.sh 
