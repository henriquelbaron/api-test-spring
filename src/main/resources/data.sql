insert into usuario(id,login,senha,nome,administrador) values (SEQ_USUARIO.NEXTVAL, 'convidado', 'manager', 'Usuário convidado', false);
insert into usuario(id,login,senha,nome,administrador) values (SEQ_USUARIO.NEXTVAL, 'admin', 'suporte', 'Gestor', true);
insert into token(id,token,login,expiracao,administrador) values (SEQ_TOKEN.NEXTVAL, 'SDFLHJKLASFKAWEFILUUJFSUESFGHJESJ', 'admin', now () - INTERVAL 10 DAY, true);
insert into pais(id,nome,sigla,gentilico) values (SEQ_PAIS.NEXTVAL, 'Brasil', 'BR', 'Brasileiro');
insert into pais(id,nome,sigla,gentilico) values (SEQ_PAIS.NEXTVAL, 'Argentina', 'AR', 'Argentino');
insert into pais(id,nome,sigla,gentilico) values (SEQ_PAIS.NEXTVAL, 'Alemanha', 'AL', 'Alemão');