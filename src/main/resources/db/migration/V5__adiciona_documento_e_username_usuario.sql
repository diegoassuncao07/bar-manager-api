ALTER TABLE usuario ADD COLUMN documento VARCHAR(11) NOT NULL;
ALTER TABLE usuario ADD CONSTRAINT uk_usuario_documento UNIQUE (documento);

ALTER TABLE usuario ADD COLUMN username VARCHAR(20) NOT NULL;
ALTER TABLE usuario ADD CONSTRAINT uk_usuario_username UNIQUE (username);