/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     05/06/2018 10:32:10                          */
/*==============================================================*/


drop table if exists ARRIENDO;

drop table if exists AUTOR;

drop table if exists AUTOR_LIBRO;

drop table if exists BOLETA;

drop table if exists BOLETAARRIENDO;

drop table if exists CATEGORIA;

drop table if exists CATEGORIA_LIBRO;

drop table if exists CLIENTE;

drop table if exists COMPRA;

drop table if exists COMUNA;

drop table if exists CORREOCLIENTE;

drop table if exists CORREOTRABAJADOR;

drop table if exists DIRECCIONCLIENTE;

drop table if exists DIRECCIONTRABAJADOR;

drop table if exists DISTRIBUIDOR;

drop table if exists EDITORIAL;

drop table if exists ESTADO;

drop table if exists FACTURA;

drop table if exists IDIOMA;

drop table if exists IDIOMA_LIBRO;

drop table if exists LIBRO;

drop table if exists METODOPAGO;

drop table if exists MULTA;

drop table if exists TELEFONOCLIENTE;

drop table if exists TELEFONOTRABAJADOR;

drop table if exists TRABAJADOR;

drop table if exists VENTA;

/*==============================================================*/
/* Table: ARRIENDO                                              */
/*==============================================================*/
create table ARRIENDO
(
   ID_ARRIENDO          int not null AUTO_INCREMENT,
   ID_LIBRO             int,
   FECHA_ARRIENDO       date,
   FECHA_DEVOLUCION     date,
   FECHA_ENTREGA_REAL   date,
   primary key (ID_ARRIENDO)
);

/*==============================================================*/
/* Table: AUTOR                                                 */
/*==============================================================*/
create table AUTOR
(
   ID_AUTOR             int not null AUTO_INCREMENT,
   NOM_AUTOR            varchar(20),
   APE_PAT_AUTOR        varchar(20),
   APE_MAT_AUTOR        varchar(20),
   ESTADO_AUTOR			smallint,
   primary key (ID_AUTOR)
);

/*==============================================================*/
/* Table: AUTOR_LIBRO                                           */
/*==============================================================*/
create table AUTOR_LIBRO
(
   ID_AUTOR_LIBRO       int not null AUTO_INCREMENT,
   ID_LIBRO             int,
   ID_AUTOR             int,
   primary key (ID_AUTOR_LIBRO)
);

/*==============================================================*/
/* Table: BOLETA                                                */
/*==============================================================*/
create table BOLETA
(
   FOLIO_BOLETA         int not null AUTO_INCREMENT,
   ID_CLIENTE           int,
   ID_TRABAJADOR        int,
   ID_METODO_PAGO       int not null,
   PRECIO_NETO_BOLETA   float,
   PRECIO_CON_IVA_BOLETA float,
   COSTO_IVA_BOLETA     float,
   FECHA_VENTA          date,
   HORA_VENTA           time,
   primary key (FOLIO_BOLETA)
);

/*==============================================================*/
/* Table: BOLETAARRIENDO                                        */
/*==============================================================*/
create table BOLETAARRIENDO
(
   FOLIOARRIENDO_BOLETA_ARRIENDO int not null AUTO_INCREMENT,
   ID_MULTA             int,
   ID_CLIENTE           int,
   ID_TRABAJADOR        int,
   ID_ARRIENDO          int,
   ID_METODO_PAGO       int,
   PRECIO_NETO_BOLETA_  float,
   PRECIO_CON_IVA_BOLETA_ARRIENDO float,
   COSTO_IVA_BOLETA_ARRIENDO float,
   FECHA_BOLETA_ARRIENDO date,
   primary key (FOLIOARRIENDO_BOLETA_ARRIENDO)
);

/*==============================================================*/
/* Table: CATEGORIA                                             */
/*==============================================================*/
create table CATEGORIA
(
   ID_CATEGORIA         int not null AUTO_INCREMENT,
   CATEGORIA            varchar(30),
   primary key (ID_CATEGORIA)
);

/*==============================================================*/
/* Table: CATEGORIA_LIBRO                                       */
/*==============================================================*/
create table CATEGORIA_LIBRO
(
   ID_CATEGORIA_LIBRO   int not null AUTO_INCREMENT,
   ID_LIBRO             int,
   ID_CATEGORIA         int,
   primary key (ID_CATEGORIA_LIBRO)
);

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE
(
   ID_CLIENTE           int not null AUTO_INCREMENT,
   RUT_CLIENTE          varchar(10) not null,
   NOM_CLIENTE          varchar(20),
   APE_PAT_CLIENTE      varchar(20),
   APE_MAT_CLIENTE      varchar(20),
   FECHA_NAC_CLIENTE    date,
   primary key (ID_CLIENTE)
);

/*==============================================================*/
/* Table: COMPRA                                                */
/*==============================================================*/
create table COMPRA
(
   ID_COMPRA            int not null AUTO_INCREMENT,
   ID_LIBRO_B           int not null,
   FOLIO_FACTURA        int not null,
   PRECIO_COMPRA        float,
   primary key (ID_COMPRA)
);

/*==============================================================*/
/* Table: COMUNA                                                */
/*==============================================================*/
create table COMUNA
(
   ID_COMUNA            int not null AUTO_INCREMENT,
   NOMBRE_COMUNA        varchar(60),
   primary key (ID_COMUNA)
);

/*==============================================================*/
/* Table: CORREOCLIENTE                                         */
/*==============================================================*/
create table CORREOCLIENTE
(
   ID_CORREO_CLIENTE    int not null AUTO_INCREMENT,
   ID_CLIENTE           int,
   CORREO_CLIENTE       varchar(30),
   primary key (ID_CORREO_CLIENTE)
);

/*==============================================================*/
/* Table: CORREOTRABAJADOR                                      */
/*==============================================================*/
create table CORREOTRABAJADOR
(
   ID_CORREO_TRABAJADOR int not null AUTO_INCREMENT,
   ID_TRABAJADOR        int,
   CORREO_TRABAJADOR    varchar(30),
   primary key (ID_CORREO_TRABAJADOR)
);

/*==============================================================*/
/* Table: DIRECCIONCLIENTE                                      */
/*==============================================================*/
create table DIRECCIONCLIENTE
(
   ID_DIRECCION_CLIENTE int not null AUTO_INCREMENT,
   ID_COMUNA            int not null,
   ID_CLIENTE           int,
   DIRECCION_CLIENTE    varchar(60),
   primary key (ID_DIRECCION_CLIENTE)
);

/*==============================================================*/
/* Table: DIRECCIONTRABAJADOR                                   */
/*==============================================================*/
create table DIRECCIONTRABAJADOR
(
   ID_DIRECCION_TRABAJADOR int not null AUTO_INCREMENT,
   ID_COMUNA            int not null,
   ID_TRABAJADOR        int,
   DIRECCION            varchar(60),
   primary key (ID_DIRECCION_TRABAJADOR)
);

/*==============================================================*/
/* Table: DISTRIBUIDOR                                          */
/*==============================================================*/
create table DISTRIBUIDOR
(
   ID_DISTRIBUIDOR      int not null AUTO_INCREMENT,
   RUT_DISTRIBUIDOR     varchar(10),
   NOMBRE_DISTRIBUIDOR  varchar(40),
   DIRECCION_DISTRIBUIDOR varchar(60),
   TELEFONO_DISTRIBUIDOR int,
   ANIO_INICIO          smallint,
   ESTADO_DISTRIBUIDOR	smallint,
   unique (RUT_DISTRIBUIDOR),
   primary key (ID_DISTRIBUIDOR)
);

/*==============================================================*/
/* Table: EDITORIAL                                             */
/*==============================================================*/
create table EDITORIAL
(
   ID_EDITORIAL         int not null AUTO_INCREMENT,
   RUT_EDITORIAL        varchar(10) not null,
   NOM_EDITORIAL        varchar(40),
   DIRECCION_EDITORIAL  varchar(60),
   TELEFONO_EDITORIAL   int,
   CORREO_EDITORIAL     varchar(60),
   ESTADO_EDITORIAL		smallint,
   primary key (ID_EDITORIAL)
);

/*==============================================================*/
/* Table: ESTADO                                                */
/*==============================================================*/
create table ESTADO
(
   ID_ESTADO            int not null AUTO_INCREMENT,
   DESCRIPCION_ESTADO   varchar(30),
   primary key (ID_ESTADO)
);

/*==============================================================*/
/* Table: FACTURA                                               */
/*==============================================================*/
create table FACTURA
(
   FOLIO_FACTURA        int not null,
   ID_DISTRIBUIDOR      int not null,
   ID_METODO_PAGO       int not null,
   PRECIO_NETO_FACTURA  float,
   PRECIO_IVA_FACTURA   float,
   COSTO_IVA_FACTURA    float,
   FECHA_COMPRA         varchar(20),
   HORA_COMPRA          varchar(5),
   ESTADO_FACTURA		smallint,
   primary key (FOLIO_FACTURA)
);

/*==============================================================*/
/* Table: IDIOMA                                                */
/*==============================================================*/
create table IDIOMA
(
   ID_IDIOMA            int not null AUTO_INCREMENT,
   IDIOMA               varchar(30),
   primary key (ID_IDIOMA)
);

/*==============================================================*/
/* Table: IDIOMA_LIBRO                                          */
/*==============================================================*/
create table IDIOMA_LIBRO
(
   ID_IDIOMA_LIBRO      int not null AUTO_INCREMENT,
   ID_LIBRO             int,
   ID_IDIOMA            int,
   primary key (ID_IDIOMA_LIBRO)
);

/*==============================================================*/
/* Table: LIBRO                                                 */
/*==============================================================*/
create table LIBRO
(
   ID_LIBRO             int not null AUTO_INCREMENT,
   ID_EDITORIAL         int,
   ISBN                 varchar(15),
   TITULO               varchar(50),
   NUMERO_PAGINAS       smallint,
   PRECIO_REFERENCIA	float,
   ANIO                 smallint,   
   primary key (ID_LIBRO)
);

/*==============================================================*/
/* Table: LIBRO_BIBLIOTECA                                      */
/*==============================================================*/
create table LIBRO_BIBLIOTECA
(
   ID_LIBRO_B             	int not null AUTO_INCREMENT,
   ID_LIBRO					int not null,
   ID_ESTADO            	int not null,
   NUMERO_SERIE         	varchar(15),
   TITULO_B               	varchar(50),
   PRECIO			    	float,
   ESTADO_LIBRO				smallint,
   unique (NUMERO_SERIE),
   primary key (ID_LIBRO_B)
);

/*==============================================================*/
/* Table: METODOPAGO                                            */
/*==============================================================*/
create table METODOPAGO
(
   ID_METODO_PAGO       int not null AUTO_INCREMENT,
   METODO_PAGO          varchar(30),
   primary key (ID_METODO_PAGO)
);

/*==============================================================*/
/* Table: MULTA                                                 */
/*==============================================================*/
create table MULTA
(
   ID_MULTA             int not null AUTO_INCREMENT,
   MONTO_MULTA          int,
   DIAS_RESTRASO        varchar(20),
   primary key (ID_MULTA)
);

/*==============================================================*/
/* Table: TELEFONOCLIENTE                                       */
/*==============================================================*/
create table TELEFONOCLIENTE
(
   ID_TELEFONO_CLIENTE  int not null AUTO_INCREMENT,
   ID_CLIENTE           int,
   NUMERO_TELEFONO_CLIENTE varchar(30),
   primary key (ID_TELEFONO_CLIENTE)
);

/*==============================================================*/
/* Table: TELEFONOTRABAJADOR                                    */
/*==============================================================*/
create table TELEFONOTRABAJADOR
(
   ID_TELEFONO_TRABAJADOR int not null AUTO_INCREMENT,
   ID_TRABAJADOR        int,
   NUMERO_TELEFONO_TRABAJADOR varchar(30),
   primary key (ID_TELEFONO_TRABAJADOR)
);

/*==============================================================*/
/* Table: TRABAJADOR                                            */
/*==============================================================*/
create table TRABAJADOR
(
   ID_TRABAJADOR        int not null AUTO_INCREMENT,
   RUT_TRABAJADOR       varchar(10) not null,
   NOM_TRABAJADOR       varchar(20),
   APE_PAT_TRABAJADOR   varchar(20),
   APE_MAT_TRABAJADOR   varchar(20),
   FECHA_CONTRATO       date,
   primary key (ID_TRABAJADOR)
);

/*==============================================================*/
/* Table: VENTA                                                 */
/*==============================================================*/
create table VENTA
(
   ID_VENTA             int not null AUTO_INCREMENT,
   ID_LIBRO             int not null,
   FOLIO_BOLETA         int not null,
   PRECIO_VENTA         float,
   primary key (ID_VENTA)
);

alter table ARRIENDO add constraint FK_INTEGRA3 foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;

alter table AUTOR_LIBRO add constraint FK_ESCRIBE foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;

alter table AUTOR_LIBRO add constraint FK_ESCRIBE2 foreign key (ID_AUTOR)
      references AUTOR (ID_AUTOR) on delete restrict on update restrict;

alter table BOLETA add constraint FK_COMPRA foreign key (ID_CLIENTE)
      references CLIENTE (ID_CLIENTE) on delete restrict on update restrict;

alter table BOLETA add constraint FK_GENERA foreign key (ID_TRABAJADOR)
      references TRABAJADOR (ID_TRABAJADOR) on delete restrict on update restrict;

alter table BOLETA add constraint FK_PAGA2 foreign key (ID_METODO_PAGO)
      references METODOPAGO (ID_METODO_PAGO) on delete restrict on update restrict;

alter table BOLETAARRIENDO add constraint FK_APLICA foreign key (ID_MULTA)
      references MULTA (ID_MULTA) on delete restrict on update restrict;

alter table BOLETAARRIENDO add constraint FK_ARRIENDA foreign key (ID_CLIENTE)
      references CLIENTE (ID_CLIENTE) on delete restrict on update restrict;

alter table BOLETAARRIENDO add constraint FK_GENERA2 foreign key (ID_TRABAJADOR)
      references TRABAJADOR (ID_TRABAJADOR) on delete restrict on update restrict;

alter table BOLETAARRIENDO add constraint FK_PAGA3 foreign key (ID_METODO_PAGO)
      references METODOPAGO (ID_METODO_PAGO) on delete restrict on update restrict;

alter table BOLETAARRIENDO add constraint FK_REGISTRA4 foreign key (ID_ARRIENDO)
      references ARRIENDO (ID_ARRIENDO) on delete restrict on update restrict;

alter table CATEGORIA_LIBRO add constraint FK_POSEE foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;

alter table CATEGORIA_LIBRO add constraint FK_POSEE2 foreign key (ID_CATEGORIA)
      references CATEGORIA (ID_CATEGORIA) on delete restrict on update restrict;
	  
alter table LIBRO_BIBLIOTECA add constraint FK_IGUAL foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;
	  
/*
alter table COMPRA add constraint FK_INTEGRA2 foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;*/
	  
alter table COMPRA add constraint FK_INTEGRA2 foreign key (ID_LIBRO_B)
      references LIBRO_BIBLIOTECA (ID_LIBRO_B) on delete restrict on update restrict;

alter table COMPRA add constraint FK_REALIZA foreign key (FOLIO_FACTURA)
      references FACTURA (FOLIO_FACTURA) on delete restrict on update restrict;

alter table CORREOCLIENTE add constraint FK_REGISTRA foreign key (ID_CLIENTE)
      references CLIENTE (ID_CLIENTE) on delete restrict on update restrict;

alter table CORREOTRABAJADOR add constraint FK_REGISTRA2 foreign key (ID_TRABAJADOR)
      references TRABAJADOR (ID_TRABAJADOR) on delete restrict on update restrict;

alter table DIRECCIONCLIENTE add constraint FK_PERTENECE foreign key (ID_COMUNA)
      references COMUNA (ID_COMUNA) on delete restrict on update restrict;

alter table DIRECCIONCLIENTE add constraint FK_RESIDE foreign key (ID_CLIENTE)
      references CLIENTE (ID_CLIENTE) on delete restrict on update restrict;

alter table DIRECCIONTRABAJADOR add constraint FK_PERTENECE2 foreign key (ID_COMUNA)
      references COMUNA (ID_COMUNA) on delete restrict on update restrict;

alter table DIRECCIONTRABAJADOR add constraint FK_RESIDE2 foreign key (ID_TRABAJADOR)
      references TRABAJADOR (ID_TRABAJADOR) on delete restrict on update restrict;

alter table FACTURA add constraint FK_ASOCIA foreign key (ID_DISTRIBUIDOR)
      references DISTRIBUIDOR (ID_DISTRIBUIDOR) on delete restrict on update restrict;

alter table FACTURA add constraint FK_PAGA foreign key (ID_METODO_PAGO)
      references METODOPAGO (ID_METODO_PAGO) on delete restrict on update restrict;

alter table IDIOMA_LIBRO add constraint FK_CONTIENE foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;

alter table IDIOMA_LIBRO add constraint FK_CONTIENE2 foreign key (ID_IDIOMA)
      references IDIOMA (ID_IDIOMA) on delete restrict on update restrict;

alter table LIBRO add constraint FK_EDITA foreign key (ID_EDITORIAL)
      references EDITORIAL (ID_EDITORIAL) on delete restrict on update restrict;

alter table LIBRO_BIBLIOTECA add constraint FK_PRESENTA foreign key (ID_ESTADO)
      references ESTADO (ID_ESTADO) on delete restrict on update restrict;

alter table TELEFONOCLIENTE add constraint FK_REGISTRA1 foreign key (ID_CLIENTE)
      references CLIENTE (ID_CLIENTE) on delete restrict on update restrict;

alter table TELEFONOTRABAJADOR add constraint FK_REGISTRA3 foreign key (ID_TRABAJADOR)
      references TRABAJADOR (ID_TRABAJADOR) on delete restrict on update restrict;

alter table VENTA add constraint FK_INTEGRA foreign key (ID_LIBRO)
      references LIBRO (ID_LIBRO) on delete restrict on update restrict;

alter table VENTA add constraint FK_REALIZA2 foreign key (FOLIO_BOLETA)
      references BOLETA (FOLIO_BOLETA) on delete restrict on update restrict;

	  
insert into trabajador values (1,'5-6','CAROLE','ESCUDERO','CARRIÓN','2010-11-20');
insert into trabajador values (2,'4-8','JORGE','SOTO','CASTRO','2015-03-01');
insert into trabajador values (3,'2-9','FELIPE','ROJAS','ROJAS','2012-06-10');
insert into trabajador values (4,'1-2','CARLOS','GUAJARDO','GUTIERREZ','2005-04-09');

insert into categoria values (1,'AVENTURA');
insert into categoria values (2,'CIENCIAS');
insert into categoria values (3,'FILOSOFÍA');
insert into categoria values (4,'CRÓNICA');
insert into categoria values (5,'NARRATIVA');
insert into categoria values (6,'AUTOBIOGRAFIA');

insert into estado values (1,'DISPONIBLE');
insert into estado values (2,'ARRENDADO');
insert into estado values (3,'VENDIDO');

insert into idioma values (1,'ESPAÑOL');
insert into idioma values (2,'INGLÉS');
insert into idioma values (3,'FRANCÉS');

insert into metodopago values (1,'EFECTIVO');
insert into metodopago values (2,'DÉBITO');
insert into metodopago values (3,'CRÉDITO');

insert into autor values (1,'FEDERICO', 'GARCÍA', 'LORCA',1);
insert into autor values (2,'NICANOR', 'PARRA', 'SANDOVAL',1);
insert into autor values (3,'FRANCISCO', 'COLOANE', 'CÁRDENAS',1);
insert into autor values (4,'ADONIS', 'SUBIABRE', 'TORO',1);
insert into autor values (5,'MARIO', 'BENEDETTI', 'FARRUGIA',1);
insert into autor values (6,'ISABEL', 'ALLENDE', 'LLONA',1);
insert into autor values (7,'CHARLES', 'BUKOWSKI', 'BUKOWSKI',1);

insert into distribuidor values (1,'70645789-7', 'EOS LIBROS LTDA.', 'PADRE ALONSO DE OVALLE 748', 24809800, 2009,1);
insert into distribuidor values (2,'71443459-4', 'DISTRIBUIDORA VIVA', 'CATAMARCA Nº 1680, QUINTA NORMAL', 999989010, 2013,1);
insert into distribuidor values (3,'72458796-1', 'LIBRERIA BIBLIOGRAFICA INTERNACIONAL', 'MONJITAS 308,
Santiago Centro', 226394057, 2011,1);

insert into editorial values (1,'70454579-5', 'EDITORIAL PLANETA', 'AV. 11 DE SEPTIEMBRE 2353, PROVIDENCIA', 26522910, 'INFO@PLANETADELIBROS.CL',1);
insert into editorial values (2,'72457849-2', 'ZIG-ZAG', 'LOS CONQUISTADORES 1700, PROVIDENCIA', 28107400, 'CONTACTO@ZIGZAG.CL',1);
insert into editorial values (3,'71468124-9', 'OCHO LIBROS', 'ARZOBISPO CASANOVA 36, PROVIDENCIA', 223351767, 'CONTACTO@OCHOLIBROS.CL',1);

insert into libro values (1,1,1,777,'5622335176764','ISAAC NEWTON GRANDE ENTRE LOS GRANDES',370,5990,2012);
insert into categoria_libro values(1,1,1);
insert into categoria_libro values(2,1,4);
insert into autor_libro values(1,1,4);
insert into idioma_libro values(1,1,1);
insert into idioma_libro values(2,1,2);

insert into libro values (2,2,1,888,'9789562479332','LA TREGUA',208,7990,2015);
insert into categoria_libro values(3,2,5);
insert into autor_libro values(5,2,5);
insert into idioma_libro values(3,2,1);

insert into libro values (3,3,1,123,'9788401352898','LA CASA DE LOS ESPIRITUS',512,6990,2011);
insert into categoria_libro values(4,3,6);
insert into autor_libro values(6,3,6);
insert into idioma_libro values(4,3,1);

insert into libro values (4,3,1,555,'8433920634','EL CARTERO',192,6990,2013);
insert into categoria_libro values(5,4,5);
insert into autor_libro values(7,4,7);
insert into idioma_libro values(5,4,2);