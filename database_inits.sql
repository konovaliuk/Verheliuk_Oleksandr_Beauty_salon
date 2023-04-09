-- DROP SCHEMA `beauty_salon`;
CREATE SCHEMA `beauty_salon`
    DEFAULT CHARACTER SET utf8
    COLLATE utf8_unicode_ci;
    
USE `beauty_salon`;
    
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
    id            BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL,
	email         VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    date_created  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_user_id PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
    id 	 		BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL,
    role_type 	VARCHAR(255),
    CONSTRAINT pk_role_id PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
    id_user BIGINT(20) UNSIGNED NOT NULL,
    id_role BIGINT(20) UNSIGNED NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (id_user, id_role)
);

DROP TABLE IF EXISTS `workdays`;
CREATE TABLE IF NOT EXISTS `workdays` (
	id 			BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL,
    id_master 	BIGINT(20) UNSIGNED NOT NULL,
    work_start 	TIMESTAMP NOT NULL,
    work_finish TIMESTAMP NOT NULL,
	CONSTRAINT pk_workdays PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `appointments`;
CREATE TABLE IF NOT EXISTS `appointments` (
	id 					BIGINT(20) UNSIGNED AUTO_INCREMENT NOT NULL,
    id_workday 			BIGINT(20) UNSIGNED NOT NULL,
    id_customer 		BIGINT(20) UNSIGNED NOT NULL,
    appointments_start 	TIMESTAMP NOT NULL,
    appointments_finish TIMESTAMP NOT NULL,
    feedback 			VARCHAR(255), 
    CONSTRAINT pk_appointments PRIMARY KEY (id)
);


-- !!!
-- CONSTRAINT UNIQUE FIELDS
-- !!!

ALTER TABLE users ADD CONSTRAINT
    uc_email UNIQUE(email);

ALTER TABLE role ADD CONSTRAINT
    uc_role UNIQUE(role_type);

ALTER TABLE user_role ADD CONSTRAINT
	uc_user_role UNIQUE(id_user, id_role);

-- !!!
-- CONSTRAINT RELATIONSHIPS
-- !!!

ALTER TABLE user_role ADD CONSTRAINT
    fk_user_role_users FOREIGN KEY (id_user)
    REFERENCES users(id);

ALTER TABLE user_role ADD CONSTRAINT
    fk_user_role_role FOREIGN KEY (id_role)
    REFERENCES role(id);

ALTER TABLE workdays ADD CONSTRAINT
	fk_workdays_users FOREIGN KEY (id_master)
    REFERENCES users(id);
    
ALTER TABLE appointments ADD CONSTRAINT
	fk_appointments_workdays FOREIGN KEY (id_workday)
    REFERENCES workdays(id);

ALTER TABLE appointments ADD CONSTRAINT
	fk_appointments_users FOREIGN KEY (id_customer)
    REFERENCES users(id);


-- !!!
-- CREATE DATA
-- !!!
INSERT INTO role (role_type) VALUES("user");
INSERT INTO role (role_type) VALUES("master");
INSERT INTO role (role_type) VALUES("admin");


