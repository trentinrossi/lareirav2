create table lareira (
    id bigint not null auto_increment,
    bairro varchar(255),
    cep varchar(255),
    cidade varchar(255),
    endereco varchar(255),
    estado varchar(255),
    nome varchar(255),
    telefone varchar(255),
    primary key (id)
) engine=MyISAM;

INSERT INTO lareira (bairro, cep, cidade, endereco, estado, nome, telefone) VALUES ('São Cristóvão', '85.806-250', 'Cascavel', 'Av. Brasil', 'PR', 'Lareira São Cristóvão', '(45) 3326-0000');

create table casal (
    id bigint not null auto_increment,
    data_uniao date,
    fone_fixo varchar(255),
    foto longblob,
    memorando varchar(255),
    numero_ficha integer,
    casal_padrinho_id bigint,
    id_endereco bigint,
    id_esposa bigint,
    id_lareira bigint,
    id_marido bigint,
    id_tipo_uniao bigint,
    primary key (id)
) engine=MyISAM;

create table endereco (
    id bigint not null auto_increment,
    bairro varchar(255),
    cep varchar(255),
    cidade varchar(255),
    estado varchar(255),
    numero varchar(255),
    rua varchar(255),
    primary key (id)
) engine=MyISAM;

create table filho (
    id bigint not null auto_increment,
    data_nascimento date,
    nome varchar(255),
    sexo varchar(255),
    id_casal bigint not null,
    primary key (id)
) engine=MyISAM;

create table pessoa_fisica (
    id bigint not null auto_increment,
    data_nascimento date,
    email varchar(255),
    nome varchar(255),
    problema_saude varchar(255),
    profissao varchar(255),
    sobrenome varchar(255),
    tel_celular varchar(255),
    primary key (id)
) engine=MyISAM;

create table tipo_uniao (
    id bigint not null auto_increment,
    descricao varchar(255),
    nome varchar(255),
    primary key (id)
) engine=MyISAM;

alter table casal add constraint FK_endereco              foreign key (id_endereco)   references endereco (id);
alter table casal add constraint FK_pessoa_fisica_esposta foreign key (id_esposa)     references pessoa_fisica (id);
alter table casal add constraint FK_lareira               foreign key (id_lareira)    references lareira (id);
alter table casal add constraint FK_pessoa_fisica_marido  foreign key (id_marido)     references pessoa_fisica (id);
alter table casal add constraint FK_tipo_uniao            foreign key (id_tipo_uniao) references tipo_uniao (id);
alter table filho add constraint FK_casal                 foreign key (id_casal)      references casal (id);