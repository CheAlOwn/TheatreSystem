--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6
-- Dumped by pg_dump version 16.6

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: actors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actors (
    actor_id integer NOT NULL,
    employee_id integer NOT NULL,
    height numeric(5,2),
    voice_type character varying(50),
    CONSTRAINT actors_height_check CHECK ((height > (0)::numeric))
);


ALTER TABLE public.actors OWNER TO postgres;

--
-- Name: actors_actor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.actors_actor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.actors_actor_id_seq OWNER TO postgres;

--
-- Name: actors_actor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.actors_actor_id_seq OWNED BY public.actors.actor_id;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    employee_id integer NOT NULL,
    last_name character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    middle_name character varying(255),
    birthday date NOT NULL,
    gender character(1),
    hire_year integer NOT NULL,
    category character varying(50),
    post character varying(255),
    salary numeric(10,2) NOT NULL,
    phone character varying(15),
    address text,
    experience integer,
    children_count integer,
    CONSTRAINT employees_category_check CHECK (((category)::text = ANY ((ARRAY['Актер'::character varying, 'Музыкант'::character varying, 'Служащий'::character varying, 'Постановщик'::character varying])::text[]))),
    CONSTRAINT employees_children_count_check CHECK ((children_count >= 0)),
    CONSTRAINT employees_experience_check CHECK ((experience >= 0)),
    CONSTRAINT employees_gender_check CHECK ((gender = ANY (ARRAY['М'::bpchar, 'Ж'::bpchar])))
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- Name: employees_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employees_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employees_employee_id_seq OWNER TO postgres;

--
-- Name: employees_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employees_employee_id_seq OWNED BY public.employees.employee_id;


--
-- Name: musicians; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.musicians (
    musician_id integer NOT NULL,
    employee_id integer NOT NULL,
    instrument character varying(100) NOT NULL
);


ALTER TABLE public.musicians OWNER TO postgres;

--
-- Name: musicians_musician_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.musicians_musician_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.musicians_musician_id_seq OWNER TO postgres;

--
-- Name: musicians_musician_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.musicians_musician_id_seq OWNED BY public.musicians.musician_id;


--
-- Name: performances; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.performances (
    performance_id integer NOT NULL,
    name character varying(255) NOT NULL,
    genre character varying(100),
    author character varying(255),
    director_id integer,
    set_designer_id integer,
    conductor_id integer
);


ALTER TABLE public.performances OWNER TO postgres;

--
-- Name: performances_performance_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.performances_performance_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.performances_performance_id_seq OWNER TO postgres;

--
-- Name: performances_performance_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.performances_performance_id_seq OWNED BY public.performances.performance_id;


--
-- Name: repertoires; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repertoires (
    id integer NOT NULL,
    performance_id integer NOT NULL,
    show_date date NOT NULL,
    show_time time without time zone NOT NULL,
    is_premiere boolean NOT NULL,
    period text,
    price numeric(10,2) NOT NULL
);


ALTER TABLE public.repertoires OWNER TO postgres;

--
-- Name: repertoire_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repertoire_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.repertoire_id_seq OWNER TO postgres;

--
-- Name: repertoire_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repertoire_id_seq OWNED BY public.repertoires.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    role_name character varying(255) NOT NULL,
    performance_id integer NOT NULL,
    actor_id integer,
    understudy_id integer
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tickets (
    ticket_id integer NOT NULL,
    repertoire_id integer NOT NULL,
    seat character varying(50) NOT NULL,
    price numeric(10,2) NOT NULL,
    status character varying(50),
    sale_date date,
    CONSTRAINT tickets_status_check CHECK (((status)::text = ANY ((ARRAY['Доступен'::character varying, 'Продан'::character varying, 'Забронирован'::character varying])::text[])))
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- Name: tickets_ticket_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tickets_ticket_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tickets_ticket_id_seq OWNER TO postgres;

--
-- Name: tickets_ticket_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tickets_ticket_id_seq OWNED BY public.tickets.ticket_id;


--
-- Name: tours; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tours (
    id integer NOT NULL,
    employee_id integer NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    location character varying(255) NOT NULL
);


ALTER TABLE public.tours OWNER TO postgres;

--
-- Name: tours_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tours_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tours_id_seq OWNER TO postgres;

--
-- Name: tours_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tours_id_seq OWNED BY public.tours.id;


--
-- Name: actors actor_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actors ALTER COLUMN actor_id SET DEFAULT nextval('public.actors_actor_id_seq'::regclass);


--
-- Name: employees employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees ALTER COLUMN employee_id SET DEFAULT nextval('public.employees_employee_id_seq'::regclass);


--
-- Name: musicians musician_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.musicians ALTER COLUMN musician_id SET DEFAULT nextval('public.musicians_musician_id_seq'::regclass);


--
-- Name: performances performance_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.performances ALTER COLUMN performance_id SET DEFAULT nextval('public.performances_performance_id_seq'::regclass);


--
-- Name: repertoires id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repertoires ALTER COLUMN id SET DEFAULT nextval('public.repertoire_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: tickets ticket_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets ALTER COLUMN ticket_id SET DEFAULT nextval('public.tickets_ticket_id_seq'::regclass);


--
-- Name: tours id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours ALTER COLUMN id SET DEFAULT nextval('public.tours_id_seq'::regclass);


--
-- Data for Name: actors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.actors (actor_id, employee_id, height, voice_type) FROM stdin;
1	1	1.80	Баритон
2	5	1.75	Тенор
5	17	1.70	Сопрано
6	21	1.85	Баритон
7	24	1.68	Меццо-сопрано
9	32	1.74	Бас
3	9	1.79	Баритон
8	28	1.77	Бас
4	13	1.82	Меццо-сопрано
14	46	2.00	Баритон
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (employee_id, last_name, first_name, middle_name, birthday, gender, hire_year, category, post, salary, phone, address, experience, children_count) FROM stdin;
1	Иванов	Иван	Иванович	1980-05-15	М	2005	Актер	Актер	50000.00	89160001111	Москва, ул. Ленина, д. 1	15	2
2	Петрова	Мария	Александровна	1990-08-20	Ж	2010	Музыкант	Музыкант	35000.00	89160002222	Санкт-Петербург, ул. Пушкина, д. 10	12	1
3	Смирнов	Дмитрий	Николаевич	1975-02-25	М	2000	Служащий	Менеджер	45000.00	89160003333	Казань, ул. Победы, д. 5	20	3
4	Кузнецова	Ольга	Викторовна	1985-12-10	Ж	2008	Постановщик	Режиссер-постановщик	60000.00	89160004444	Воронеж, ул. Мира, д. 3	16	0
5	Давыдов	Алексей	Игоревич	1992-11-18	М	2015	Актер	Актер	28000.00	89160005555	Екатеринбург, ул. Карла Маркса, д. 8	9	1
6	Соловьева	Ирина	Евгеньевна	1993-04-07	Ж	2017	Музыкант	Музыкант	32000.00	89160006666	Новосибирск, ул. Ленина, д. 12	7	0
7	Михайлов	Сергей	Юрьевич	1980-01-05	М	2003	Служащий	Директор	70000.00	89160007777	Ростов-на-Дону, ул. Гагарина, д. 15	21	2
8	Попова	Елена	Сергеевна	1991-09-22	Ж	2011	Постановщик	Художник-постановщик	38000.00	89160008888	Краснодар, ул. Чернышевского, д. 22	13	1
9	Федоров	Максим	Анатольевич	1982-03-30	М	2006	Актер	Актер	50000.00	89160009999	Уфа, ул. Чехова, д. 7	18	0
10	Григорьева	Наталья	Вячеславовна	1987-06-10	Ж	2013	Музыкант	Музыкант	27000.00	89160010000	Тюмень, ул. Московская, д. 11	11	2
11	Старков	Роман	Юрьевич	1994-02-12	М	2018	Служащий	Кассир	25000.00	89160011111	Самара, ул. Ленина, д. 14	6	1
12	Иванова	Анастасия	Петровна	1988-10-04	Ж	2009	Постановщик	Продюсер	52000.00	89160012222	Волгоград, ул. Горького, д. 18	15	3
13	Захарова	Татьяна	Владимировна	1984-07-14	Ж	2007	Актер	Актер	38000.00	89160013333	Мурманск, ул. Ленина, д. 13	17	0
14	Орлов	Владимир	Сергеевич	1976-03-11	М	1999	Музыкант	Музыкант	45000.00	89160014444	Тула, ул. Калинина, д. 10	25	1
15	Сидорова	Марина	Константиновна	1990-05-23	Ж	2010	Служащий	Менеджер по продажам	30000.00	89160015555	Сочи, ул. Роза, д. 8	12	0
16	Федорова	Екатерина	Дмитриевна	1995-07-08	Ж	2019	Постановщик	Сценарист	31000.00	89160016666	Нижний Новгород, ул. Гоголя, д. 2	5	0
17	Тимофеева	Елизавета	Валерьевна	1997-01-25	Ж	2020	Актер	Актер	20000.00	89160017777	Челябинск, ул. Гагарина, д. 6	4	1
18	Ковалев	Андрей	Михайлович	1983-03-20	М	2008	Музыкант	Музыкант	32000.00	89160018888	Ярославль, ул. Победы, д. 19	16	2
19	Гончарова	Людмила	Петровна	1994-11-11	Ж	2016	Служащий	Секретарь	26000.00	89160019999	Кострома, ул. Пролетарская, д. 23	8	0
20	Андреева	Вера	Григорьевна	1993-12-05	Ж	2014	Постановщик	Художник-постановщик	42000.00	89160020000	Владивосток, ул. Красной Армии, д. 14	10	1
21	Щербакова	Алла	Станиславовна	1980-09-18	Ж	2002	Актер	Актер	50000.00	89160021111	Красноярск, ул. Комсомольская, д. 20	22	2
22	Борисова	Юлия	Евгеньевна	1986-10-13	Ж	2012	Музыкант	Музыкант	28000.00	89160022222	Белгород, ул. Крылова, д. 9	12	0
24	Яковлева	Тамара	Игоревна	1983-09-22	Ж	2008	Актер	Актер	48000.00	89160024444	Ижевск, ул. Победы, д. 5	16	1
25	Смирнова	Елена	Владимировна	1999-11-05	Ж	2021	Музыкант	Музыкант	32000.00	89160025555	Томск, ул. Октябрьская, д. 8	3	0
26	Филатов	Петр	Николаевич	1980-12-20	М	2004	Служащий	Аналитик	46000.00	89160026666	Архангельск, ул. Ленина, д. 10	19	2
27	Егорова	Виктория	Викторовна	1994-02-01	Ж	2016	Постановщик	Режиссер-постановщик	53000.00	89160027777	Чита, ул. Мира, д. 12	8	1
28	Бурова	Надежда	Ивановна	1987-07-14	Ж	2007	Актер	Актер	47000.00	89160028888	Владикавказ, ул. Пушкина, д. 14	17	0
29	Герасимова	Екатерина	Сергеевна	1991-12-25	Ж	2013	Музыкант	Музыкант	30000.00	89160029999	Нижний Новгород, ул. Чехова, д. 9	9	2
30	Лебедев	Анатолий	Вячеславович	1981-03-18	М	2006	Служащий	Главный бухгалтер	65000.00	89160030000	Ставрополь, ул. Советская, д. 5	18	3
31	Коваленко	Тимур	Андреевич	1992-10-04	М	2017	Постановщик	Художник-постановщик	45000.00	89160031111	Симферополь, ул. Маяковского, д. 7	6	1
32	Шмидт	Александра	Васильевна	1983-08-10	Ж	2010	Актер	Актер	51000.00	89160032222	Калининград, ул. Петрова, д. 10	15	2
33	Татаринова	Ольга	Станиславовна	1995-03-20	Ж	2019	Музыкант	Музыкант	32000.00	89160033333	Нижний Тагил, ул. Чапаева, д. 14	5	0
34	Волкова	Вера	Анатольевна	1984-07-05	Ж	2002	Служащий	Руководитель отдела	70000.00	89160034444	Казань, ул. Гагарина, д. 2	22	1
35	Чернова	Елена	Вячеславовна	1985-05-14	Ж	2009	Постановщик	Режиссер-постановщик	59000.00	89160035555	Тамбов, ул. Ленина, д. 3	15	2
36	Зайцева	Анна	Петровна	1988-03-13	Ж	2011	Актер	Актер	45000.00	89160036666	Курск, ул. Толстого, д. 8	13	3
37	Мельников	Константин	Олегович	1979-06-17	М	2004	Музыкант	Музыкант	40000.00	89160037777	Уфа, ул. Ломоносова, д. 12	20	1
38	Варламова	Маргарита	Андреевна	1983-09-03	Ж	2010	Служащий	Офис-менеджер	35000.00	89160038888	Астрахань, ул. Кирова, д. 6	14	1
39	Колесников	Игорь	Дмитриевич	1990-01-21	М	2016	Постановщик	Дирижер-постановщик	60000.00	89160039999	Магнитогорск, ул. Молодежная, д. 9	8	2
40	Рябова	Екатерина	Игоревна	1996-08-15	Ж	2019	Актер	Актер	22000.00	89160040000	Тула, ул. Розы, д. 3	5	0
41	Гаврилова	Полина	Анатольевна	1985-11-09	Ж	2006	Музыкант	Музыкант	33000.00	89160041111	Владикавказ, ул. Красная, д. 5	18	1
23	Николаев	Юрий	Михайлович	1990-04-14	Ж	2015	Постановщик	Дирижер-постановщик	55000.00	89160023333	Пермь, ул. Строителей, д. 17	10	2
46	Дроздов	Михаил	Васильевич	2001-01-03	М	2024	Актер	Актер	30000.00	89960067070	ул. Пушкина, д. 8	1	0
\.


--
-- Data for Name: musicians; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.musicians (musician_id, employee_id, instrument) FROM stdin;
1	2	Гитара
2	6	Фортепиано
4	14	Тромбон
5	18	Виолончель
6	22	Флейта
7	25	Трубка
8	29	Акустическая гитара
9	33	Кларнет
11	41	Скрипка
3	10	Скрипка
\.


--
-- Data for Name: performances; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.performances (performance_id, name, genre, author, director_id, set_designer_id, conductor_id) FROM stdin;
1	Король Лир	Трагедия	Уильям Шекспир	4	8	23
2	Севильский цирюльник	Опера	Дж. Россини	35	20	23
3	Лебединое озеро	Балет	П. Чайковский	4	31	39
4	Ромео и Джульетта	Трагедия	У. Шекспир	27	8	23
5	Травиата	Опера	Дж. Верди	35	31	39
6	Кармен	Опера	Ж. Бизе	4	20	39
7	Щелкунчик	Балет	П. Чайковский	4	31	23
8	Гроза	Трагедия	А.Н. Островский	35	8	39
9	Ночь в опере	Опера	Г. Верди	27	31	23
11	Тоска	Опера	Дж. Пуччини	4	20	39
12	Мадам Баттерфляй	Опера	Дж. Пуччини	4	31	39
13	Сельская честь	Опера	П. Масканьи	27	8	23
14	Летучая мышь	Опера	И. Штраус	27	20	39
15	Фигаро	Опера	В. Моцарт	35	31	23
10	Сон в летнюю ночь	Комедия	У. Шекспир	35	20	23
\.


--
-- Data for Name: repertoires; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repertoires (id, performance_id, show_date, show_time, is_premiere, period, price) FROM stdin;
1	1	2024-01-10	19:00:00	t	2024-01-10 - 2024-01-20	500.00
3	3	2024-02-01	20:00:00	t	2024-02-01 - 2024-02-10	450.00
4	4	2024-02-05	19:30:00	f	2024-02-05 - 2024-02-15	550.00
5	5	2024-02-10	18:30:00	t	2024-02-10 - 2024-02-20	650.00
6	6	2024-02-20	20:00:00	f	2024-02-20 - 2024-03-01	600.00
7	7	2024-03-01	19:00:00	t	2024-03-01 - 2024-03-10	700.00
9	9	2024-03-15	20:00:00	t	2024-03-15 - 2024-03-25	650.00
11	15	2025-01-03	17:20:00	t	2025-01-03 - 2025-01-13	700.00
2	2	2024-01-16	18:30:00	t	2024-01-15 - 2024-01-25	650.00
14	9	2025-01-09	12:40:00	t	2025-01-08 - 2025-01-18	400.00
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, role_name, performance_id, actor_id, understudy_id) FROM stdin;
1	Король Лир	1	1	2
2	Глостер	1	3	4
3	Эдгар	1	6	7
4	Эдмунд	1	8	9
5	Фигаро	2	1	2
6	Розина	2	5	6
7	Граф Альмавива	2	8	7
8	Доктор Бартоло	2	3	4
9	Одетта	3	5	6
10	Сигефрид	3	1	2
11	Ротбарта	3	3	4
12	Одетт	3	7	8
13	Ромео	4	2	3
14	Джульетта	4	5	6
15	Меркуцио	4	7	8
16	Тибальт	4	4	5
17	Виолетта	5	5	6
18	Альфредо	5	8	7
19	Жерома	5	6	1
20	Граф Ди Лира	5	3	4
21	Кармен	6	5	6
22	Дон Хозе	6	1	2
23	Эскамильо	6	8	7
24	Микаэла	6	4	3
25	Клара	7	5	6
26	Щелкунчик	7	1	2
27	Петрушка	7	3	4
28	Лариса	7	7	8
29	Катерина	8	5	6
30	Борис	8	1	2
31	Семен	8	6	7
32	Митрофан	8	8	3
33	Граф Альмавива	9	8	7
34	Розина	9	5	6
35	Фигаро	9	1	2
36	Барбарино	9	6	3
38	Петр	10	1	2
39	Тимон	10	7	3
40	Ариэль	10	4	6
41	Тоска	11	5	6
42	Каварадосси	11	1	2
43	Скарпиа	11	8	7
44	Джорджи	11	6	3
45	Чио-Чио-Сан	12	5	6
46	Пинкертон	12	1	2
47	Шарплес	12	8	7
49	Сильвия	13	5	6
50	Тонди	13	1	2
51	Франсеско	13	7	8
52	Мариано	13	6	3
53	Альфред	14	8	7
54	Софи	14	5	6
55	Графиня	14	1	2
56	Ирма	14	6	3
58	Сюзанна	15	5	6
59	Граф	15	8	7
60	Графиня	15	4	6
61	Лопух	15	5	6
62	Лопух	14	5	6
64	Сузуки	10	4	5
\.


--
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tickets (ticket_id, repertoire_id, seat, price, status, sale_date) FROM stdin;
1	1	A1	5000.00	Доступен	2024-12-01
2	1	A2	5000.00	Продан	2024-12-02
3	1	B1	3000.00	Доступен	2024-12-01
4	1	B2	3000.00	Забронирован	2024-12-03
5	1	C1	1500.00	Продан	2024-12-04
7	2	A1	5000.00	Забронирован	2024-12-05
8	2	A2	5000.00	Продан	2024-12-06
9	2	B1	3000.00	Доступен	2024-12-07
10	2	B2	3000.00	Продан	2024-12-08
11	2	C1	1500.00	Доступен	2024-12-09
12	2	C2	1500.00	Забронирован	2024-12-10
13	3	A1	5000.00	Продан	2024-12-11
14	3	A2	5000.00	Забронирован	2024-12-12
15	3	B1	3000.00	Доступен	2024-12-13
16	3	B2	3000.00	Продан	2024-12-14
17	3	C1	1500.00	Доступен	2024-12-15
18	3	C2	1500.00	Забронирован	2024-12-16
19	4	A1	5000.00	Доступен	2024-12-17
20	4	A2	5000.00	Продан	2024-12-18
21	4	B1	3000.00	Доступен	2024-12-19
22	4	B2	3000.00	Забронирован	2024-12-20
23	4	C1	1500.00	Продан	2024-12-21
24	4	C2	1500.00	Доступен	\N
25	5	A1	5000.00	Продан	2024-12-22
26	5	A2	5000.00	Доступен	2024-12-23
27	5	B1	3000.00	Доступен	2024-12-24
28	5	B2	3000.00	Продан	2024-12-25
29	5	C1	1500.00	Забронирован	2024-12-26
30	5	C2	1500.00	Доступен	2024-12-27
\.


--
-- Data for Name: tours; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tours (id, employee_id, start_date, end_date, location) FROM stdin;
1	1	2024-01-10	2024-01-20	Москва
2	2	2024-02-05	2024-02-15	Санкт-Петербург
3	4	2024-03-01	2024-03-10	Екатеринбург
4	5	2024-03-15	2024-03-25	Новосибирск
5	6	2024-04-01	2024-04-10	Казань
6	8	2024-04-20	2024-04-30	Нижний Новгород
7	9	2024-05-05	2024-05-15	Самара
8	10	2024-05-20	2024-05-30	Ростов-на-Дону
9	12	2024-06-01	2024-06-10	Красноярск
10	13	2024-06-15	2024-06-25	Воронеж
11	14	2024-07-01	2024-07-10	Уфа
12	16	2024-07-15	2024-07-25	Пермь
13	17	2024-08-01	2024-08-10	Волгоград
14	18	2024-08-15	2024-08-25	Сочи
15	20	2024-09-01	2024-09-10	Калининград
17	22	2024-10-01	2024-10-10	Челябинск
18	23	2024-10-15	2024-10-25	Иркутск
19	24	2024-11-01	2024-11-10	Омск
20	25	2024-11-15	2024-11-25	Барнаул
21	27	2024-12-01	2024-12-10	Хабаровск
22	28	2024-12-15	2024-12-25	Владивосток
23	29	2025-01-01	2025-01-10	Ярославль
24	31	2025-01-15	2025-01-25	Томск
25	32	2025-02-01	2025-02-10	Иваново
26	33	2025-02-15	2025-02-25	Саратов
27	35	2025-03-01	2025-03-10	Тверь
28	36	2025-03-15	2025-03-25	Кострома
29	37	2025-04-01	2025-04-10	Псков
30	39	2025-04-15	2025-04-25	Архангельск
31	40	2025-05-01	2025-05-10	Мурманск
32	41	2025-05-15	2025-05-25	Астрахань
\.


--
-- Name: actors_actor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.actors_actor_id_seq', 14, true);


--
-- Name: employees_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employees_employee_id_seq', 46, true);


--
-- Name: musicians_musician_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.musicians_musician_id_seq', 14, true);


--
-- Name: performances_performance_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.performances_performance_id_seq', 16, true);


--
-- Name: repertoire_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repertoire_id_seq', 14, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 65, true);


--
-- Name: tickets_ticket_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tickets_ticket_id_seq', 34, true);


--
-- Name: tours_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tours_id_seq', 33, true);


--
-- Name: actors actors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actors
    ADD CONSTRAINT actors_pkey PRIMARY KEY (actor_id);


--
-- Name: musicians employee_id_musician_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.musicians
    ADD CONSTRAINT employee_id_musician_unique UNIQUE (employee_id);


--
-- Name: actors employee_id_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actors
    ADD CONSTRAINT employee_id_unique UNIQUE (employee_id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (employee_id);


--
-- Name: musicians musicians_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.musicians
    ADD CONSTRAINT musicians_pkey PRIMARY KEY (musician_id);


--
-- Name: performances performances_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.performances
    ADD CONSTRAINT performances_pkey PRIMARY KEY (performance_id);


--
-- Name: repertoires repertoire_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repertoires
    ADD CONSTRAINT repertoire_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: tickets tickets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pkey PRIMARY KEY (ticket_id);


--
-- Name: tours tours_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tours_pkey PRIMARY KEY (id);


--
-- Name: roles unique_role_performance_actor; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT unique_role_performance_actor UNIQUE (role_name, performance_id, actor_id, understudy_id);


--
-- Name: roles fk_actor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT fk_actor FOREIGN KEY (actor_id) REFERENCES public.actors(actor_id);


--
-- Name: performances fk_conductor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.performances
    ADD CONSTRAINT fk_conductor FOREIGN KEY (conductor_id) REFERENCES public.employees(employee_id);


--
-- Name: performances fk_director; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.performances
    ADD CONSTRAINT fk_director FOREIGN KEY (director_id) REFERENCES public.employees(employee_id);


--
-- Name: actors fk_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actors
    ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES public.employees(employee_id);


--
-- Name: musicians fk_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.musicians
    ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES public.employees(employee_id);


--
-- Name: tours fk_employee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES public.employees(employee_id);


--
-- Name: repertoires fk_performance; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repertoires
    ADD CONSTRAINT fk_performance FOREIGN KEY (performance_id) REFERENCES public.performances(performance_id);


--
-- Name: roles fk_performance; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT fk_performance FOREIGN KEY (performance_id) REFERENCES public.performances(performance_id);


--
-- Name: tickets fk_repertoire; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT fk_repertoire FOREIGN KEY (repertoire_id) REFERENCES public.repertoires(id);


--
-- Name: performances fk_set_designer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.performances
    ADD CONSTRAINT fk_set_designer FOREIGN KEY (set_designer_id) REFERENCES public.employees(employee_id);


--
-- Name: roles fk_understudy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT fk_understudy FOREIGN KEY (understudy_id) REFERENCES public.actors(actor_id);


--
-- PostgreSQL database dump complete
--

