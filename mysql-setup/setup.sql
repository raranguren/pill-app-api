/* Create database */
DROP DATABASE IF EXISTS prod;
CREATE DATABASE prod;
USE prod;

/* Create the user that the API will use (set in application.properties) */
DROP USER IF EXISTS apiuser;
CREATE USER apiuser IDENTIFIED BY 'dbpassword';
GRANT SELECT, INSERT, UPDATE, DELETE ON prod.* TO apiuser;

CREATE TABLE User
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    email    VARCHAR(40) UNIQUE    NOT NULL,
    userName VARCHAR(20) UNIQUE    NOT NULL,
    password VARCHAR(100)          NOT NULL,
    PRIMARY KEY (id),
    INDEX (email)
);

CREATE TABLE Patient_users
(
    users_id BIGINT,
    Patient_id BIGINT,
    INDEX (users_id),
    INDEX (Patient_id)
);

CREATE TABLE Patient
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    updatedTimestamp BIGINT                NOT NULL,
    fullName         VARCHAR(40)           NOT NULL,
    notes            VARCHAR(140),
    kg               INT,
    PRIMARY KEY (id)
);

CREATE TABLE Reminder
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    type                 INT                   NOT NULL,
    patient_id           BIGINT                NOT NULL,
    updatedTimestamp     BIGINT,
    reminderTimestamp    BIGINT,
    startTimestamp       BIGINT,
    `description`        VARCHAR(40),
    appointmentTimestamp BIGINT,
    duration             BIGINT,
    `period`             BIGINT,
    dosePerIntake        VARCHAR(40),
    PRIMARY KEY (id),
    index (patient_id),
    index (type),
    index (updatedTimestamp)
);

CREATE TABLE CareAction
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    type             INT                   NOT NULL,
    patient_id       BIGINT                NOT NULL,
    plan_id          BIGINT,
    updatedTimestamp BIGINT,
    doneTimestamp    BIGINT,
    doseNote         VARCHAR(40),
    PRIMARY KEY (id),
    index (patient_id),
    index (type),
    index (doneTimestamp)
);