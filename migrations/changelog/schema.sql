--liquibase formatted sql

--changeset VoroninEgor:create-tgchats-table

create table tgchats (
    id serial PRIMARY KEY,
    created_at timestamp NOT NULL
);

--changeset VoroninEgor:create-links-table

create table links (
  id serial PRIMARY KEY,
  url text UNIQUE NOT NULL,
  updated_at timestamp NOT NULL

);

--changeset VoroninEgor:create-tgchat_links-table

create table tgchat_links (
  tgchats_id bigint REFERENCES tgchats(id) ON DELETE CASCADE,
  links_id bigint REFERENCES links(id) ON DELETE CASCADE,

  constraint tgchat_links_pk PRIMARY KEY (tgchats_id, links_id)
);
