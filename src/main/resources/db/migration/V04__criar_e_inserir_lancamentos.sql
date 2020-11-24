CREATE SEQUENCE lancamento_id_seq START 1
INCREMENT 1
MINVALUE 1;

CREATE TABLE lancamento (
	id INT NOT NULL DEFAULT nextval('lancamento_id_seq'),
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_categoria INT NOT NULL,
	id_pessoa INT NOT NULL,
	CONSTRAINT pk_lancamento PRIMARY KEY (id),
	CONSTRAINT fk_lancamento_categoria
    FOREIGN KEY (codigo_categoria) 
    REFERENCES categoria (codigo),
 	CONSTRAINT fk_lancamento_pessoa
		FOREIGN KEY (id_pessoa) 
    REFERENCES pessoa (id)
);

--FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
--FOREIGN KEY (id_pessoa) REFERENCES pessoa(id),	

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'DESPESA', 2, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Top Club', '2017-06-10', null, 120, null, 'RECEITA', 3, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'RECEITA', 3, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('DMAE', '2017-06-10', null, 200.30, null, 'DESPESA', 3, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'RECEITA', 4, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Bahamas', '2017-06-10', null, 500, null, 'RECEITA', 1, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'DESPESA', 4, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'RECEITA', 5, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Café', '2017-06-10', null, 8.32, null, 'DESPESA', 1, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'DESPESA', 5, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'DESPESA', 4, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'DESPESA', 4, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, id_pessoa) values ('Lanche', '2017-06-10', null, 10.20, null, 'DESPESA', 4, 3);
