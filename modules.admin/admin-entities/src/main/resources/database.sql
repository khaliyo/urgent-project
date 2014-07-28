CREATE TABLE OPENJPASEQ (ID TINYINT NOT NULL, SEQUENCE_VALUE BIGINT, PRIMARY KEY (ID))
  ENGINE = innodb;
CREATE TABLE sys_dict (id BIGINT NOT NULL, createBy VARCHAR(255), creationDate DATETIME, updateBy VARCHAR(255), updateDate DATETIME, description VARCHAR(255), label VARCHAR(255), sort INTEGER, type VARCHAR(255), value VARCHAR(255), version_ BIGINT, PRIMARY KEY (id))
  ENGINE = innodb;
CREATE TABLE sys_office (id BIGINT NOT NULL, createBy VARCHAR(255), creationDate DATETIME, updateBy VARCHAR(255), updateDate DATETIME, address VARCHAR(255), code VARCHAR(255), email VARCHAR(255), fax VARCHAR(255), grade VARCHAR(255), master VARCHAR(255), name VARCHAR(255), parentIds VARCHAR(255), phone VARCHAR(255), type VARCHAR(255), zipCode VARCHAR(255), version_ BIGINT, parent_id BIGINT, PRIMARY KEY (id))
  ENGINE = innodb;
CREATE TABLE sys_permission (id BIGINT NOT NULL, createBy VARCHAR(255), creationDate DATETIME, updateBy VARCHAR(255), updateDate DATETIME, href VARCHAR(255), icon VARCHAR(255), isShow VARCHAR(255), name VARCHAR(255), parentIds VARCHAR(255), permission VARCHAR(255), sort INTEGER, version_ BIGINT, parent_id BIGINT, PRIMARY KEY (id))
  ENGINE = innodb;
CREATE TABLE sys_role (id BIGINT NOT NULL, createBy VARCHAR(255), creationDate DATETIME, updateBy VARCHAR(255), updateDate DATETIME, name VARCHAR(255), remark VARCHAR(255), version_ BIGINT, PRIMARY KEY (id))
  ENGINE = innodb;
CREATE TABLE sys_role_permission (role_id BIGINT, permission_id BIGINT)
  ENGINE = innodb;
CREATE TABLE sys_user (id BIGINT NOT NULL, createBy VARCHAR(255), creationDate DATETIME, updateBy VARCHAR(255), updateDate DATETIME, email VARCHAR(255), loginDate DATETIME, loginIp VARCHAR(255), loginName VARCHAR(255), mobile VARCHAR(255), name VARCHAR(255), password VARCHAR(255), phone VARCHAR(255), version_ BIGINT, company_id BIGINT, office_id BIGINT, PRIMARY KEY (id))
  ENGINE = innodb;
CREATE TABLE sys_user_role (user_id BIGINT, role_id BIGINT, UNIQUE U_SYS__RL_USER_ID (user_id, role_id))
  ENGINE = innodb;
CREATE INDEX I_SYS_FFC_PARENT ON sys_office (parent_id);
CREATE INDEX I_SYS_SSN_PARENT ON sys_permission (parent_id);
CREATE INDEX I_SYS_SSN_ELEMENT ON sys_role_permission (permission_id);
CREATE INDEX I_SYS_SSN_ROLE_ID ON sys_role_permission (role_id);
CREATE INDEX I_SYS_USR_COMPANY ON sys_user (company_id);
CREATE INDEX I_SYS_USR_OFFICE ON sys_user (office_id);
CREATE INDEX I_SYS__RL_ELEMENT ON sys_user_role (role_id);
CREATE INDEX I_SYS__RL_USER_ID ON sys_user_role (user_id);