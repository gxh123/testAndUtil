# noinspection SqlNoDataSourceInspectionForFile

create table table_log
(
	id bigint auto_increment comment '主键ID'
		primary key,
	cause varchar(255) default '' not null comment '原因',
	table_name varchar(255) default '' not null comment '修改的表的表名',
	content varchar(2000) null comment '修改的内容',
	creator_id bigint null comment '创建人ID',
	create_name varchar(255) null comment '创建人',
	create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)comment '日志主表' engine=InnoDB;

# create table table_log_detail
# (
# 	id bigint auto_increment comment '主键ID'
# 		primary key,
# 	log_id bigint null comment 'log主表id',
# 	prop_name varchar(255) null comment '字段名称',
# 	pre_value varchar(2000) null comment '修改前的值',
# 	post_value varchar(2000) null comment '修改后的值'
# )comment '日志详情' engine=InnoDB;


