CREATE TABLE acessos (
    id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    data_hora_acesso TIMESTAMP NOT NULL,
    pagina_visitada VARCHAR(255) NOT NULL,
    CONSTRAINT pk_acessos PRIMARY KEY (id)
);