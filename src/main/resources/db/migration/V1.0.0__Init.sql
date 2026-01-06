
create TABLE IF NOT EXISTS books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  created_on timestamp NULL DEFAULT NULL,
  updated_on timestamp NULL DEFAULT NULL,
  title varchar(50) NOT NULL,
  content text NOT NULL
);

INSERT INTO books (created_on, updated_on, title, content) VALUES 
('2025-12-31 09:58:08',NULL,'hanoi 1989','Memory'),
('2025-12-31 09:58:19',NULL,'munich 2004','Roman');


create TABLE IF NOT EXISTS reviews (
  id INT AUTO_INCREMENT PRIMARY KEY,
  book_id int DEFAULT NULL,
  created_on timestamp NULL DEFAULT NULL, 
  updated_on timestamp NULL DEFAULT NULL,
  name varchar(50) NOT NULL,
  email varchar(150) NOT NULL,
  content text NOT NULL,
  like_status enum('Low','Medium','High') DEFAULT NULL,
  FOREIGN KEY(book_id)
		REFERENCES books(id)
);

INSERT INTO reviews (book_id, created_on, updated_on, name, email, content, like_status) VALUES 
(1,'2025-12-31 10:00:56',NULL,'hong','hong@gmail.com','good memory','High'),
(2,'2025-12-31 10:01:35',NULL,'tom','tom@yahoo.de','chaos time','High');


create TABLE IF NOT EXISTS user_account (
  id INT AUTO_INCREMENT PRIMARY KEY,
  authority varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL
);

INSERT INTO user_account (authority, password, username) VALUES 
('ROLE_USER','$2a$10$d38vAsu4ot.vnZSn2SyZWOLwDwxkeBVAELIj6kG4V0MMOQ4ZFAy9u','user'),
('ROLE_AUTOR','$2a$10$gMLVJRnGBoOqcLA.jbrWDu8doYxX6/bqMtGroVobvhEDSg34wM6Ly','autor'),
('ROLE_ADMIN','$2a$10$SrmbiTY/ESHlI2OxHOsAKeoTQ/Cg3aavbQPn4VupCPll4F7QNuRkC','admin');