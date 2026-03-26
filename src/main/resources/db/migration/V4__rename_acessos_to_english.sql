-- V4__rename_acessos_to_english.sql

-- Renomear colunas
ALTER TABLE acessos RENAME COLUMN data_hora_acesso TO hour_date_access;
ALTER TABLE acessos RENAME COLUMN pagina_visitada TO visited_page;
ALTER TABLE acessos RENAME CONSTRAINT pk_acessos TO pk_access;

-- Renomear a tabela por último
ALTER TABLE acessos RENAME TO access;