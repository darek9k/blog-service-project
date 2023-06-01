insert into post (version, author, created_date_time, last_modified_date, publication_date, scope, status, text)
    values (0, 'Darek Kowalski', '2023-05-22T12:48:19','2023-05-24T12:48:19', null, 'PUBLIC', 'ACTIVE', 'POST1 z data.sql');

insert into post (version, author, created_date_time, last_modified_date, publication_date, scope, status, text)
    values (0, 'Arek Kowalski2', '2023-05-23T12:48:19','2023-05-24T12:48:19', null, 'PUBLIC', 'ACTIVE', 'POST2 z data.sql');

insert into post (version, author, created_date_time, last_modified_date, publication_date, scope, status, text)
    values (0, 'Marek Kowalski3', '2023-05-23T12:48:19','2023-05-24T12:48:19', null, 'PUBLIC', 'ACTIVE', 'POST3 z data.sql DELETED');

insert into post (version, author, created_date_time, last_modified_date, publication_date, scope, status, text)
    values (0, 'Jarek Kowalski4', '2023-05-26T12:48:19','2023-05-24T12:48:19', null, 'PUBLIC', 'DELETED', 'POST4 z data.sql DELETED');

insert into post (version, author, created_date_time, last_modified_date, publication_date, scope, status, text)
     values (0, 'Darek Kowalski5', '2023-05-29T12:48:19','2023-05-24T12:48:19', null, 'PUBLIC', 'DELETED', 'POST5 z data.sql DELETED');

insert into invoice (BUYER,CREATED_DATE_TIME,LAST_MODIFIED_DATE,PAYMENT_DATE,SELLER,STATUS,VERSION)
    values ('Buyer1', '2023-05-23T12:48:19', '2023-05-23T12:48:19', '2023-06-28', 'Seller1', 'ACTIVE', 0 );

insert into invoice (BUYER,CREATED_DATE_TIME,LAST_MODIFIED_DATE,PAYMENT_DATE,SELLER,STATUS,VERSION)
    values ('Buyer2', '2023-05-23T12:48:19', '2023-05-23T12:48:19', '2023-06-24', 'Seller2', 'ACTIVE', 0 );

insert into invoice (BUYER,CREATED_DATE_TIME,LAST_MODIFIED_DATE,PAYMENT_DATE,SELLER,STATUS,VERSION)
    values ('Buyer3', '2023-05-23T12:48:19', '2023-05-23T12:48:19', '2023-06-21', 'Jarek', 'ACTIVE', 0 );

insert into invoice (BUYER,CREATED_DATE_TIME,LAST_MODIFIED_DATE,PAYMENT_DATE,SELLER,STATUS,VERSION)
    values ('Buyer4', '2023-05-23T12:48:19', '2023-05-23T12:48:19', '2023-06-20', 'seller4', 'ACTIVE', 0 );

insert into invoice (BUYER,CREATED_DATE_TIME,LAST_MODIFIED_DATE,PAYMENT_DATE,SELLER,STATUS,VERSION)
        values ('Buyer3', '2023-05-23T12:48:19', '2023-05-23T12:48:19', '2023-06-20', 'Darek', 'DRAFT', 0 );

insert into invoice (BUYER,CREATED_DATE_TIME,LAST_MODIFIED_DATE,PAYMENT_DATE,SELLER,STATUS,VERSION)
        values ('Buyer4', '2023-05-23T12:48:19', '2023-05-23T12:48:19', '2023-06-20', 'seller6', 'DELETED', 0 );