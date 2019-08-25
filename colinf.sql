CREATE TABLE  tarefa  (
	 tar_codigo 	INTEGER NOT NULL PRIMARY KEY auto_increment,
	 tar_descricao 	VARCHAR ( 40 ) NOT NULL,
	 tar_tipo 	VARCHAR ( 1 ),
	 tar_status 	VARCHAR ( 1 ),
	 rea_codigo 	INTEGER,
	 data_criacao 	timestamp NOT NULL,
	 data_termino 	timestamp,
	CONSTRAINT  fk_rea_codigo  FOREIGN KEY( rea_codigo ) REFERENCES  realizada ( rea_codigo )
);
CREATE TABLE  realizada  (
	 rea_codigo 	INTEGER NOT NULL PRIMARY KEY auto_increment,
	 num_lab 	VARCHAR ( 2 ),
	 equi_codigo 	VARCHAR ( 8 ),
	CONSTRAINT  fk_equi_codigo  FOREIGN KEY( equi_codigo ) REFERENCES  equipamento ( equi_codigo ),
	CONSTRAINT  fk_num_lab  FOREIGN KEY( num_lab ) REFERENCES  laboratorio ( num_lab )
);
CREATE TABLE  laboratorio  (
	 num_lab 	varchar ( 2 ) NOT NULL,
	 qnt_maq 	integer,
	CONSTRAINT  pk_num_lab  PRIMARY KEY( num_lab )
);
CREATE TABLE funcionario  (
	 fun_matricula 	varchar ( 13 ) NOT NULL,
	 fun_nome 	varchar ( 40 ) NOT NULL,
	 fun_cargo 	varchar ( 3 ),
	 fun_mail 	varchar ( 40 ),
	 fun_telefone 	varchar ( 11 ),
	 fun_senha 	VARCHAR ( 16 ) NOT NULL,
	CONSTRAINT  pk_funcionario  PRIMARY KEY( fun_matricula )
);
CREATE TABLE  faz  (
	 faz_codigo 	INTEGER NOT NULL PRIMARY KEY auto_increment,
	 tar_codigo 	INTEGER NOT NULL,
	 fun_criacao 	VARCHAR ( 13 ) NOT NULL,
	 fun_termino 	VARCHAR ( 13 ),
	CONSTRAINT  fk_fun_criacao  FOREIGN KEY( fun_criacao ) REFERENCES  funcionario ( fun_matricula ),
	CONSTRAINT  fk_tar_codigo  FOREIGN KEY( tar_codigo ) REFERENCES  tarefa ( tar_codigo ),
	CONSTRAINT  fun_termino  FOREIGN KEY( fun_termino ) REFERENCES  funcionario ( fun_matricula )
);
CREATE TABLE equipamento  (
	 equi_codigo 	VARCHAR ( 8 ) NOT NULL,
	 equi_descricao 	VARCHAR ( 20 ) NOT NULL,
	 equi_marca 	VARCHAR ( 20 ) NOT NULL,
	 equi_tipo 	VARCHAR ( 1 ),
	 equi_status 	VARCHAR ( 1 ),
	 num_lab 	VARCHAR ( 2 ) NOT NULL,
	CONSTRAINT  fk_num_lab  FOREIGN KEY( num_lab ) REFERENCES  laboratorio ( num_lab ) ON DELETE CASCADE,
	CONSTRAINT  pk_equipamento  PRIMARY KEY( equi_codigo )
);

INSERT INTO  tarefa  (tar_codigo,tar_descricao,tar_tipo,tar_status,rea_codigo,data_criacao,data_termino) VALUES (1,'A','D','P',NULL,'2000-01-01 00:00:00',''),
 (2,'A','D','P',NULL,'2001-01-01 00:00:00',NULL);
INSERT INTO  funcionario  (fun_matricula,fun_nome,fun_cargo,fun_mail,fun_telefone,fun_senha) VALUES ('vrau','vrau','coo','vrau','vrau','vrau'),
 ('vrau1','vrau1','alu','vrau1','vrau1','vrau1');

CREATE VIEW main AS select data_criacao DESDE, tar_descricao DESCRIÇÃO from tarefa where (tar_tipo = "D" and tar_status = "P");
CREATE VIEW login AS SELECT fun_matricula as matricula, fun_senha as senha,fun_cargo as cargo from funcionario;
