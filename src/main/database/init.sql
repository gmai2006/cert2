alter table EXAM add rank int DEFAULT 1;

update VENDOR set rank=1;
commit;
update VENDOR set rank=1
where UPPER(vendor_name) in ('CISCO', 'MICROSOFT', 'ORACLE', 
'COMPTIA', 'ITIL', 'IBM', 'LINUX', 
'CHECKPOINT', 'EMC',
'HUAWEI', 'CITRIX');
commit;

#clean up database - 05-23-2015
DROP TABLE SHOPPING_CART_RECORD;
DROP TABLE ORDER_CAP;
DROP TABLE EXAM_ORDER;
DROP TABLE EXAM_ORDER_STATUS_VALUES;
DROP TABLE EXAM_ORDER_TRANSACTION;
DROP TABLE EXTEND_PLAN;
DROP TABLE AFFILIATION;
DROP TABLE BLACKLIST;
DROP TABLE ASSOC_URL;
DROP TABLE ELEMENT;
DROP TABLE SUBSCRIPTION;
DROP TABLE MAIL_HISTORY;
DROP TABLE PAGE;

#Update and consolidate vendor names
update EXAM set vendor_id=41, vendor_name='Cisco'
where vendor_id in (122,124,130,125);

update CERTIFICATION set vendor_id=41
where vendor_id in (122,124,130,125); 

update EXAM set vendor_id=109, vendor_name='Alcatel Lucent'
where vendor_id in (126);

update CERTIFICATION set vendor_id=109
where vendor_id in (126); 

update EXAM set vendor_id=131, vendor_name='Brocade Communications'
where vendor_id in (120);

update CERTIFICATION set vendor_id=131
where vendor_id in (120); 

update EXAM set vendor_id=123, vendor_name='Check Point'
where vendor_id in (71);

update CERTIFICATION set vendor_id=123
where vendor_id in (71); 

update EXAM set vendor_id=117, vendor_name='Enterasys Networks'
where vendor_id in (60);

update CERTIFICATION set vendor_id=117
where vendor_id in (60); 

update EXAM set vendor_id=118, vendor_name='Juniper Networks'
where vendor_id in (58);

update CERTIFICATION set vendor_id=118
where vendor_id in (58); 

delete from VENDOR where vendor_id not in (select distinct(vendor_id) from EXAM); 
commit;

select * from EXAM where exam_name not in (select distinct (exam_name) from QUESTION);

delete from EXAM where exam_name not in (select distinct (exam_name) from QUESTION);

delete from EXHIBIT where exam_name not in (select distinct (exam_name) from QUESTION);

delete from EXPLANATION where exam_name not in (select distinct (exam_name) from EXAM);

delete from OBJECTIVE where exam_name not in (select distinct (exam_name) from EXAM);

delete from ANSWER where exam_name not in (select distinct (exam_name) from EXAM);

delete from QUESTION where TRIM(exam_name)='';

delete from CERTIFICATION where id in
(
select distinct cert_id from CERTIFICATION_EXAM where exam_name not in (select exam_name from EXAM)
);

update EXAM 
inner join VENDOR using (vendor_id)
set EXAM.vendor_name=VENDOR.vendor_name;

update EXAM set vendor_id=36 where vendor_id=116
and exam_desc like 'IBM%';

update EXAM set vendor_id=43, vendor_name='Oracle' where vendor_id=116
and exam_name like '1Z0%';

update EXAM set vendor_id=35, vendor_name='Nortel' where vendor_id=116
and exam_name like '920%';

update EXAM set vendor_id=40, vendor_name='HP' where vendor_id=116
and exam_name like 'HP0%';

update EXAM set vendor_id=35, vendor_name='Nortel' where vendor_id=116
and exam_name like '922%';

update EXAM set vendor_id=118, vendor_name='Juniper Networks' where vendor_id=116
and exam_name like 'JN0%';

update EXAM set vendor_id=54, vendor_name='Apple' where vendor_id=116
and exam_name like '9L0%';
commit;

select exams.* from 
(select vendor_name, count(*) as num from EXAM group by vendor_name) as exams
where exams.num > 100;

update EXAM set rank=100
where exam_name in 
(select exam_name from (select exam_name, max(questions) as num from EXAM 
inner join VENDOR using (vendor_id)
where VENDOR.rank= 100
group by vendor_id) as exams);
