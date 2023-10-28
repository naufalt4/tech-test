--
-- PostgreSQL database dump
--

-- Dumped from database version 13.12
-- Dumped by pg_dump version 13.12

-- Started on 2023-10-28 08:44:55

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 16397)
-- Name: salt; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA salt;


ALTER SCHEMA salt OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16430)
-- Name: konsumen; Type: TABLE; Schema: salt; Owner: postgres
--

CREATE TABLE salt.konsumen (
    id integer NOT NULL,
    nama character varying(255) NOT NULL,
    alamat text NOT NULL,
    kota character(50) NOT NULL,
    provinsi character(50) NOT NULL,
    tgl_registrasi timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status bpchar DEFAULT 'aktif'::bpchar NOT NULL
);


ALTER TABLE salt.konsumen OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16442)
-- Name: konsumen_seq; Type: SEQUENCE; Schema: salt; Owner: postgres
--

CREATE SEQUENCE salt.konsumen_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE salt.konsumen_seq OWNER TO postgres;

--
-- TOC entry 2986 (class 0 OID 16430)
-- Dependencies: 201
-- Data for Name: konsumen; Type: TABLE DATA; Schema: salt; Owner: postgres
--

COPY salt.konsumen (id, nama, alamat, kota, provinsi, tgl_registrasi, status) FROM stdin;
2	Naufal	Jl satu	Bandung                                           	Jawa Barat                                        	2023-10-10 00:00:00	Aktif
6	Taufik	jalan test	Bandung                                           	Jawa Barat                                        	2023-10-27 00:00:00	Aktif
\.


--
-- TOC entry 2993 (class 0 OID 0)
-- Dependencies: 202
-- Name: konsumen_seq; Type: SEQUENCE SET; Schema: salt; Owner: postgres
--

SELECT pg_catalog.setval('salt.konsumen_seq', 11, true);


--
-- TOC entry 2855 (class 2606 OID 16439)
-- Name: konsumen konsumen_pk; Type: CONSTRAINT; Schema: salt; Owner: postgres
--

ALTER TABLE ONLY salt.konsumen
    ADD CONSTRAINT konsumen_pk PRIMARY KEY (id);


-- Completed on 2023-10-28 08:44:55

--
-- PostgreSQL database dump complete
--

