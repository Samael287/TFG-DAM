--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2025-03-17 21:02:16

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
-- TOC entry 910 (class 1247 OID 129366)
-- Name: user_role; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.user_role AS ENUM (
    'ADMIN',
    'CLIENTE'
);


ALTER TYPE public.user_role OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 121171)
-- Name: actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actor (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.actor OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 121170)
-- Name: Actors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Actors_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Actors_id_seq" OWNER TO postgres;

--
-- TOC entry 4963 (class 0 OID 0)
-- Dependencies: 219
-- Name: Actors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Actors_id_seq" OWNED BY public.actor.id;


--
-- TOC entry 222 (class 1259 OID 121180)
-- Name: studios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.studios (
    id integer NOT NULL,
    studio character varying(255) NOT NULL
);


ALTER TABLE public.studios OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 121179)
-- Name: Studio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Studio_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Studio_id_seq" OWNER TO postgres;

--
-- TOC entry 4964 (class 0 OID 0)
-- Dependencies: 221
-- Name: Studio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Studio_id_seq" OWNED BY public.studios.id;


--
-- TOC entry 241 (class 1259 OID 129542)
-- Name: carrito; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.carrito (
    id integer NOT NULL,
    usuario_id integer NOT NULL,
    pelicula_id integer NOT NULL
);


ALTER TABLE public.carrito OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 129541)
-- Name: carrito_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.carrito_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.carrito_id_seq OWNER TO postgres;

--
-- TOC entry 4965 (class 0 OID 0)
-- Dependencies: 240
-- Name: carrito_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.carrito_id_seq OWNED BY public.carrito.id;


--
-- TOC entry 218 (class 1259 OID 121162)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id integer NOT NULL,
    category character varying(255) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 121161)
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.category_id_seq OWNER TO postgres;

--
-- TOC entry 4966 (class 0 OID 0)
-- Dependencies: 217
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.categories.id;


--
-- TOC entry 243 (class 1259 OID 129562)
-- Name: compras; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.compras (
    id integer NOT NULL,
    usuario_id integer,
    id_usuario_borrado integer,
    pelicula_id integer,
    id_pelicula_borrada integer,
    tarjeta_id integer,
    id_tarjeta_borrada integer,
    precio double precision NOT NULL,
    fecha_compra timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    transaccion_id integer NOT NULL
);


ALTER TABLE public.compras OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 129561)
-- Name: compras_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.compras_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.compras_id_seq OWNER TO postgres;

--
-- TOC entry 4967 (class 0 OID 0)
-- Dependencies: 242
-- Name: compras_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.compras_id_seq OWNED BY public.compras.id;


--
-- TOC entry 228 (class 1259 OID 121207)
-- Name: director; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.director (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.director OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 121206)
-- Name: directors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.directors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.directors_id_seq OWNER TO postgres;

--
-- TOC entry 4968 (class 0 OID 0)
-- Dependencies: 227
-- Name: directors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.directors_id_seq OWNED BY public.director.id;


--
-- TOC entry 224 (class 1259 OID 121189)
-- Name: flags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flags (
    id integer NOT NULL,
    flag character varying(255) NOT NULL
);


ALTER TABLE public.flags OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 121188)
-- Name: flags_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.flags_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.flags_id_seq OWNER TO postgres;

--
-- TOC entry 4969 (class 0 OID 0)
-- Dependencies: 223
-- Name: flags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.flags_id_seq OWNED BY public.flags.id;


--
-- TOC entry 226 (class 1259 OID 121198)
-- Name: genres; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genres (
    id integer NOT NULL,
    genre character varying(255) NOT NULL
);


ALTER TABLE public.genres OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 121197)
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.genre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.genre_id_seq OWNER TO postgres;

--
-- TOC entry 4970 (class 0 OID 0)
-- Dependencies: 225
-- Name: genre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.genre_id_seq OWNED BY public.genres.id;


--
-- TOC entry 216 (class 1259 OID 121153)
-- Name: language; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.language (
    id integer NOT NULL,
    language character varying(255) NOT NULL
);


ALTER TABLE public.language OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 121279)
-- Name: pelicula_actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pelicula_actor (
    pelicula_id integer NOT NULL,
    actor_id integer NOT NULL
);


ALTER TABLE public.pelicula_actor OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 121234)
-- Name: pelicula_director; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pelicula_director (
    pelicula_id integer NOT NULL,
    director_id integer NOT NULL
);


ALTER TABLE public.pelicula_director OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 121264)
-- Name: pelicula_flags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pelicula_flags (
    pelicula_id integer NOT NULL,
    flag_id integer NOT NULL
);


ALTER TABLE public.pelicula_flags OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 121249)
-- Name: pelicula_genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pelicula_genre (
    pelicula_id integer NOT NULL,
    genre_id integer NOT NULL
);


ALTER TABLE public.pelicula_genre OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 121294)
-- Name: pelicula_language; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pelicula_language (
    pelicula_id integer NOT NULL,
    language_id integer NOT NULL
);


ALTER TABLE public.pelicula_language OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 121216)
-- Name: peliculas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.peliculas (
    id integer NOT NULL,
    titulo character varying(255),
    fecha_estreno date,
    duracion integer,
    precio double precision,
    portada character varying(255),
    studio_id integer,
    category_id integer,
    descripcion character varying(255)
);


ALTER TABLE public.peliculas OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 121215)
-- Name: peliculas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.peliculas_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.peliculas_id_seq OWNER TO postgres;

--
-- TOC entry 4971 (class 0 OID 0)
-- Dependencies: 229
-- Name: peliculas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.peliculas_id_seq OWNED BY public.peliculas.id;


--
-- TOC entry 215 (class 1259 OID 121152)
-- Name: public.language_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."public.language_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."public.language_id_seq" OWNER TO postgres;

--
-- TOC entry 4972 (class 0 OID 0)
-- Dependencies: 215
-- Name: public.language_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."public.language_id_seq" OWNED BY public.language.id;


--
-- TOC entry 237 (class 1259 OID 129355)
-- Name: tarjeta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tarjeta (
    id integer NOT NULL,
    numero_tarjeta character varying(255) NOT NULL,
    fecha_caducidad timestamp(6) without time zone NOT NULL,
    numero_seguridad character varying(255) NOT NULL,
    fondos_disponibles double precision NOT NULL,
    usuario_anterior_id integer
);


ALTER TABLE public.tarjeta OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 129354)
-- Name: tarjeta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tarjeta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tarjeta_id_seq OWNER TO postgres;

--
-- TOC entry 4973 (class 0 OID 0)
-- Dependencies: 236
-- Name: tarjeta_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tarjeta_id_seq OWNED BY public.tarjeta.id;


--
-- TOC entry 239 (class 1259 OID 129372)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios (
    id integer NOT NULL,
    nombre character varying(255) NOT NULL,
    apellidos character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    rol character varying(255) DEFAULT 'CLIENTE'::public.user_role NOT NULL,
    tarjeta_id integer
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 129371)
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuarios_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuarios_id_seq OWNER TO postgres;

--
-- TOC entry 4974 (class 0 OID 0)
-- Dependencies: 238
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;


--
-- TOC entry 4714 (class 2604 OID 121174)
-- Name: actor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actor ALTER COLUMN id SET DEFAULT nextval('public."Actors_id_seq"'::regclass);


--
-- TOC entry 4723 (class 2604 OID 129545)
-- Name: carrito id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carrito ALTER COLUMN id SET DEFAULT nextval('public.carrito_id_seq'::regclass);


--
-- TOC entry 4713 (class 2604 OID 121165)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 129565)
-- Name: compras id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras ALTER COLUMN id SET DEFAULT nextval('public.compras_id_seq'::regclass);


--
-- TOC entry 4718 (class 2604 OID 121210)
-- Name: director id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.director ALTER COLUMN id SET DEFAULT nextval('public.directors_id_seq'::regclass);


--
-- TOC entry 4716 (class 2604 OID 121192)
-- Name: flags id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flags ALTER COLUMN id SET DEFAULT nextval('public.flags_id_seq'::regclass);


--
-- TOC entry 4717 (class 2604 OID 121201)
-- Name: genres id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genres ALTER COLUMN id SET DEFAULT nextval('public.genre_id_seq'::regclass);


--
-- TOC entry 4712 (class 2604 OID 121156)
-- Name: language id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.language ALTER COLUMN id SET DEFAULT nextval('public."public.language_id_seq"'::regclass);


--
-- TOC entry 4719 (class 2604 OID 121219)
-- Name: peliculas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.peliculas ALTER COLUMN id SET DEFAULT nextval('public.peliculas_id_seq'::regclass);


--
-- TOC entry 4715 (class 2604 OID 121183)
-- Name: studios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studios ALTER COLUMN id SET DEFAULT nextval('public."Studio_id_seq"'::regclass);


--
-- TOC entry 4720 (class 2604 OID 129358)
-- Name: tarjeta id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarjeta ALTER COLUMN id SET DEFAULT nextval('public.tarjeta_id_seq'::regclass);


--
-- TOC entry 4721 (class 2604 OID 129375)
-- Name: usuarios id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);


--
-- TOC entry 4934 (class 0 OID 121171)
-- Dependencies: 220
-- Data for Name: actor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actor (id, name) FROM stdin;
1	Leonardo DiCaprio
2	Tom Hardy
3	Christian Bale
4	Scarlett Johansson
5	Morgan Freeman
6	Tom Holland Updated
8	Tom Hanks
9	Johnny Depp
10	Brad Pitt
11	Robert Downey Jr
12	Denzel Washington
13	Will Smith
14	Keanu Reeves
15	Joaquin Phoenix
16	Chris Hemsworth
17	Hugh Jackman
18	Matt Damon
19	Angelina Jolie
20	Jennifer Lawrence
21	Margot Robbie
22	Gal Gadot
23	Zendaya
24	Amy Adams
25	Nicole Kidman
\.


--
-- TOC entry 4955 (class 0 OID 129542)
-- Dependencies: 241
-- Data for Name: carrito; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.carrito (id, usuario_id, pelicula_id) FROM stdin;
3	2	1
4	2	2
7	2	5
8	51	4
11	52	5
16	51	7
\.


--
-- TOC entry 4932 (class 0 OID 121162)
-- Dependencies: 218
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, category) FROM stdin;
1	Todos los públicos
2	+7
3	+13\n
4	+16\n
5	+18
\.


--
-- TOC entry 4957 (class 0 OID 129562)
-- Dependencies: 243
-- Data for Name: compras; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.compras (id, usuario_id, id_usuario_borrado, pelicula_id, id_pelicula_borrada, tarjeta_id, id_tarjeta_borrada, precio, fecha_compra, transaccion_id) FROM stdin;
1	51	\N	2	\N	56	\N	8.99	2025-03-16 12:41:37.495149	1
2	51	\N	1	\N	56	\N	9.99	2025-03-16 15:13:24.048181	2
6	51	\N	3	\N	56	\N	7.99	2025-03-16 20:43:02.079864	3
7	51	\N	15	\N	56	\N	90	2025-03-16 20:43:02.174852	3
8	51	\N	17	\N	56	\N	12	2025-03-16 20:43:02.201852	3
9	51	\N	7	\N	56	\N	19.99	2025-03-17 15:37:27.754103	4
10	51	\N	4	\N	56	\N	10.99	2025-03-17 15:37:27.853089	4
11	53	\N	4	\N	57	\N	10.99	2025-03-17 16:40:16.472171	5
17	55	\N	7	\N	59	\N	19.99	2025-03-17 17:25:19.097205	1
18	55	\N	3	\N	59	\N	7.99	2025-03-17 17:25:19.108197	1
19	54	\N	4	\N	58	\N	10.99	2025-03-17 17:27:11.366216	1
20	54	\N	2	\N	58	\N	8.99	2025-03-17 17:27:26.01294	2
21	55	\N	4	\N	59	\N	10.99	2025-03-17 17:28:18.037081	2
22	55	\N	17	\N	59	\N	12	2025-03-17 17:29:35.168008	3
24	55	\N	1	\N	59	\N	9.99	2025-03-17 18:26:21.86068	4
25	55	\N	15	\N	59	\N	90	2025-03-17 18:46:41.941834	5
\.


--
-- TOC entry 4942 (class 0 OID 121207)
-- Dependencies: 228
-- Data for Name: director; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.director (id, name) FROM stdin;
1	Christopher Nolan
2	Steven Spielberg
3	James Cameron
4	MinaBotiesoUpdated
6	Quentin Tarantino
7	Ridley Scott
8	Peter Jackson
9	Tim Burton
10	David Fincher
11	Guillermo del Toro
12	Sam Raimy
13	Alfred Hitchcock
\.


--
-- TOC entry 4938 (class 0 OID 121189)
-- Dependencies: 224
-- Data for Name: flags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flags (id, flag) FROM stdin;
1	Subtitulada
2	Versión Extendida
3	Doblada al Español
4	4K UHD
5	Violencia
7	Clásico 
8	Basada en hechos reales
9	Animada
10	Lenguaje explícito
11	Disponible en streaming
12	Drogas
13	Blockbuster
14	Indie
15	B y N
16	Dolby Atmos
17	3D
18	Final Abierto
19	Basada en un videojuego
20	Live-Action
\.


--
-- TOC entry 4940 (class 0 OID 121198)
-- Dependencies: 226
-- Data for Name: genres; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genres (id, genre) FROM stdin;
1	Acción
2	Aventura
3	Ciencia Ficción
4	Drama
5	Comedia
6	Terror
9	Animación
10	Biográfico
11	Crimen
12	Guerra
13	Infantil/Familiar
14	Misterio
15	Cyberpunk
\.


--
-- TOC entry 4930 (class 0 OID 121153)
-- Dependencies: 216
-- Data for Name: language; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.language (id, language) FROM stdin;
1	Español
2	English
3	Françocis
4	Deutsch
5	Italiano
6	Portuguese
8	Ruso
9	Japonés
10	Griego
11	Polaco
12	Tailandés
\.


--
-- TOC entry 4948 (class 0 OID 121279)
-- Dependencies: 234
-- Data for Name: pelicula_actor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pelicula_actor (pelicula_id, actor_id) FROM stdin;
17	10
17	3
2	5
17	11
17	1
2	6
7	4
7	5
5	3
5	4
4	2
4	5
3	1
3	6
1	3
1	2
1	1
1	6
15	9
15	15
15	11
\.


--
-- TOC entry 4945 (class 0 OID 121234)
-- Dependencies: 231
-- Data for Name: pelicula_director; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pelicula_director (pelicula_id, director_id) FROM stdin;
2	2
2	4
7	2
5	2
4	1
3	3
1	1
15	12
15	9
17	12
17	13
17	9
17	2
\.


--
-- TOC entry 4947 (class 0 OID 121264)
-- Dependencies: 233
-- Data for Name: pelicula_flags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pelicula_flags (pelicula_id, flag_id) FROM stdin;
2	2
5	3
5	2
4	4
4	1
3	3
1	3
1	1
1	4
1	5
15	8
15	12
15	1
7	4
7	1
17	4
17	10
17	8
17	12
\.


--
-- TOC entry 4946 (class 0 OID 121249)
-- Dependencies: 232
-- Data for Name: pelicula_genre; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pelicula_genre (pelicula_id, genre_id) FROM stdin;
2	2
2	3
7	4
7	1
5	3
5	2
4	4
4	1
3	4
1	4
1	1
1	3
1	9
15	5
15	10
15	11
17	6
17	4
17	13
17	3
17	11
\.


--
-- TOC entry 4949 (class 0 OID 121294)
-- Dependencies: 235
-- Data for Name: pelicula_language; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pelicula_language (pelicula_id, language_id) FROM stdin;
17	2
17	1
2	2
17	4
17	10
17	6
7	2
7	3
7	1
5	2
5	1
4	3
4	1
4	2
3	2
3	1
1	1
1	2
1	4
15	10
15	1
15	11
\.


--
-- TOC entry 4944 (class 0 OID 121216)
-- Dependencies: 230
-- Data for Name: peliculas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.peliculas (id, titulo, fecha_estreno, duracion, precio, portada, studio_id, category_id, descripcion) FROM stdin;
2	Jurassic Park	1993-06-11	127	8.99	images/peliculas/jurassic_park.png	2	1	Un parque temático con dinosaurios clonados se convierte en una pesadilla.
7	Gladiator	2000-05-05	155	19.99	images/peliculas/gladiator.jpg	2	4	Un general romano es traicionado y convertido en esclavo, pero lucha para vengar la muerte de su familia.
5	Interestellar	2014-11-07	169	12.99	images/peliculas/interstellar.jpg	1	3	Un equipo de astronautas viaja a través de un agujero de gusano en busca de un nuevo hogar para la humanidad.
4	The Dark Knight	2008-07-18	152	10.99	images/peliculas/thedarkknight.jpg	2	4	Batman se enfrenta a su mayor enemigo, el Joker, en una batalla por el alma de Gotham.
3	Titanic	1997-12-19	195	7.99	images/peliculas/titanic.jpg	3	3	Una historia de amor en el trágico hundimiento del Titanic.
1	Inception	2010-07-16	148	9.99	images/peliculas/inception.jpg	1	2	Un ladrón que roba secretos corporativos a través de la tecnología de los sueños.
15	Gitanic	2025-02-26	5	90	images/peliculas/1740922748645.png	4	5	Un barco lleno de gitanos
17	InsertPostJWT	2025-03-08	98	12	images/peliculas/1741466412915.png	7	3	inserción realizada después de hacer el jwt para los usuarios y cambiar un poco los planes
\.


--
-- TOC entry 4936 (class 0 OID 121180)
-- Dependencies: 222
-- Data for Name: studios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.studios (id, studio) FROM stdin;
1	Warner Bros
2	Universal Pictures
3	Paramount Pictures
4	Columbia Pictures
6	20th Century Studios
7	Walt Disney Pictures
8	Sony Pictures Entertainment
9	Metro-Goldwyn-Mayer (MGM)
10	Legendary Entertainment
11	Lionsgate Films
\.


--
-- TOC entry 4951 (class 0 OID 129355)
-- Dependencies: 237
-- Data for Name: tarjeta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tarjeta (id, numero_tarjeta, fecha_caducidad, numero_seguridad, fondos_disponibles, usuario_anterior_id) FROM stdin;
2	8765432187654321	2025-08-15 00:00:00	456	300.5	\N
3	1111222233334444	2027-07-10 00:00:00	789	1000	\N
4	5555666677778888	2024-11-30 00:00:00	321	50	\N
5	9999000011112222	2028-03-25 00:00:00	654	750.75	\N
45	1234567899876543	2025-03-30 01:00:00	321	2555.87	37
32	8888555577778888	2026-10-15 02:00:00	123	1500.75	\N
49	1234567899876545	2025-03-29 01:00:00	424	3001	39
34	6666333344445552	2027-09-16 02:00:00	999	2500	\N
51	1234567893456789	2025-03-27 01:00:00	626	2541	44
39	8888555577776789	2027-09-16 02:00:00	999	2500	12
1	1234567812345678	2026-12-01 01:00:00	123	5000	\N
42	1212121212121213	2026-10-11 02:00:00	123	1750.75	13
40	8888555577779876	2027-09-16 02:00:00	999	2500	12
41	1212121212121212	2026-10-10 02:00:00	123	1750.75	14
44	1212121212121221	2026-10-28 01:00:00	321	3750.75	13
43	1212121212121214	2026-10-10 02:00:00	123	1750.75	13
55	1234123443211234	2025-03-14 01:00:00	555	785	51
56	1234123443211235	2025-03-14 01:00:00	111	524.05	\N
57	9696878954561023	2025-04-03 02:00:00	012	309.91	\N
58	213564785239687	2025-04-30 02:00:00	212	402.66	\N
59	2145698578549632	2025-04-04 02:00:00	369	417.48	\N
\.


--
-- TOC entry 4953 (class 0 OID 129372)
-- Dependencies: 239
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuarios (id, nombre, apellidos, email, password, rol, tarjeta_id) FROM stdin;
2	Juan	Pérez	juan.perez@email.com	clave123	CLIENTE	2
3	María	Gómez	maria.gomez@email.com	password456	CLIENTE	3
4	Carlos	López	carlos.lopez@email.com	secreto789	CLIENTE	4
5	Ana	Martínez	ana.martinez@email.com	contra321	CLIENTE	5
50	cliente	cliente	cliente@cliente.pruebaveri	4263DyM5AgU1r5/pe1+tD0W8u/ZKgTVt/oMuDmzhuqI=	CLIENTE	\N
48	cliente	cliente	cliente@cliente.cliente	pguF1AmgHUYCP5B0HgG3lUOjyxugSOrvvl16Y2OAQ78=	CLIENTE	\N
10	Marco	Fernández	marco.fernandez@email.com	miPasswordSeguro	CLIENTE	32
9	Luis	Fernández	luis.fernandez@email.com	nuevaClave123	CLIENTE	34
12	Pepe	Rodriguez	pepe.rodriguez@email.com	miNuevaPasswordSeguro	CLIENTE	40
51	tarjeta	seña	contra@xn--sea-8ma.com	pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=	CLIENTE	56
52	pruebaValidacion	Encriptar	pve@pve.com	jSPPbIboNKeqbt7VTCbOK7LnSQNTjGG91dIZeZerL3I=	CLIENTE	\N
14	PepeNuevo	Rodriguez	pepenuevo.rodriguez@email.com	miPasswordSeguro	CLIENTE	41
15	DeletePrueba	Prueba	delete.prueba@email.com	deletePass	CLIENTE	\N
53	pruebaCompra	compra	compra@compra.compra	pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=	CLIENTE	57
54	compraDef	definitiva	compradf@compradf.com	jSPPbIboNKeqbt7VTCbOK7LnSQNTjGG91dIZeZerL3I=	CLIENTE	58
55	actTarjet	tarjet	actTarjet@tarjet.tarjeta	pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=	CLIENTE	59
13	DeletePrueba	Prueba	delete.prueba1@email.com	deletePass	CLIENTE	\N
17	JWTPrueba	JWT	jwt.prueba@email.com	JWTPass	CLIENTE	\N
18	JWTPrueba2	JWT	jwt2.prueba@email.com	JWTPass22	CLIENTE	\N
19	JWTPrueba3	JWT	jwt3.prueba@email.com	JWTPass3	CLIENTE	\N
26	JWT 	Registro	jwt.registro@email.com	o68CvN7phpSmelvJyWYgK1nmR3+BhsKL45Qqrz5n81c=	CLIENTE	\N
27	Prueba Token	Admin	jwt.admin@email.com	hoZzORJVhWHM1PioG42ZH0lpGg1RwTtQQix8WMwSO5M=	CLIENTE	\N
28	Prueba Token2	Admin2	jwt.admin2@email.com	CJARnWGEYHy19i3EUCs7228f2ARLbzJfMr11QWN8tRg=	CLIENTE	\N
29	Prueba Token3	Admin3	jwt.admin3@email.com	xih1EeXthPUdbQPfMLHKfUKaLjS4/L3uAWVJmExYN50=	CLIENTE	\N
30	Prueba Token4	Admin4	jwt.admin4@email.com	f8uDZL63WvWr6LlyLY0xSA+GqnoUMG0aUx0KtM0YQtE=	ADMIN	\N
31	Prueba Token5	Admin5	jwt.admin5@email.com	Ls/w7ZHYEpcMXng8y4E97sYhl9VbG6AxkWYC3nZq9X8=	ADMIN	\N
32	Prueba Token6	Admin6	jwt.admin6@email.com	GWUB+8xuS+YteG0F2nHSU6KTYnI35EYQvnnX96pxtPI=	ADMIN	\N
33	Prueba Admin	Admin	admin@email.com	O2Esdae1BIpDX7bsgeUv+S1teVqLWpwXBw9qY8l6U7I=	ADMIN	\N
34	Prueba Admin11	Admin	admin123@email.com	O2Esdae1BIpDX7bsgeUv+S1teVqLWpwXBw9qY8l6U7I=	ADMIN	\N
35	Prueba Cliente	Cliente	cliente23@email.com	NOQiJ46nRbXYe6ZZLw6j/jKi63WT9ZYKxy1wlPsSHz0=	CLIENTE	\N
49	cliente	cliente	cliente@cliente.pruebarol	j/iSg/HFT7NAGCts8AJrII4x/1L3h4zegUT0yoo8oYE=	CLIENTE	\N
44	a	a	a@a.a	a	CLIENTE	51
47	admin	admin	admin@admin.com	admin	ADMIN	\N
1	Admin	Admin	admin@admin.admin	JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=	ADMIN	1
\.


--
-- TOC entry 4975 (class 0 OID 0)
-- Dependencies: 219
-- Name: Actors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Actors_id_seq"', 26, true);


--
-- TOC entry 4976 (class 0 OID 0)
-- Dependencies: 221
-- Name: Studio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Studio_id_seq"', 12, true);


--
-- TOC entry 4977 (class 0 OID 0)
-- Dependencies: 240
-- Name: carrito_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.carrito_id_seq', 32, true);


--
-- TOC entry 4978 (class 0 OID 0)
-- Dependencies: 217
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 8, true);


--
-- TOC entry 4979 (class 0 OID 0)
-- Dependencies: 242
-- Name: compras_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.compras_id_seq', 25, true);


--
-- TOC entry 4980 (class 0 OID 0)
-- Dependencies: 227
-- Name: directors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.directors_id_seq', 14, true);


--
-- TOC entry 4981 (class 0 OID 0)
-- Dependencies: 223
-- Name: flags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.flags_id_seq', 21, true);


--
-- TOC entry 4982 (class 0 OID 0)
-- Dependencies: 225
-- Name: genre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.genre_id_seq', 16, true);


--
-- TOC entry 4983 (class 0 OID 0)
-- Dependencies: 229
-- Name: peliculas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.peliculas_id_seq', 18, true);


--
-- TOC entry 4984 (class 0 OID 0)
-- Dependencies: 215
-- Name: public.language_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."public.language_id_seq"', 13, true);


--
-- TOC entry 4985 (class 0 OID 0)
-- Dependencies: 236
-- Name: tarjeta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tarjeta_id_seq', 59, true);


--
-- TOC entry 4986 (class 0 OID 0)
-- Dependencies: 238
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuarios_id_seq', 55, true);


--
-- TOC entry 4731 (class 2606 OID 121178)
-- Name: actor Actors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actor
    ADD CONSTRAINT "Actors_pkey" PRIMARY KEY (id);


--
-- TOC entry 4733 (class 2606 OID 121187)
-- Name: studios Studio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studios
    ADD CONSTRAINT "Studio_pkey" PRIMARY KEY (id);


--
-- TOC entry 4763 (class 2606 OID 129548)
-- Name: carrito carrito_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carrito
    ADD CONSTRAINT carrito_pkey PRIMARY KEY (id);


--
-- TOC entry 4765 (class 2606 OID 129550)
-- Name: carrito carrito_usuario_id_pelicula_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carrito
    ADD CONSTRAINT carrito_usuario_id_pelicula_id_key UNIQUE (usuario_id, pelicula_id);


--
-- TOC entry 4729 (class 2606 OID 121169)
-- Name: categories category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 4767 (class 2606 OID 129568)
-- Name: compras compras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras
    ADD CONSTRAINT compras_pkey PRIMARY KEY (id);


--
-- TOC entry 4739 (class 2606 OID 121214)
-- Name: director directors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.director
    ADD CONSTRAINT directors_pkey PRIMARY KEY (id);


--
-- TOC entry 4735 (class 2606 OID 121196)
-- Name: flags flags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flags
    ADD CONSTRAINT flags_pkey PRIMARY KEY (id);


--
-- TOC entry 4737 (class 2606 OID 121205)
-- Name: genres genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genres
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- TOC entry 4753 (class 2606 OID 129451)
-- Name: tarjeta numero_tarjeta; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarjeta
    ADD CONSTRAINT numero_tarjeta UNIQUE (numero_tarjeta);


--
-- TOC entry 4749 (class 2606 OID 121283)
-- Name: pelicula_actor pelicula_actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_actor
    ADD CONSTRAINT pelicula_actor_pkey PRIMARY KEY (pelicula_id, actor_id);


--
-- TOC entry 4743 (class 2606 OID 121238)
-- Name: pelicula_director pelicula_director_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_director
    ADD CONSTRAINT pelicula_director_pkey PRIMARY KEY (pelicula_id, director_id);


--
-- TOC entry 4747 (class 2606 OID 121268)
-- Name: pelicula_flags pelicula_flags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_flags
    ADD CONSTRAINT pelicula_flags_pkey PRIMARY KEY (pelicula_id, flag_id);


--
-- TOC entry 4745 (class 2606 OID 121253)
-- Name: pelicula_genre pelicula_genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_genre
    ADD CONSTRAINT pelicula_genre_pkey PRIMARY KEY (pelicula_id, genre_id);


--
-- TOC entry 4751 (class 2606 OID 121298)
-- Name: pelicula_language pelicula_language_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_language
    ADD CONSTRAINT pelicula_language_pkey PRIMARY KEY (pelicula_id, language_id);


--
-- TOC entry 4741 (class 2606 OID 121223)
-- Name: peliculas peliculas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.peliculas
    ADD CONSTRAINT peliculas_pkey PRIMARY KEY (id);


--
-- TOC entry 4727 (class 2606 OID 121160)
-- Name: language public.language_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.language
    ADD CONSTRAINT "public.language_pkey" PRIMARY KEY (id);


--
-- TOC entry 4755 (class 2606 OID 129362)
-- Name: tarjeta tarjeta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarjeta
    ADD CONSTRAINT tarjeta_pkey PRIMARY KEY (id);


--
-- TOC entry 4757 (class 2606 OID 129468)
-- Name: usuarios usuarios_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_email_key UNIQUE (email);


--
-- TOC entry 4759 (class 2606 OID 129380)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 4761 (class 2606 OID 129384)
-- Name: usuarios usuarios_tarjeta_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_tarjeta_id_key UNIQUE (tarjeta_id);


--
-- TOC entry 4776 (class 2606 OID 121289)
-- Name: pelicula_actor actor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_actor
    ADD CONSTRAINT actor_id FOREIGN KEY (actor_id) REFERENCES public.actor(id);


--
-- TOC entry 4781 (class 2606 OID 129556)
-- Name: carrito carrito_pelicula_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carrito
    ADD CONSTRAINT carrito_pelicula_id_fkey FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id) ON DELETE CASCADE;


--
-- TOC entry 4782 (class 2606 OID 129551)
-- Name: carrito carrito_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.carrito
    ADD CONSTRAINT carrito_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id) ON DELETE CASCADE;


--
-- TOC entry 4768 (class 2606 OID 121229)
-- Name: peliculas category_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.peliculas
    ADD CONSTRAINT category_id FOREIGN KEY (category_id) REFERENCES public.categories(id) NOT VALID;


--
-- TOC entry 4783 (class 2606 OID 129574)
-- Name: compras compras_pelicula_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras
    ADD CONSTRAINT compras_pelicula_id_fkey FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id) ON DELETE SET NULL;


--
-- TOC entry 4784 (class 2606 OID 129579)
-- Name: compras compras_tarjeta_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras
    ADD CONSTRAINT compras_tarjeta_id_fkey FOREIGN KEY (tarjeta_id) REFERENCES public.tarjeta(id) ON DELETE SET NULL;


--
-- TOC entry 4785 (class 2606 OID 129569)
-- Name: compras compras_usuario_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.compras
    ADD CONSTRAINT compras_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuarios(id) ON DELETE SET NULL;


--
-- TOC entry 4770 (class 2606 OID 121244)
-- Name: pelicula_director director_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_director
    ADD CONSTRAINT director_id FOREIGN KEY (director_id) REFERENCES public.director(id);


--
-- TOC entry 4780 (class 2606 OID 129385)
-- Name: usuarios fk_tarjeta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT fk_tarjeta FOREIGN KEY (tarjeta_id) REFERENCES public.tarjeta(id) NOT VALID;


--
-- TOC entry 4774 (class 2606 OID 121274)
-- Name: pelicula_flags flag_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_flags
    ADD CONSTRAINT flag_id FOREIGN KEY (flag_id) REFERENCES public.flags(id);


--
-- TOC entry 4772 (class 2606 OID 121259)
-- Name: pelicula_genre genre_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_genre
    ADD CONSTRAINT genre_id FOREIGN KEY (genre_id) REFERENCES public.genres(id);


--
-- TOC entry 4778 (class 2606 OID 121304)
-- Name: pelicula_language language_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_language
    ADD CONSTRAINT language_id FOREIGN KEY (language_id) REFERENCES public.language(id);


--
-- TOC entry 4771 (class 2606 OID 121239)
-- Name: pelicula_director pelicula_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_director
    ADD CONSTRAINT pelicula_id FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id);


--
-- TOC entry 4773 (class 2606 OID 121254)
-- Name: pelicula_genre pelicula_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_genre
    ADD CONSTRAINT pelicula_id FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id);


--
-- TOC entry 4775 (class 2606 OID 121269)
-- Name: pelicula_flags pelicula_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_flags
    ADD CONSTRAINT pelicula_id FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id);


--
-- TOC entry 4777 (class 2606 OID 121284)
-- Name: pelicula_actor pelicula_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_actor
    ADD CONSTRAINT pelicula_id FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id);


--
-- TOC entry 4779 (class 2606 OID 121299)
-- Name: pelicula_language pelicula_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pelicula_language
    ADD CONSTRAINT pelicula_id FOREIGN KEY (pelicula_id) REFERENCES public.peliculas(id);


--
-- TOC entry 4769 (class 2606 OID 121224)
-- Name: peliculas studio_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.peliculas
    ADD CONSTRAINT studio_id FOREIGN KEY (studio_id) REFERENCES public.studios(id);


-- Completed on 2025-03-17 21:02:16

--
-- PostgreSQL database dump complete
--

