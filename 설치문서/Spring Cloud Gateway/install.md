## 1. Write a query to compare each active account’s balance to the average balance of all active accounts of the same type.
```
  select id, a.type, status, amount, (amount - avg_amount) as difference
  from problem1.account a 
      left outer join 
     (select type, avg(amount) as avg_amount
      from problem1.account
      where status = 'Closed'
      group by type) b on a.type = b.type
  where status = 'Active';
```
![Alt text](/final_img/part2/final2_1.PNG)
* * *
## 2. Create an employee table in the metastore that contains the employee records stored in HDFS.
```
create database problem2;

  create external table if not exists problem2.solution
  (id int,
   fname string,
   lname string,
   address string,
   city string,
   state string,
   zip string,
   birthday string,
   hireday string)
  stored as parquet
  location '/user/training/problem2/data/employee';

  select * from problem2.solution;
```
![Alt text](/final_img/part2/final2_2.PNG)
* * *
## 3. Generate a table that contains all customers who have negative account balances.
```
  create table problem3.solution as
  select a.id, a.fname, a.lname, a.hphone
  from problem3.customer a
     , problem3.account b
  where a.id = b.custid
  and b.amount < 0;

  select * from problem3.solution;
```
![Alt text](/final_img/part2/final2_3.PNG)
* * *
## 4. LoudAcre Mobile has merged with another company located in California. Each company has a list of customers in different formats. Combine the two customer lists into a single dataset using an identical schema.
* * *
## 5. The bank is making a Facebook group for the Palo Alto, CA branch. Generate a script that outputs the customers and employees who live in Palo Alto, CA.
```
  select a.fname, a.lname, a.city, a.state
  from (
  select fname, lname, city, state
  from problem5.employee
  where city = 'Palo Alto'
  and state = 'CA'
  union all
  select fname, lname, city, state
  from problem5.customer
  where city = 'Palo Alto'
  and state = 'CA'
  ) a
```
![Alt text](/final_img/part2/final2_5.PNG)
* * *
## 6. There are privacy concerns about the employee data that is stored on the cluster. Your task is to remove any age information from the employee data by creating a new table for the data analysts  to query against.
```
  create table problem6.solution as
  select id, fname, lname, address, city, state, zip
       , substr(birthday,0,5) as birthyear
  from problem6.employee;

  select * from problem6.solution;
```
![Alt text](/final_img/part2/final2_6.PNG)
* * *
## 7. Generate a report that contains all of the Seattle employee names in sorted order.
```
  select concat(lname , ',', fname) as name
  from problem7.employee
  where city = 'Seattle'
  order by name
```
![Alt text](/final_img/part2/final2_7.PNG)
* * *
## 8. Use Sqoop to export customer data from HDFS into a MySQL database table. Place the data in the solution table in MySQL, which has been created and is currently empty.
* * *
## 9. Your company is being acquired by another company. To prepare for this acquisition, update the customer records to guarantee there will be no duplicate IDs with their existing customer IDs.
```
  create table problem9.solution as
  select concat('A', cast(id as string)) as id, fname, lname, address, city, state, zip, birthday
  from problem9.customer
```
![Alt text](/final_img/part2/final2_9.PNG)
* * *
## 10. Your boss needs specialized reports using the billing data and is constantly asking for help to write SQL queries. Create a database view in the metastore so that your boss has customer and billing data joined.
```
  create view problem10.solution as
  select b.id, b.fname, b.lname, b.city, b.state
       , a.charge, substr(a.tstamp, 1, 10) as billdata
  from problem10.billing a
     , problem10.customer b
  where a.id = b.id;

  select * from problem10.solution;
```
![Alt text](/final_img/part2/final2_10.PNG)
