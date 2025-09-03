CREATE TABLE employee(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(255),
    salary DECIMAL(10, 2) NOT NULL,
    company_id BIGINT,
    PRIMARY KEY (id)

);

CREATE TABLE company(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);