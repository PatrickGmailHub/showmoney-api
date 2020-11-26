CREATE SEQUENCE usuario_id_seq START 1
INCREMENT 1
MINVALUE 1;

CREATE TABLE usuario (
	id INT PRIMARY KEY DEFAULT nextval('usuario_id_seq'),
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
);

CREATE SEQUENCE permissao_id_seq START 1
INCREMENT 1
MINVALUE 1;

CREATE TABLE permissao (
	id INT PRIMARY KEY DEFAULT nextval('permissao_id_seq'),
	descricao VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_permissao (
	id_usuario INT NOT NULL,
	id_permissao INT NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id)
);

INSERT INTO usuario (nome, email, senha) values ('Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (nome, email, senha) values ('Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permissao (descricao) values ('ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (descricao) values ('ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissao (descricao) values ('ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao (descricao) values ('ROLE_REMOVER_PESSOA');
INSERT INTO permissao (descricao) values ('ROLE_PESQUISAR_PESSOA');

INSERT INTO permissao (descricao) values ('ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao (descricao) values ('ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao (descricao) values ('ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 6);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 7);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 8);

-- maria
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 8);
