CREATE TABLE public.ast_setting
(
    id integer DEFAULT nextval('ast_setting_id_seq'::regclass) PRIMARY KEY NOT NULL,
    setting_group varchar(32) DEFAULT NULL::character varying NOT NULL,
    settings varchar(255) DEFAULT NULL::character varying,
    created_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL,
    updated_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL
);
INSERT INTO public.ast_setting (id, setting_group, settings, created_at, updated_at) VALUES (150, 'GROUP_GIT', '{"repoAddress":"https://github.com/Sharuru/jast-assist-tool.git","repoBranch":"master","repoLocalPath":"D:\\JATtest"}', '2017-07-01 00:28:28.920000', '2017-08-24 14:56:30.280000');