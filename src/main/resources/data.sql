INSERT INTO public.cliente (data_nascimento,data_atualizacao,data_criacao,bairro,cep,cidade,complemento,cpf,logradouro,nome,numero,tipo,uf)
VALUES
    ('1985-05-15','2025-04-10 08:50:21.268092','2025-04-10 08:50:21.268141','Centro','06240155','São Paulo','Apto 45','37606232806','Rua das Flores','Carlos Roberto Evangelista Silva Reis','123','COMUM','SP');

INSERT INTO public.conta (saldo,taxa_manutencao,taxa_rendimento,cliente_id,data_atualizacao,data_criacao,ultima_movimentacao,dtype,tipo_conta)
VALUES
   (950.00,NULL,0.05,1,'2025-04-10 08:50:54.853','2025-04-10 08:50:38.468','2025-04-10 08:50:54.846','ContaPoupanca','POUPANCA'),
   (950.00,12.00,NULL,1,'2025-04-10 08:51:04.766','2025-04-10 08:50:43.718','2025-04-10 08:51:04.764','ContaCorrente','CORRENTE');

INSERT INTO public.transacao (conta_numero,valor,"data",descricao,tipo)
VALUES
    (1,950.00,'2025-04-10 08:50:54.847','DEPOSITO_RECEBIDO','DEPOSITO'),
    (2,950.00,'2025-04-10 08:51:04.764','DEPOSITO_RECEBIDO','DEPOSITO');

