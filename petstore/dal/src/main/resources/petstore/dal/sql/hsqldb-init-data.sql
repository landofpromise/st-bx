set   names   gbk;

INSERT INTO user VALUES('j2ee','j2ee',null);
INSERT INTO user VALUES('admin','admin','admin');
INSERT INTO user VALUES('finance','finance','finance');
INSERT INTO user VALUES('headman','headman','headman');
INSERT INTO user VALUES('stmember','stmember','stmember');

INSERT INTO account VALUES('j2ee', '����', '����', 'yourname@yourdomain.com', '555-555-5555', '15827551226');
INSERT INTO account VALUES('admin', '������', '����', 'yourname@yourdomain.com', '555-555-1234', '15827551227');
INSERT INTO account VALUES('finance', '����', '����', 'yourname@yourdomain.com', '555-555-2222', '15827551228');
INSERT INTO account VALUES('headman', 'С��', '����', 'yourname@yourdomain.com', '555-555-3333', '15827551229');
INSERT INTO account VALUES('stmember', '����', '����', 'yourname@yourdomain.com', '555-555-4444', '15827551220');

INSERT INTO orders VALUES(1, 'stmember', '����', '2015-5-28', 3.50, 8.00, 7.00, 1.00, 6.50, 6.71, 32.71, 0, '������̴��6%�ܿ��ģ���*����*��');
INSERT INTO orders VALUES(2, 'headman', '����', '2015-5-28', 3.51, 8.00, 7.00, 1.00, 6.50, 6.71, 32.72, 65.43, '������̴��6%�ܿ��ģ���*����*��');
INSERT INTO orders VALUES(3, 'headman', '����', '2015-5-28', 3.52, 8.00, 7.00, 1.00, 6.50, 6.71, 32.73, 65.47, '������̴��6%�ܿ��ģ���*����*��');
INSERT INTO orders VALUES(4, 'stmember', '����', '2015-5-28', 3.53, 8.00, 7.00, 1.00, 6.50, 6.71, 32.74, 0, '������̴��6%�ܿ��ģ���*����*��');

INSERT INTO orderstatus VALUES(1,'�����');
INSERT INTO orderstatus VALUES(2,'�����');
INSERT INTO orderstatus VALUES(3,'�����');
INSERT INTO orderstatus VALUES(4,'�����');


INSERT INTO sequence VALUES('stmember', 5);
INSERT INTO sequence VALUES('������', 0);