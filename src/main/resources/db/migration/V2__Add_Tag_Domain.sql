drop table if exists post_tag;
drop table if exists tag;
create table post_tag (
       post_tag_id bigint not null auto_increment,
        post_id bigint,
        tag_id bigint,
        primary key (post_tag_id)
    ) engine=InnoDB;
create table tag (
       tag_id bigint not null auto_increment,
        tag_name varchar(255),
        primary key (tag_id)
    ) engine=InnoDB;

alter table post_tag
       add constraint FKc2auetuvsec0k566l0eyvr9cs
       foreign key (post_id)
       references post (post_id);
alter table post_tag
       add constraint FKac1wdchd2pnur3fl225obmlg0
       foreign key (tag_id)
       references tag (tag_id);
