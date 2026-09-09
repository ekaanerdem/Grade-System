# Veritabanı Tasarımı

Grade System projesi üç temel tablodan oluşacaktır.

## Student

- id
- name
- surname
- email
- password

## Course

- id
- name
- code
- teacher_name

## Grade

- id
- score
- student_id
- course_id

## Entities

- Bir öğrencinin birden fazla notu olabilir.
- Bir dersin birden fazla öğrenciye ait notu olabilir.
- Her not bir öğrenciye ve bir derse aittir.

