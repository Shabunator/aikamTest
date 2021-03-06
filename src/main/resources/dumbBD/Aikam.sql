PGDMP                         x            test    12.2    12.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                        1262    16585    test    DATABASE     �   CREATE DATABASE test WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';
    DROP DATABASE test;
                postgres    false            !           0    0    DATABASE test    COMMENT     $   COMMENT ON DATABASE test IS 'test';
                   postgres    false    2848            �            1259    24387    buyers    TABLE     �   CREATE TABLE public.buyers (
    id integer NOT NULL,
    firstname character varying(100),
    lastname character varying(100)
);
    DROP TABLE public.buyers;
       public         heap    postgres    false            �            1259    24385    buyers_id_seq    SEQUENCE     �   CREATE SEQUENCE public.buyers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.buyers_id_seq;
       public          postgres    false    203            "           0    0    buyers_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.buyers_id_seq OWNED BY public.buyers.id;
          public          postgres    false    202            �            1259    24395    products    TABLE     u   CREATE TABLE public.products (
    id integer NOT NULL,
    productname character varying(100),
    price numeric
);
    DROP TABLE public.products;
       public         heap    postgres    false            �            1259    24393    products_id_seq    SEQUENCE     �   CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.products_id_seq;
       public          postgres    false    205            #           0    0    products_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;
          public          postgres    false    204            �            1259    24406 	   purchases    TABLE     v   CREATE TABLE public.purchases (
    id integer NOT NULL,
    buyerid integer,
    productid integer,
    date date
);
    DROP TABLE public.purchases;
       public         heap    postgres    false            �            1259    24404    purchases_id_seq    SEQUENCE     �   CREATE SEQUENCE public.purchases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.purchases_id_seq;
       public          postgres    false    207            $           0    0    purchases_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;
          public          postgres    false    206            �
           2604    24390 	   buyers id    DEFAULT     f   ALTER TABLE ONLY public.buyers ALTER COLUMN id SET DEFAULT nextval('public.buyers_id_seq'::regclass);
 8   ALTER TABLE public.buyers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202    203            �
           2604    24398    products id    DEFAULT     j   ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);
 :   ALTER TABLE public.products ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    205    205            �
           2604    24409    purchases id    DEFAULT     l   ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);
 ;   ALTER TABLE public.purchases ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    207    206    207                      0    24387    buyers 
   TABLE DATA           9   COPY public.buyers (id, firstname, lastname) FROM stdin;
    public          postgres    false    203   �                 0    24395    products 
   TABLE DATA           :   COPY public.products (id, productname, price) FROM stdin;
    public          postgres    false    205                    0    24406 	   purchases 
   TABLE DATA           A   COPY public.purchases (id, buyerid, productid, date) FROM stdin;
    public          postgres    false    207   �       %           0    0    buyers_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.buyers_id_seq', 21, true);
          public          postgres    false    202            &           0    0    products_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.products_id_seq', 31, true);
          public          postgres    false    204            '           0    0    purchases_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.purchases_id_seq', 6, true);
          public          postgres    false    206            �
           2606    24392    buyers buyers_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.buyers
    ADD CONSTRAINT buyers_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.buyers DROP CONSTRAINT buyers_pkey;
       public            postgres    false    203            �
           2606    24403    products products_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public            postgres    false    205            �
           2606    24411    purchases purchases_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_pkey;
       public            postgres    false    207            �
           2606    24412     purchases purchases_buyerid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_buyerid_fkey FOREIGN KEY (buyerid) REFERENCES public.buyers(id);
 J   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_buyerid_fkey;
       public          postgres    false    203    2704    207            �
           2606    24417 "   purchases purchases_productid_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_productid_fkey FOREIGN KEY (productid) REFERENCES public.products(id);
 L   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_productid_fkey;
       public          postgres    false    207    2706    205               W  x�eQ�N�P|>�c����?�-F} �*Fb"D���Ph�7����{�Hx�g�ݙ���f(Q`��C�c�)ZԒI�[�O>ktL�~uV���9��bRb�7�[�㟥�;��0\֍aOD��W�yZ��C�*f'��1)�vd������1�62	X��hI�5G��-�d$i�9�$������)W�Qt��ʘK*�֎�3��E�U(Й��V�s�!�L��jIr��W�tå�����A���f��#�^@_.kz�f=,䖭��eI~�itv�9��K�����JsZٝ	q�U�L��]�E��~���cX�=�;��g�������n.����I�8         �  x�]R[N�@��O���&��]8Li�
j%(|� ��@|��m��7b�I[����x�kH^e-�,(Nؒ<H�OPi�D�c-4�+?(e��g��ɻ,��ǲ����`�o^�/H�~
�Z�(.(�9%����5%�3h�!�Kr	�$o~��-iϟv�Y�����������v��T��#6����	�±���
$�{��y+�Ծ�d�E8����[JRT\H�5�	�m�U� ���<e@���#tG�~ �0
��� ����qB;ֶf!��0�jI;Y�Z�>u��	���;�FVd�ckt��_�2�+X�g�Q ��dF���_��P��T�X���F�`3��}�e�B������k��P�5�'-5&��{�~Rp�����*�el�rlTN�� J���=5(��Q1��r         ?   x�Mʹ�0���Ł�ǀw��sD���pBB7�e��t�+�h�_9L�)�^9e�RSއ��f�     