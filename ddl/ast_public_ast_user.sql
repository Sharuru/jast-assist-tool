CREATE TABLE public.ast_user
(
    id integer DEFAULT nextval('ast_user_id_seq'::regclass) PRIMARY KEY NOT NULL,
    username varchar(255) DEFAULT NULL::character varying NOT NULL,
    nickname varchar(255) DEFAULT NULL::character varying NOT NULL,
    password varchar(255) DEFAULT NULL::character varying NOT NULL,
    email varchar(255) DEFAULT NULL::character varying,
    enable boolean NOT NULL,
    created_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL,
    updated_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL
);
INSERT INTO public.ast_user (id, username, nickname, password, email, enable, created_at, updated_at) VALUES (4, 'admin', 'Admin', 'admin', 'admin@admin.com', true, '2017-07-01 00:12:52.000000', '2017-07-01 00:12:55.000000');
INSERT INTO public.ast_user (id, username, nickname, password, email, enable, created_at, updated_at) VALUES (7, 'user', 'User', 'user', 'user@user.com', true, '2017-07-01 02:02:43.000000', '2017-07-01 02:02:45.000000');
INSERT INTO public.ast_user (id, username, nickname, password, email, enable, created_at, updated_at) VALUES (8, 'SYSTEM', 'SYSTEM', 'SYSTEM', 'SYSTEM@SYSTEM.COM', true, '2017-07-02 15:14:28.000000', '2017-07-02 15:14:30.000000');