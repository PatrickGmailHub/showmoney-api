CREATE SEQUENCE pessoa_id_seq START 1
INCREMENT 1
MINVALUE 1;

CREATE TABLE pessoa (
	id INT NOT NULL DEFAULT nextval('pessoa_id_seq'),
	nome VARCHAR(60) NOT NULL,
	ativo BOOLEAN DEFAULT true,
	CONSTRAINT pk_pessoa PRIMARY KEY (id)
);

--INSERT INTO categoria (nome) values ('Lazer');
