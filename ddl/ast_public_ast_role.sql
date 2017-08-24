CREATE TABLE public.ast_role
(
    id integer DEFAULT nextval('ast_roll_id_seq'::regclass) PRIMARY KEY NOT NULL,
    user_id integer NOT NULL,
    role_name varchar(255) DEFAULT NULL::character varying NOT NULL,
    created_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL,
    updated_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL
);
INSERT INTO public.ast_role (id, user_id, role_name, created_at, updated_at) VALUES (4, 4, 'ROLE_ADMIN', '2017-07-01 00:24:50.000000', '2017-07-01 00:24:53.000000');
INSERT INTO public.ast_role (id, user_id, role_name, created_at, updated_at) VALUES (5, 7, 'ROLE_USER', '2017-07-01 02:02:57.000000', '2017-07-01 02:02:59.000000');
INSERT INTO public.ast_role (id, user_id, role_name, created_at, updated_at) VALUES (6, 8, 'ROLE_ADMIN', '2017-07-02 15:14:43.000000', '2017-07-02 15:14:45.000000');