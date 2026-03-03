CREATE TABLE processes (
    id VARCHAR(255) NOT NULL,
    number_cnj VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    client_name VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    court VARCHAR(255),
    district VARCHAR(255),
    CONSTRAINT pk_processes PRIMARY KEY (id)
);

CREATE TABLE processes_users (
    process_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_processes_users PRIMARY KEY (process_id, user_id),
    CONSTRAINT fk_processes_users_process FOREIGN KEY (process_id) REFERENCES processes(id),
    CONSTRAINT fk_processes_users_user FOREIGN KEY (user_id) REFERENCES users(id)
);
