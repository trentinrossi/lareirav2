create table perfis (
    usuario_id bigint not null,
    perfis integer
);

create table usuario (
    id bigint not null auto_increment,
    ativo boolean,
    descricao varchar(100),
    email varchar(100),
    login varchar(255) not null,
    nome varchar(100),
    senha varchar(255),
    primary key (id)
);

alter table perfis add constraint FK_perfil_usuario foreign key (usuario_id) references usuario (id);
alter table casal add constraint FK_casal_padrinho foreign key (casal_padrinho_id) references casal (id);

INSERT INTO usuario VALUES (null, 1, 'Usuário admin', 'admin@lareira.com', 'admin', 'Admin', '$2a$10$JFjcPgiy/jvhq5vcqvfn.eoMK25cK/KCxSGBO98YRKRXKSbh/F4Ci');
INSERT INTO usuario VALUES (null, 1, 'Usuário Lareira Cascavel', 'cascavelsc@lareira.com', 'cascavelsc', 'Lareira Cascavel', '$2a$10$JFjcPgiy/jvhq5vcqvfn.eoMK25cK/KCxSGBO98YRKRXKSbh/F4Ci');
INSERT INTO perfis VALUES (1, 1);
INSERT INTO perfis VALUES (2, 2);