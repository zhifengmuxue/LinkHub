create table category(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

create table site(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE url_mapping (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 original_url VARCHAR(2048) NOT NULL,
 short_code VARCHAR(10) NOT NULL UNIQUE,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);