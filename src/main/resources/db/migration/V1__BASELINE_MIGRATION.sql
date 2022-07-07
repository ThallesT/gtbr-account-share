CREATE TABLE IF NOT EXISTS account_share
(
    id uuid NOT NULL,
    authenticator boolean,
    created_at timestamp,
    owner varchar(255),
    password varchar(255),
    platform varchar(255),
    username varchar(255),
    CONSTRAINT account_share_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS request
(
    id uuid NOT NULL,
    channel_id varchar(255),
    channel_name varchar(255),
    requested_at timestamp,
    user_discord_id varchar(255),
    user_discord_tag varchar(255),
    account_share_id uuid,
    CONSTRAINT request_pkey PRIMARY KEY (id),
    CONSTRAINT account_share_fk FOREIGN KEY (account_share_id)
    REFERENCES account_share (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS thumbnails
(
    id uuid NOT NULL,
    image_url varchar(255),
    platform varchar(255),
    CONSTRAINT thumbnails_pkey PRIMARY KEY (id)
);
INSERT INTO thumbnails (id, image_url, platform) VALUES ('05d3aa37-eb6a-4da1-af37-ce0b8858d205', 'https://assets.b9.com.br/wp-content/uploads/2016/06/netflix-logo-thumb.jpg', 'netflix');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('2aeac0dc-4ddb-4394-9121-fd988efbc790', 'https://www.lavoz.com.ar/resizer/ilqbfdfGkmGqLyfL8dXKqy1kgjM=/1023x682/smart/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/2MAHHX3HDJGGZJV7WN6FPCN7JA.jpg', 'star+');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('4705112f-b5b4-4bfc-bd22-5b4df1ea92cc', 'http://disneyplusbrasil.com.br/wp-content/uploads/2021/06/DisneyPlus-Logo-1024x576.jpg', 'disney+');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('4a3a3108-b524-46b9-b184-97f131843d3a', 'https://hbomax-images.warnermediacdn.com/2020-05/square%20social%20logo%20400%20x%20400_0.png', 'hbo');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('4dc33a7a-936a-4620-b773-8b325e1b6329', 'http://store-images.s-microsoft.com/image/apps.44134.9007199266244356.8c8f8b98-231f-43f4-b251-7e5c4931b8a2.0a5801df-68bf-4fb5-888b-40f49a061c80', 'crunchyroll');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('66121173-79b0-468e-a7b5-7aaec8d833fb', 'https://hbomax-images.warnermediacdn.com/2020-05/square%20social%20logo%20400%20x%20400_0.png','hbomax');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('6a939828-339d-4e96-b009-902460c49d0c', 'https://t2.tudocdn.net/235304?w=1920', 'telecineplay');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('6ce06e46-514b-4383-858b-a035c7eef3f0', 'https://www.paramountplus.com/assets/pplus/PPlus_Logo_1200x630_BLUE.png','paramountplus');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('c72ebbd9-14c9-4c55-8d71-4909c1f859ba', 'https://gkpb.com.br/wp-content/uploads/2018/08/novo-logo-identidade-visual-globo-play.png', 'globoplay');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('dbffff55-5491-43ee-b5f7-c0d422a80150', 'http://disneyplusbrasil.com.br/wp-content/uploads/2021/06/DisneyPlus-Logo-1024x576.jpg', 'disney');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('f7916c5e-02b7-4a21-9103-a0bbce7df013', 'https://www.lavoz.com.ar/resizer/ilqbfdfGkmGqLyfL8dXKqy1kgjM=/1023x682/smart/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/2MAHHX3HDJGGZJV7WN6FPCN7JA.jpg', 'starplus');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('fc4fdc45-a6fc-4bcd-828a-5c645bc10e4f', 'http://disneyplusbrasil.com.br/wp-content/uploads/2021/06/DisneyPlus-Logo-1024x576.jpg', 'disneyplus');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('fd2acb97-98bf-403f-950b-6cd09c86a54f', 'https://images-na.ssl-images-amazon.com/images/I/41mpv9rBhmL.png', 'primevideo');
INSERT INTO thumbnails (id, image_url, platform) VALUES ('fe46cff9-af8b-4f55-8817-d3fd5fe0c034', 'https://play-lh.googleusercontent.com/-BxxbuwD4cVLcXBy-7WSCeWDfg_Cn5PCaWrLy87zN5nh-c0fOYdTeWV5Pf-8qogsvZxr' , 'f1tv');
