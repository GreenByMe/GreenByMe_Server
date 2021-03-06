drop table if exists mission cascade;
drop table if exists personal_mission cascade;
drop table if exists post cascade;
drop table if exists post_like cascade;
drop table if exists user cascade;
drop table if exists user_roles cascade;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table mission (
                         mission_id bigint not null auto_increment,
                         created_date timestamp,
                         last_modified_date timestamp,
                         category varchar(255),
                         day_category varchar(255),
                         description varchar(255),
                         expect_co2 double not null,
                         expect_tree double not null,
                         mission_certificate_count varchar(255),
                         text varchar(255) not null,
                         title varchar(255) not null,
                         pass_candidates_count bigint,
                         picture_url varchar(255),
                         subject varchar(255),
                         mission_title varchar(255),
                         primary key (mission_id)
);
create table personal_mission (
                                  personal_mission_id bigint not null auto_increment,
                                  created_date timestamp,
                                  last_modified_date timestamp,
                                  finish_count integer not null,
                                  personal_mission_status varchar(255),
                                  progress integer not null,
                                  remain_day bigint,
                                  remain_hour bigint,
                                  remain_min bigint,
                                  mission_id bigint,
                                  user_id bigint,
                                  primary key (personal_mission_id)
);

create table post (
                      post_id bigint not null auto_increment,
                      created_date timestamp,
                      last_modified_date timestamp,
                      created_by varchar(255),
                      last_modified_by varchar(255),
                      open boolean,
                      picture varchar(255),
                      text varchar(255),
                      title varchar(255),
                      personal_mission_id bigint,
                      user_id bigint,
                      primary key (post_id)
);

create table post_like (
                           postlike_id bigint not null auto_increment,
                           post_id bigint,
                           user_id bigint,
                           primary key (postlike_id)
);

create table user (
                      user_id bigint not null auto_increment,
                      created_date timestamp,
                      last_modified_date timestamp,
                      created_by varchar(255),
                      last_modified_by varchar(255),
                      email varchar(255),
                      expect_co2 double not null,
                      expect_tree double not null,
                      name varchar(255),
                      nickname varchar(255),
                      password varchar(255),
                      photo varchar(255),
                      platform_id varchar(255),
                      platform_type varchar(255),
                      primary key (user_id)
);

create table user_roles (
                            user_user_id bigint not null,
                            roles varchar(255)
);

alter table personal_mission add constraint FKfc1b2tt463bv3ku3iff79qj79 foreign key (mission_id) references mission(mission_id);
alter table personal_mission add constraint FKnbi7w2xrmx5mm9l5st6kr44e0 foreign key (user_id) references user(user_id);
alter table post add constraint FKhfg3a7f7gsrplxjece1ck4twa foreign key (personal_mission_id) references personal_mission(personal_mission_id);
alter table post
    add constraint FK72mt33dhhs48hf9gcqrq4fxte
        foreign key (user_id)
            references user(user_id);

alter table post_like
    add constraint FKj7iy0k7n3d0vkh8o7ibjna884
        foreign key (post_id)
            references post(post_id);

alter table post_like
    add constraint FKhuh7nn7libqf645su27ytx21m
        foreign key (user_id)
            references user(user_id);

alter table user_roles
    add constraint FKkv46dn3qakjvsk7ra33nd5sns
        foreign key (user_user_id)
            references user(user_id);