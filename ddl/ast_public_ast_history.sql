CREATE TABLE public.ast_history
(
    id integer DEFAULT nextval('ast_history_id_seq'::regclass) PRIMARY KEY NOT NULL,
    file_id integer,
    revision_id varchar,
    operation_task varchar,
    operator integer,
    operation_time timestamp(6) with time zone DEFAULT NULL::timestamp with time zone,
    created_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL,
    updated_at timestamp(6) with time zone DEFAULT NULL::timestamp with time zone NOT NULL
);