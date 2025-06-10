

---------------------------------------------------------------
-- SECURITY MODEL
-- EXAMPLE MODEL
----------------------------------------------------------------

DROP INDEX idx_usuario_mobpass;
DROP INDEX idx_activo_prev_token_usuario;
DROP INDEX idx_activo_token_token_usuario;
DROP INDEX uk_dx_user_id_email_usuario;
DROP INDEX idx_tipo_rol;

DROP TABLE AUDIT_LOG;
DROP TABLE ERROR_LOG;
DROP TABLE USUARIO;
DROP TABLE TIPO_ROL;

CREATE TABLE audit_log (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_user_id VARCHAR(50),
    dx_token VARCHAR(4000),
    dx_log_level VARCHAR(40) NOT NULL,
    dx_class_name VARCHAR(200) NOT NULL,
    dx_method_name VARCHAR(100) NOT NULL,
    dx_message TEXT NOT NULL,
    dd_event_date TIMESTAMP NOT NULL,
    dd_request_date VARCHAR(30),
    dd_log_date TIMESTAMP NOT NULL,
    dx_request_vo TEXT,
    dx_response_vo TEXT,
    dx_stacktrace TEXT,
    dx_id_client_invoke VARCHAR(50),
    dx_client_operation_code VARCHAR(50),
    dx_elapsed_time VARCHAR(20),
    dx_status VARCHAR(10)
);

CREATE TABLE error_log (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_user_id VARCHAR(50),
    dx_token TEXT,
    dx_log_level VARCHAR(40) NOT NULL,
    dx_class_name VARCHAR(200) NOT NULL,
    dx_method_name VARCHAR(100) NOT NULL,
    dx_message TEXT NOT NULL,
    dd_event_date TIMESTAMP NOT NULL,
    dd_request_date VARCHAR(30),
    dd_log_date TIMESTAMP NOT NULL,
    dx_request_vo TEXT,
    dx_stacktrace TEXT,
    dx_id_client_invoke VARCHAR(50),
    dx_client_operation_code VARCHAR(50)
);


-- Tabla USUARIO
CREATE TABLE usuario (
    pk_id SERIAL PRIMARY KEY,
    dx_imagen_perfil VARCHAR(100),
    dx_user_id_email VARCHAR(50),
    dx_user_id_pais VARCHAR(5),
    dx_user_id_mobile VARCHAR(50),
    dx_user_id_device VARCHAR(50),
    dn_notification_enabled BOOLEAN,
    dd_last_login TIMESTAMP,
    dx_password VARCHAR(70),
    dn_password_status NUMERIC(6,0),
    dd_password_status_change TIMESTAMP,
    dd_refresh_token_date TIMESTAMP,
    dx_refresh_previous_token VARCHAR(4000),
    dx_refresh_token_token VARCHAR(4000),
    dx_firebase_token VARCHAR(250),
    dx_codigo_alexa VARCHAR(10),
    dd_aceptacion_terminos TIMESTAMP,
    dn_activo BOOLEAN,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0),
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);


-- Tabla TIPO ROL
CREATE TABLE TIPO_ROL (
    pk_id BIGSERIAL PRIMARY KEY,
    dx_id_Nombre VARCHAR(26),
    dx_descripcion VARCHAR(100),
    dn_global VARCHAR(5),
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

CREATE UNIQUE INDEX idx_tipo_rol ON tipo_rol(
    dx_id_nombre ASC
);

---------------------------------------------------------------
---------------------------------------------------------------
--   FAKE THE SNACK PROJECT
---------------------------------------------------------------
---------------------------------------------------------------

-- DESTROY EXISTING DDBB OBJECTS


DROP INDEX idx_product_name;
DROP INDEX idx_brand_name;
DROP INDEX idx_category_name;
DROP INDEX idx_subcategory_name;

DROP TABLE RESULT_ITEM;
DROP TABLE EVALUATION;
DROP TABLE RECIPE;
DROP TABLE PRODUCT;
DROP TABLE INGREDIENT;
DROP TABLE SUBCATEGORY;
DROP TABLE CATEGORY;
DROP TABLE BRAND;


---------------------------------------------------------------
-- TABLE:  BRAND
-- DESCRIPTION:  List of brand
----------------------------------------------------------------

CREATE TABLE BRAND (
    pk_id BIGSERIAL PRIMARY KEY,
    dx_name VARCHAR(26),
    dx_description VARCHAR(100),
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

CREATE UNIQUE INDEX idx_brand_name ON BRAND(dx_name ASC);

---------------------------------------------------------------
-- TABLE:  CATEGORY
-- DESCRIPTION:  List of categories
----------------------------------------------------------------

CREATE TABLE CATEGORY (
    pk_id BIGSERIAL PRIMARY KEY,
    dx_name VARCHAR(26),
    dn_general_indicator BOOLEAN default true,
    dn_milk_indicator BOOLEAN default false,
    dn_meat_indicator BOOLEAN default false,
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

CREATE UNIQUE INDEX idx_category_name ON CATEGORY(dx_name ASC);


---------------------------------------------------------------
-- TABLE:  SUBCATEGORY
-- DESCRIPTION:  List of subcategories
----------------------------------------------------------------

CREATE TABLE SUBCATEGORY (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_category_id BIGSERIAL NOT NULL ,
    dx_name VARCHAR(26),
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0),
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

-- foreing key
ALTER TABLE SUBCATEGORY ADD CONSTRAINT const_fk_category_id FOREIGN KEY (fk_category_id) REFERENCES CATEGORY (pk_id);

-- idex
CREATE UNIQUE INDEX idx_subcategory_name ON CATEGORY(dx_name ASC);

---------------------------------------------------------------
-- Tabla PRODUCT
----------------------------------------------------------------

CREATE TABLE PRODUCT (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_brand_id BIGSERIAL ,
    fk_category_id BIGSERIAL,
    fk_subcategory_id BIGSERIAL,
    dx_name VARCHAR(26),
    dx_description VARCHAR(100),
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

-- foreing key
ALTER TABLE PRODUCT ADD CONSTRAINT const_fk_brand_id FOREIGN KEY (fk_brand_id) REFERENCES BRAND (pk_id);
ALTER TABLE PRODUCT ADD CONSTRAINT const_fk_category_id FOREIGN KEY (fk_category_id) REFERENCES CATEGORY (pk_id);
ALTER TABLE PRODUCT ADD CONSTRAINT const_fk_subcategory_id FOREIGN KEY (fk_subcategory_id) REFERENCES SUBCATEGORY (pk_id);

CREATE UNIQUE INDEX idx_product_name ON product(
    fk_brand_id ASC, fk_category_id ASC, fk_subcategory_id ASC, dx_name ASC);

---------------------------------------------------------------
-- TABLE:  INGREDIENT
-- DESCRIPTION:  List of ingredient
----------------------------------------------------------------

CREATE TABLE INGREDIENT (
                       pk_id BIGSERIAL PRIMARY KEY,
                       dx_name VARCHAR(26),
                       dx_description VARCHAR(100),
                       dn_activo BOOLEAN default true,
                       dd_fecha_creacion TIMESTAMP,
                       dn_usuario_creador NUMERIC(19,0) ,
                       dd_fecha_modificacion TIMESTAMP,
                       dn_usuario_modificador NUMERIC(19,0)
);

CREATE UNIQUE INDEX idx_ingredient_name ON INGREDIENT(dx_name ASC);


---------------------------------------------------------------
-- TABLE:  RECIPE
-- DESCRIPTION:  Composition os a product
----------------------------------------------------------------

CREATE TABLE RECIPE (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_product_id BIGSERIAL NOT NULL,
    fk_ingredient_id BIGSERIAL NOT NULL,
    dd_register_date DATE NOT NULL,
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

-- foreing key
ALTER TABLE RECIPE ADD CONSTRAINT const_fk_product_id FOREIGN KEY (fk_product_id) REFERENCES PRODUCT (pk_id);
ALTER TABLE RECIPE ADD CONSTRAINT const_fk_ingredient_id FOREIGN KEY (fk_ingredient_id) REFERENCES INGREDIENT (pk_id);


CREATE UNIQUE INDEX idx_recipe_product ON RECIPE(fk_product_id ASC, fk_ingredient_id ASC, dd_register_date ASC);

---------------------------------------------------------------
-- TABLE:  EVALUATION
-- DESCRIPTION:  List of simulation
----------------------------------------------------------------

CREATE TABLE EVALUATION (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_brand_id BIGSERIAL NOT NULL,
    fk_category_id BIGSERIAL NOT NULL,
    fk_subcategory_id BIGSERIAL NOT NULL,
    fk_product_id BIGSERIAL NOT NULL,
    fk_recipe_id BIGSERIAL NOT NULL,
    dn_ingredient_percentaje NUMERIC(1,0) default 1,
    dd_evaluation_date DATE NOT NULL,
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

-- foreing key
ALTER TABLE EVALUATION ADD CONSTRAINT const_fk_brand_id FOREIGN KEY (fk_brand_id) REFERENCES BRAND (pk_id);
ALTER TABLE EVALUATION ADD CONSTRAINT const_fk_category_id FOREIGN KEY (fk_category_id) REFERENCES CATEGORY (pk_id);
ALTER TABLE EVALUATION ADD CONSTRAINT const_fk_subcategory_id FOREIGN KEY (fk_subcategory_id) REFERENCES SUBCATEGORY (pk_id);
ALTER TABLE EVALUATION ADD CONSTRAINT const_fk_product_id FOREIGN KEY (fk_product_id) REFERENCES PRODUCT (pk_id);
ALTER TABLE EVALUATION ADD CONSTRAINT const_fk_recipe_id FOREIGN KEY (fk_recipe_id) REFERENCES RECIPE (pk_id);

---------------------------------------------------------------
-- TABLE:  RESULT_ITEM
-- DESCRIPTION:  Ingredient simulation results
----------------------------------------------------------------

CREATE TABLE RESULT_ITEM (
    pk_id BIGSERIAL PRIMARY KEY,
    fk_evaluation_id BIGSERIAL NOT NULL,
    dd_evaluation_date DATE NOT NULL,
    fk_recipe_id BIGSERIAL NOT NULL,
    dn_ingredient_percentaje NUMERIC(1,0) default 1,
    dn_activo BOOLEAN default true,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

-- foreing key
ALTER TABLE RESULT_ITEM ADD CONSTRAINT const_fk_evaluation_id FOREIGN KEY (fk_evaluation_id) REFERENCES EVALUATION (pk_id);
ALTER TABLE RESULT_ITEM ADD CONSTRAINT const_fk_recipe_id FOREIGN KEY (fk_recipe_id) REFERENCES RECIPE (pk_id);


--------------------------------------------------------------
--------------------  EXAMPLE DATA ---------------------------
--------------------------------------------------------------

INSERT INTO BRAND (pk_id, dx_name, dx_description, dn_activo, dn_usuario_creador, dd_fecha_creacion,dn_usuario_modificador,dd_fecha_modificacion) VALUES
                  (1,'GENERAL BRAND','DEFAULT BRAND',true,1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01');

INSERT INTO CATEGORY (pk_id, dx_name, dn_general_indicator, dn_milk_indicator, dn_meat_indicator, dn_activo, dn_usuario_creador, dd_fecha_creacion,dn_usuario_modificador,dd_fecha_modificacion) VALUES
                (1,'GENERAL CATEGORY',true,false,false,true,1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
                (2,'GENERAL MILK CATEGORY',false,true,false,true,1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
                (3,'GENERAL MEAT CATEGORY',false,false,true,true,1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01');

INSERT INTO SUBCATEGORY (PK_ID, FK_CATEGORY_ID, DX_NAME, dn_activo, dn_usuario_creador, dd_fecha_creacion, dn_usuario_modificador, dd_fecha_modificacion) VALUES
    (1,2,'PRODUCT ENTERA',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
    (2,2,'PRODUCT DESCREMADOS',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
    (3,3,'PIG',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
    (4,3,'COWN',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01');

INSERT INTO PRODUCT (PK_ID, FK_BRAND_ID, FK_CATEGORY_ID, FK_SUBCATEGORY_ID, DX_NAME, DX_DESCRIPTION, dn_activo, DN_USUARIO_CREADOR, dd_fecha_creacion, DN_USUARIO_MODIFICADOR,dd_fecha_modificacion) VALUES
    (1,1,1,1,'PRODUCTO GENERICO','NOMBRE DEL PRODUCTO GENERICO',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
    (2,1,2,1,'YOGURT DESCREMADO','NOMBRE DEL YOGURT DESCREMADO',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01'),
    (3,1,3,3,'PIG PRODUCT','NOMBRE DEL PRODUCTO DE CERDO',true, 1,'2025-05-01 01:01:01',1,'2025-05-01 01:01:01');
