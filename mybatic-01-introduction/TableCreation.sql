create table t_car
(
    id           int auto_increment comment '自增主键' primary key,
    price        double                                 not null comment '厂家指导价',
    name         VARCHAR(20)                            not null comment '车型名称',
    color        varchar(5)                             not null comment '颜色',
    brand        varchar(10) collate utf8mb4_unicode_ci null comment '品牌',
    type         varchar(10)                            not null comment '引擎类型 - 燃油, 纯电, 增程, 氢能',
    created_date varchar(10)                            not null comment '出厂日期',
    last_updated timestamp default current_timestamp    on update current_timestamp comment '上次保养时间'
)comment 'car info' engine = Innodb;

