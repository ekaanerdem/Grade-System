-- TASK #23 - KUBERNETES POSTGRESQL KARMAŞIK SORGULAR
-- Kullanılan aliaslar:
-- student = s
-- course  = c
-- grade   = g


-- 1) INNER JOIN
-- Sadece birbiriyle eşleşen öğrenci, ders ve not kayıtlarını getirir.
SELECT
    s.name,
    s.surname,
    c.name AS course_name,
    g.score
FROM grade g
INNER JOIN student s ON g.student_id = s.id
INNER JOIN course c ON g.course_id = c.id;


-- 2) LEFT OUTER JOIN
-- Bütün öğrencileri getirir.
-- Öğrencinin notu yoksa grade bilgileri NULL gelir.
SELECT
    s.name,
    s.surname,
    g.score
FROM student s
LEFT OUTER JOIN grade g ON s.id = g.student_id;


-- 3) GROUP BY + COUNT + SUM
-- Her öğrencinin kaç notu olduğunu ve notlarının toplamını gösterir.
SELECT
    s.name,
    COUNT(g.id) AS grade_count,
    SUM(g.score) AS total_score
FROM student s
LEFT JOIN grade g ON s.id = g.student_id
GROUP BY s.id, s.name;


-- 4) WITH CLAUSE (CTE)
-- Önce 80 ve üzerindeki notlardan geçici bir sonuç oluşturur.
WITH high_grades AS (
    SELECT *
    FROM grade g
    WHERE g.score >= 80
)
SELECT *
FROM high_grades;


-- 5) UNION
-- Öğrenci ve öğretmen isimlerini tek listede birleştirir.
-- Tekrar eden değerleri kaldırır.
SELECT s.name AS people
FROM student s

UNION

SELECT c.teacher_name AS people
FROM course c;


-- 6) UNION ALL
-- UNION gibi sonuçları birleştirir fakat tekrar eden değerleri korur.
SELECT s.name AS people
FROM student s

UNION ALL

SELECT c.teacher_name AS people
FROM course c;


-- 7) EXCEPT
-- PostgreSQL'de Oracle MINUS karşılığıdır.
-- İlk sorguda bulunup ikinci sorguda bulunmayan kayıtları getirir.
SELECT g.student_id
FROM grade g
WHERE g.course_id = 1

EXCEPT

SELECT g.student_id
FROM grade g
WHERE g.course_id = 2;


-- 8) DISTINCT
-- Tekrar eden student_id değerlerini kaldırır.
SELECT DISTINCT g.student_id
FROM grade g;


-- 9) COALESCE
-- NULL olan teacher_name yerine belirtilen metni gösterir.
-- PostgreSQL'de Oracle NVL yerine kullanılabilir.
SELECT
    c.name,
    COALESCE(c.teacher_name, 'Öğretmen Atanmadı') AS teacher
FROM course c;


-- 10) CASE
-- Koşula göre farklı sonuç üretir.
-- PostgreSQL'de Oracle DECODE yerine kullanılabilir.
SELECT
    c.name,
    CASE
        WHEN c.teacher_name IS NULL THEN 'Öğretmen Atanmadı'
        ELSE c.teacher_name
    END AS teacher
FROM course c;


-- 11) WHERE EXISTS
-- En az bir not kaydı bulunan öğrencileri getirir.
SELECT
    s.name,
    s.surname
FROM student s
WHERE EXISTS (
    SELECT 1
    FROM grade g
    WHERE g.student_id = s.id
);