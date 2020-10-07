insert into USER (ID, LOGIN, PASSWORD, IS_ACTIVE)
 values (1, 'admin', '{bcrypt}$2a$10$Kmu5xlBT/v8pX3R1OFQwkOvuhIjgi4I0vVThPsQo7vsRlQ1230F66', true);
insert into USER_ROLES (USER_ID, ROLES)
VALUES (1,'ROLE_ADMIN');