CREATE TABLE admin
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255)       NOT NULL
);

CREATE TABLE student
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(100)        NOT NULL,
    dob       DATE                NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL,
    sex       BOOLEAN             NOT NULL,
    phone     VARCHAR(20),
    password  VARCHAR(255)        NOT NULL,
    create_at DATE DEFAULT CURRENT_DATE
);

CREATE TABLE course
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    duration   INT          NOT NULL,
    instructor VARCHAR(100) NOT NULL,
    create_at  DATE DEFAULT CURRENT_DATE
);

CREATE TYPE enrollment_status AS ENUM (
    'WAITING',
    'DENIED',
    'CANCEL',
    'CONFIRM'
    );

CREATE TABLE enrollment
(
    id            SERIAL PRIMARY KEY,
    student_id    INT NOT NULL REFERENCES student (id),
    course_id     INT NOT NULL REFERENCES course (id),
    registered_at TIMESTAMP         DEFAULT CURRENT_TIMESTAMP,
    status        enrollment_status DEFAULT 'WAITING',
    CONSTRAINT unique_student_course UNIQUE (student_id, course_id)
);



INSERT INTO admin (username, password)
VALUES ('admin', '$2a$12$VAFOweSq6jIL.fHN8v4S5uDXABCpBVNnDtpRxb52nOgQRtD33BEHa');


INSERT INTO student (name, dob, email, sex, phone, password)
VALUES ('Nguyen Van A', '2000-01-01', 'a@gmail.com', TRUE, '0900000001',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe');
INSERT INTO student (name, dob, email, sex, phone, password)
VALUES ('Tran Van B', '2001-02-02', 'b@gmail.com', TRUE, '0900000002',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van C', '2002-03-03', 'c@gmail.com', TRUE, '0900000003',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van D', '2003-04-04', 'd@gmail.com', TRUE, '0900000004',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van E', '2004-05-05', 'e@gmail.com', TRUE, '0900000005',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van F', '2005-06-06', 'f@gmail.com', TRUE, '0900000006',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van G', '2001-07-07', 'g@gmail.com', TRUE, '0900000007',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van H', '2002-08-08', 'h@gmail.com', TRUE, '0900000008',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van I', '2003-09-09', 'i@gmail.com', TRUE, '0900000009',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van J', '2004-10-10', 'j@gmail.com', TRUE, '0900000010',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van K', '2005-11-11', 'k@gmail.com', TRUE, '0900000011',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van L', '2001-12-12', 'l@gmail.com', TRUE, '0900000012',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van M', '2002-01-13', 'm@gmail.com', TRUE, '0900000013',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van N', '2003-02-14', 'n@gmail.com', TRUE, '0900000014',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van O', '2004-03-15', 'o@gmail.com', TRUE, '0900000015',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van P', '2005-04-16', 'p@gmail.com', TRUE, '0900000016',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van Q', '2001-05-17', 'q@gmail.com', TRUE, '0900000017',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van R', '2002-06-18', 'r@gmail.com', TRUE, '0900000018',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van S', '2003-07-19', 's@gmail.com', TRUE, '0900000019',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe'),
       ('Tran Van T', '2004-08-20', 't@gmail.com', TRUE, '0900000020',
        '$2a$12$q6ZlXv.sjS5am3U07IaNYeyf.gm6TYWKmBgDiOVHP4HxtiwCU6gEe');



INSERT INTO course (name, duration, instructor)
VALUES ('Java Core', 30, 'Mr. Thanh'),
       ('Java Web', 45, 'Mr. Thanh'),
       ('Spring Boot', 60, 'Ms. Hoa'),
       ('SQL Database', 25, 'Mr. Minh'),
       ('HTML/CSS', 20, 'Ms. Lan'),
       ('JavaScript', 40, 'Mr. Khoa'),
       ('ReactJS', 50, 'Ms. My'),
       ('Python Basic', 30, 'Mr. Huy'),
       ('Data Analysis', 60, 'Ms. Trang'),
       ('Machine Learning', 70, 'Mr. Long');


INSERT INTO enrollment (student_id, course_id, status)
VALUES (1, 1, 'CONFIRM'),
       (1, 2, 'WAITING'),
       (1, 3, 'DENIED'),
       (1, 4, 'WAITING'),
       (1, 5, 'CONFIRM');

INSERT INTO enrollment (student_id, course_id, status)
VALUES (2, 1, 'WAITING'),
       (2, 2, 'CONFIRM'),
       (2, 3, 'CANCEL'),

       (3, 1, 'WAITING'),
       (3, 4, 'CONFIRM'),

       (4, 2, 'WAITING'),
       (4, 5, 'CONFIRM'),

       (5, 3, 'WAITING'),
       (5, 6, 'CONFIRM'),

       (6, 4, 'WAITING'),
       (6, 7, 'CONFIRM'),

       (7, 5, 'WAITING'),
       (7, 8, 'CONFIRM'),

       (8, 6, 'WAITING'),
       (8, 9, 'CONFIRM'),

       (9, 7, 'WAITING'),
       (9, 10, 'CONFIRM'),

       (10, 1, 'WAITING'),
       (10, 2, 'CONFIRM'),

       (11, 3, 'WAITING'),
       (11, 4, 'CONFIRM'),

       (12, 5, 'WAITING'),
       (12, 6, 'CONFIRM'),

       (13, 7, 'WAITING'),
       (13, 8, 'CONFIRM'),

       (14, 9, 'WAITING'),
       (14, 10, 'CONFIRM'),

       (15, 1, 'WAITING'),
       (15, 2, 'CONFIRM');
