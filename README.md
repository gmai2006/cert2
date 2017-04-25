Instruction

a. download the mysql database file which should received by now

b. check out the source code from this site to your local computer

c. run gradle depdendencies

d. run gradle eclipse

e. run buildlibdir

f. open the project in eclipse

g. go the project properties and click on [Libraries] tab and click [Add External Jar] button h. select and add server-api.jar from apache tomcat version 8 (should be under /lib/ into the library

the project should compile successfully if not you are missing some thing. Share your errors and we can work from there

Database instruction:

log in to mysql database using super user:
run these commands

GRANT ALL PRIVILEGES ON cert.* TO '*******' IDENTIFIED BY '******' WITH GRANT OPTION;

create database *****;

FLUSH privileges;

quit;

#Now import the cert database

o terminal (Linux) hay DOS command (windows) type:

cd vao directory where the database file is stored.

type following command

mysql -u ****** -p ***** < ./*******.sql
