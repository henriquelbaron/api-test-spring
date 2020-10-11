insert into usuario(id,login,senha,nome,administrador) values (seq_usuario.NEXTVAL, 'convidado', 'manager', 'Usuário convidado', false);
insert into usuario(id,login,senha,nome,administrador) values (seq_usuario.NEXTVAL, 'admin', 'suporte', 'Gestor', true);
insert into token(id,token,login,expiracao,administrador) values (seq_token.NEXTVAL, 'SDFLHJKLASFKAWEFILUUJFSUESFGHJESJ', 'admin', now () - INTERVAL 10 DAY, true);
insert into pais(id,nome,sigla,gentilico) values (seq_pais.NEXTVAL, 'Brasil', 'BR', 'Brasileiro');
insert into pais(id,nome,sigla,gentilico) values (seq_pais.NEXTVAL, 'Argentina', 'AR', 'Argentino');
insert into pais(id,nome,sigla,gentilico) values (seq_pais.NEXTVAL, 'Alemanha', 'AL', 'Alemão');