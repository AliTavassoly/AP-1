PGDMP                         x           hearth-stone    10.13     12.3 (Ubuntu 12.3-1.pgdg18.04+1)                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16723    hearth-stone    DATABASE     �   CREATE DATABASE "hearth-stone" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE "hearth-stone";
                postgres    false            �            1259    17245    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public          postgres    false                       0    0    hibernate_sequence    SEQUENCE SET     @   SELECT pg_catalog.setval('public.hibernate_sequence', 3, true);
          public          postgres    false    196           