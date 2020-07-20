CREATE DATABASE user_store
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'ru_UA.UTF-8'
    LC_CTYPE = 'ru_UA.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;



CREATE TABLE public.users
(
    id            integer                                            NOT NULL,
    "firstName"   character varying(30) COLLATE pg_catalog."default" NOT NULL,
    "secondName"  character varying(30) COLLATE pg_catalog."default" NOT NULL,
    salary        double precision                                   NOT NULL,
    "dateOfBirth" date                                               NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;



SELECT id, "firstName", "secondName", salary, "dateOfBirth"
FROM public.users;

INSERT INTO public.users(id, "firstName", "secondName", salary, "dateOfBirth")
VALUES (?, ?, ?, ?, ?);

UPDATE public.users
SET id=?,
    "firstName"=?,
    "secondName"=?,
    salary=?,
    "dateOfBirth"=?
WHERE <condition>;

DELETE
FROM public.users
WHERE <condition>;

