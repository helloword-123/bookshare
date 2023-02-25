
 drop table if exists `acl_permission`;
 create table `acl_permission`  (
 `id` int(11)  auto_increment ,
 `name` varchar(255)  ,
 `description` varchar(255)  ,
 `key` varchar(255)  ,
 `type_id` int,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `acl_permission_type`;
 create table `acl_permission_type`  (
 `id` int(11)  auto_increment,
 `name` varchar(255)  ,
 `description` varchar(255)  ,
 `key` varchar(255)  ,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `acl_role`;
 create table `acl_role`  (
 `id` int(11)  auto_increment,
 `name` varchar(255)  ,
 `description` varchar(255)  ,
 `key` varchar(255)  ,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `acl_role_permission`;
 create table `acl_role_permission`  (
 `role_id` int,
 `permission_id` int,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`role_id`)
 );

 drop table if exists `acl_role_user`;
 create table `acl_role_user`  (
 `user_id` int,
 `role_id` int,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`user_id`)
 );

 drop table if exists `advice`;
 create table `advice`  (
 `id` int(11)  auto_increment,
 `content` varchar(255)  ,
 `contact` varchar(255)  ,
 `user_id` int,
 `create_time` datetime,
 `update_time` datetime,
 `reply` varchar(255)  ,
 `status` int,
 `star` float,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `advice_picture`;
 create table `advice_picture`  (
 `advice_id` int,
 `picture_id` int,
 primary key (`advice_id`)
 );

 drop table if exists `auth_picture`;
 create table `auth_picture`  (
 `id` int(11)  auto_increment,
 `picture_url` varchar(255)  ,
 `auth_id` int,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `book`;
 create table `book`  (
 `id` int(11)  auto_increment,
 `name` varchar(255)  ,
 `author` varchar(255)  ,
 `category_id` int,
 `publishing_time` varchar(255)  ,
 `publishing_house` varchar(255)  ,
 `description` varchar(255)  ,
 `picture_url` varchar(255)  ,
 `isbn` varchar(255)  ,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `book_category`;
 create table `book_category`  (
 `id` int(11)  auto_increment,
 `pid` int,
 `name` varchar(255)  ,
 `description` varchar(255)  ,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `book_drift`;
 create table `book_drift`  (
 `id` int(11)  auto_increment,
 `sharer_id` int,
 `sharer_name` varchar(255)  ,
 `sharer_phone` varchar(255)  ,
 `borrower_id` int,
 `driftaddress` varchar(255)  ,
 `latitude` float,
 `longitude` float,
 `release_time` datetime,
 `drift_time` datetime,
 `book_id` int,
 `note` varchar(255)  ,
 `status` int,
 `drift_num` int,
 `create_time` datetime,
 `update_time` datetime,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `book_drift_picture`;
 create table `book_drift_picture`  (
 `drift_id` int,
 `picture_id` int,
 primary key (`drift_id`)
 );

 drop table if exists `campus_staff_auth`;
 create table `campus_staff_auth`  (
 `id` int(11)  auto_increment,
 `real_name` varchar(255)  ,
 `phone` varchar(255)  ,
 `email` varchar(255)  ,
 `number` varchar(255)  ,
 `user_id` int,
 `status` int,
 `create_time` datetime,
 `update_time` datetime,
 `checker_id` int,
 `check_time` datetime,
 `description` varchar(255)  ,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `drift_picture`;
 create table `drift_picture`  (
 `id` int(11)  auto_increment,
 `picture_url` varchar(255)  ,
 primary key (`id`) ,
 index `id`(`id`)
 );

 drop table if exists `user`;
 create table `user`  (
 `id` int(11)  auto_increment,
 `user_name` varchar(255)  , 
 `account` varchar(255)  , 
 `password` varchar(255)  , 
 `phone` varchar(255)  , 
 `sex` int, 
 `point` int, 
 `total_share_num` int, 
 `total_use_num` int, 
 `avatar_url` varchar(255)  , 
 `openid` varchar(255)  , 
 `create_time` datetime, 
 `update_time` datetime, 
 `auth_status` int, 
 primary key (`id`) ,
 index `id`(`id`)  
 );
