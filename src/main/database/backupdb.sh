#!/bin/bash
datevar=`date +%Y-%m-%d`
echo $datevar 
mysqldump -u"$4" -p"$1" -h"$2" "$3" --skip-comments --skip-lock-tables > /home/tomcat/databases/"$3"_"$datevar".sql
echo 'db backup complete'
mysql -udung -p"$1" "$3" < /home/tomcat/databases/"$3"_"$datevar".sql
echo 'db restore complete'

