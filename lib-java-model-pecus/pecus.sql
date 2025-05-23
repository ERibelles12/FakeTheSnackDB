CREATE USER pecusapp WITH PASSWORD 'p3cu54pp';

GRANT ALL PRIVILEGES ON DATABASE "PECUS" to pecusApp;

GRANT ALL ON SCHEMA public TO pecusapp;

CREATE TABLE pecus_schema.audit_log (
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

CREATE TABLE pecus_schema.error_log (
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
CREATE TABLE pecus_schema.usuario (
    pk_id NUMERIC(19,0) PRIMARY KEY,
    dx_imagen_perfil VARCHAR(100),
    dx_user_id_email VARCHAR(50),
    dx_user_id_pais VARCHAR(5),
    dx_user_id_mobile VARCHAR(50),
    dx_user_id_device VARCHAR(50),
    dn_notification_enabled NUMERIC(1,0) NOT NULL,
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
    dn_activo NUMERIC(1,0) NOT NULL,
    dd_fecha_creacion TIMESTAMP,
    dn_usuario_creador NUMERIC(19,0) ,
    dd_fecha_modificacion TIMESTAMP,
    dn_usuario_modificador NUMERIC(19,0)
);

-- Índices
CREATE INDEX idx_usuario_mobpass ON pecus_schema.usuario (
    dn_activo ASC, dx_user_id_mobile ASC, dx_password ASC
);


CREATE INDEX idx_activo_prev_token_usuario ON pecus_schema.usuario (
    dn_activo ASC, dx_refresh_previous_token ASC
);

CREATE INDEX idx_activo_token_token_usuario ON pecus_schema.usuario (
    dn_activo ASC, dx_refresh_token_token ASC
);

CREATE UNIQUE INDEX uk_dx_user_id_email_usuario ON pecus_schema.usuario (
    dx_user_id_email DESC
);
