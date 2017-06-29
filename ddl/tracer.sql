--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

-- Started on 2017-06-29 15:30:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12393)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2146 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16485)
-- Name: tracer_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tracer_role (
    id integer NOT NULL,
    username character varying(255) NOT NULL,
    role_name character varying(255) NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL
);


ALTER TABLE tracer_role OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16483)
-- Name: tracer_roll_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tracer_roll_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tracer_roll_id_seq OWNER TO postgres;

--
-- TOC entry 2147 (class 0 OID 0)
-- Dependencies: 187
-- Name: tracer_roll_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tracer_roll_id_seq OWNED BY tracer_role.id;


--
-- TOC entry 186 (class 1259 OID 16439)
-- Name: tracer_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tracer_user (
    id integer NOT NULL,
    username character varying(255) NOT NULL,
    nickname character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    email character varying(255),
    enable boolean NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);


ALTER TABLE tracer_user OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16437)
-- Name: tracer_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tracer_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tracer_user_id_seq OWNER TO postgres;

--
-- TOC entry 2148 (class 0 OID 0)
-- Dependencies: 185
-- Name: tracer_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tracer_user_id_seq OWNED BY tracer_user.id;


--
-- TOC entry 2014 (class 2604 OID 16488)
-- Name: tracer_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tracer_role ALTER COLUMN id SET DEFAULT nextval('tracer_roll_id_seq'::regclass);


--
-- TOC entry 2013 (class 2604 OID 16442)
-- Name: tracer_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tracer_user ALTER COLUMN id SET DEFAULT nextval('tracer_user_id_seq'::regclass);


--
-- TOC entry 2139 (class 0 OID 16485)
-- Dependencies: 188
-- Data for Name: tracer_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tracer_role (id, username, role_name, created_at, updated_at) FROM stdin;
1	admin	ROLE_ADMIN	2017-06-29 14:17:12+00	2017-06-29 14:17:14+00
2	admin	ROLE_DEV	2017-06-29 14:22:30+00	2017-06-29 14:22:32+00
\.


--
-- TOC entry 2149 (class 0 OID 0)
-- Dependencies: 187
-- Name: tracer_roll_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tracer_roll_id_seq', 2, true);


--
-- TOC entry 2137 (class 0 OID 16439)
-- Dependencies: 186
-- Data for Name: tracer_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tracer_user (id, username, nickname, password, email, enable, created_at, updated_at) FROM stdin;
3	admin	Admin	admin	admin@admin.com	t	2017-06-29 13:40:22+00	2017-06-29 13:40:24+00
\.


--
-- TOC entry 2150 (class 0 OID 0)
-- Dependencies: 185
-- Name: tracer_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tracer_user_id_seq', 3, true);


--
-- TOC entry 2018 (class 2606 OID 16493)
-- Name: tracer_role tracer_roll_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tracer_role
    ADD CONSTRAINT tracer_roll_pkey PRIMARY KEY (id);


--
-- TOC entry 2016 (class 2606 OID 16495)
-- Name: tracer_user tracer_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tracer_user
    ADD CONSTRAINT tracer_user_pkey PRIMARY KEY (id);


-- Completed on 2017-06-29 15:30:24

--
-- PostgreSQL database dump complete
--

