CREATE SEQUENCE categoria_id_seq START 1
INCREMENT 1
MINVALUE 1;

CREATE TABLE categoria (
	codigo INT NOT NULL DEFAULT nextval('categoria_id_seq'),
	nome VARCHAR(50) NOT NULL,
	-- PRIMARY KEY(codigo)
	CONSTRAINT pk_categoria PRIMARY KEY (codigo)
);

INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Supermercado');
INSERT INTO categoria (nome) values ('Farmácia');
INSERT INTO categoria (nome) values ('Outros');