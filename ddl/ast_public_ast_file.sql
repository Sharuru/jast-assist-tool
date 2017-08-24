CREATE TABLE public.ast_file
(
    id integer DEFAULT nextval('ast_file_id_seq'::regclass) PRIMARY KEY NOT NULL,
    file_name varchar(255) DEFAULT NULL::character varying NOT NULL,
    file_path varchar(255) DEFAULT NULL::character varying NOT NULL,
    keyword1 varchar(255) DEFAULT NULL::character varying,
    keyword2 varchar(255) DEFAULT NULL::character varying,
    file_status varchar NOT NULL,
    review_status varchar NOT NULL,
    delivery_status varchar NOT NULL,
    created_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL,
    updated_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL,
    revision_id varchar
);