create table USER (
    USER_ID varchar(25) not null,
    PASSWORD varchar(25) not null,
    ROLES varchar(30),
    
    constraint PK_USER primary key (USER_ID)
);

create table ACCOUNT (
    USER_ID varchar(25) not null,
    NAME varchar(20) not null, 
    LEADERNAME varchar(20) not null,  
    EMAIL varchar(80) not null,
    PHONE varchar(80) not null,
    ALIPAY_ACCOUNT varchar(50) null, 

    constraint PK_ACCOUNT primary key (USER_ID)
);

create table orders (
      orderid int not null,
      userid varchar(25) not null,
      leadername varchar(20) not null,
      orderdate date not null,      
      breakfast decimal(5,2) not null,
      lunch decimal(5,2) not null,      
      dinner decimal(5,2) not null,
      drink decimal(5,2) not null,
      carfare decimal(5,2) not null,   
      other decimal(5,2) not null,  
      subprice decimal(10,2) not null,     
      totalprice decimal(10,2) null,   
      commonts varchar(200) not null, 
           
      constraint pk_orders primary key (orderid)
);

create table orderstatus (
      orderid int not null,     
      ststatus varchar(20) not null,
      
      constraint pk_orderstatus primary key (orderid)
);

CREATE TABLE sequence
(
    name               varchar(30)  not null,
    nextid             int          not null,
    constraint pk_sequence primary key (name)
);
