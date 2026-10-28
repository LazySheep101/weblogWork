/*
 Weblog 完整初始化脚本（建库 + 建表 + 初始数据）
*/

CREATE DATABASE IF NOT EXISTS `weblog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `weblog`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 文章表
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(120) NOT NULL DEFAULT '' COMMENT '文章标题',
  `cover` varchar(120) NOT NULL DEFAULT '' COMMENT '文章封面',
  `summary` varchar(160) DEFAULT '' COMMENT '文章摘要',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  `read_num` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '被阅读次数',
  `weight` int(11) NOT NULL DEFAULT 0 COMMENT '权重（置顶）',
  `is_publish` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否发布：0否 1是',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '文章类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 文章-分类关联
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category_rel`;
CREATE TABLE `t_article_category_rel` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) UNSIGNED NOT NULL COMMENT '文章id',
  `category_id` bigint(20) UNSIGNED NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_article_id` (`article_id`) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章所属分类关联表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 文章内容
-- ----------------------------
DROP TABLE IF EXISTS `t_article_content`;
CREATE TABLE `t_article_content` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章内容id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `content` mediumtext COMMENT '正文',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章内容表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 文章-标签关联
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag_rel`;
CREATE TABLE `t_article_tag_rel` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) UNSIGNED NOT NULL COMMENT '文章id',
  `tag_id` bigint(20) UNSIGNED NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_article_id` (`article_id`) USING BTREE,
  KEY `idx_tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章对应标签关联表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 博客设置
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_settings`;
CREATE TABLE `t_blog_settings` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(120) NOT NULL DEFAULT '' COMMENT '博客Logo',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '博客名称',
  `author` varchar(20) NOT NULL DEFAULT '' COMMENT '作者名',
  `introduction` varchar(120) NOT NULL DEFAULT '' COMMENT '介绍语',
  `avatar` varchar(120) NOT NULL DEFAULT '' COMMENT '作者头像',
  `github_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'GitHub',
  `csdn_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'CSDN',
  `gitee_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'Gitee',
  `zhihu_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT '知乎',
  `mail` varchar(60) NOT NULL DEFAULT '' COMMENT '邮箱',
  `is_comment_sensi_word_open` tinyint(2) NOT NULL DEFAULT 0 COMMENT '评论敏感词开关',
  `is_comment_examine_open` tinyint(2) NOT NULL DEFAULT 0 COMMENT '评论审核开关',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='博客设置表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 分类
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '分类名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `articles_total` int(11) NOT NULL DEFAULT 0 COMMENT '该分类下文章数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章分类表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- PV 统计
-- ----------------------------
DROP TABLE IF EXISTS `t_statistics_article_pv`;
CREATE TABLE `t_statistics_article_pv` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pv_date` date NOT NULL COMMENT '被统计的日期',
  `pv_count` bigint(20) UNSIGNED NOT NULL COMMENT 'pv浏览量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_pv_date` (`pv_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='统计表-文章PV' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 标签
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '标签名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `articles_total` int(11) NOT NULL DEFAULT 0 COMMENT '该标签下文章数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章标签表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 用户
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志位',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 用户角色
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `role` varchar(60) NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色表' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 知识库
-- ----------------------------
DROP TABLE IF EXISTS `t_wiki`;
CREATE TABLE `t_wiki` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(120) NOT NULL DEFAULT '' COMMENT '标题',
  `cover` varchar(120) NOT NULL DEFAULT '' COMMENT '封面',
  `summary` varchar(160) DEFAULT '' COMMENT '摘要',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `weight` int(11) NOT NULL DEFAULT 0 COMMENT '权重',
  `is_publish` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否发布',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识库' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 知识库目录
-- ----------------------------
DROP TABLE IF EXISTS `t_wiki_catalog`;
CREATE TABLE `t_wiki_catalog` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wiki_id` bigint(20) UNSIGNED NOT NULL COMMENT '知识库id',
  `article_id` bigint(20) UNSIGNED DEFAULT NULL COMMENT '文章id',
  `title` varchar(120) NOT NULL DEFAULT '' COMMENT '目录标题',
  `level` int(11) NOT NULL DEFAULT 1 COMMENT '层级',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父目录id',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_wiki_id` (`wiki_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识库目录' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 公告
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '公告内容',
  `is_show` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否展示',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公告' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 评论
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '评论内容',
  `avatar` varchar(120) DEFAULT '' COMMENT '头像',
  `nickname` varchar(60) NOT NULL DEFAULT '' COMMENT '昵称',
  `mail` varchar(60) NOT NULL DEFAULT '' COMMENT '邮箱',
  `website` varchar(120) DEFAULT '' COMMENT '网站',
  `router_url` varchar(160) NOT NULL DEFAULT '' COMMENT '评论路由',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `reply_comment_id` bigint(20) DEFAULT NULL COMMENT '回复的评论id',
  `parent_comment_id` bigint(20) DEFAULT NULL COMMENT '父评论id',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态',
  `reason` varchar(120) DEFAULT '' COMMENT '原因',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_router_url` (`router_url`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- 聊天室消息
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_message`;
CREATE TABLE `t_chat_message` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nickname` varchar(60) NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(120) DEFAULT '' COMMENT '头像',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `qq` varchar(20) DEFAULT '' COMMENT 'QQ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='聊天室消息' ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 初始化数据
-- 账号：admin / admin（管理员）
-- 账号：test / test（游客）
-- ----------------------------
INSERT INTO `t_user` (`id`, `username`, `password`, `create_time`, `update_time`, `is_deleted`) VALUES
(1, 'admin', '$2a$10$DHt1d8fLLy45DNBbZ.XTr.ABLyNdvzZPlUwyqwjKz.EKo3mmHoGAK', '2026-01-01 10:00:00', '2026-01-01 10:00:00', 0),
(2, 'test', '$2a$10$AtfvfnESx51DlJsWvQuU2O3HGchJCtgXiMxwyFOoWw5fTCbf/hGiW', '2026-01-01 10:00:00', '2026-01-01 10:00:00', 0);

INSERT INTO `t_user_role` (`id`, `username`, `role`, `create_time`) VALUES
(1, 'admin', 'ROLE_ADMIN', '2026-01-01 10:00:00'),
(2, 'test', 'ROLE_VISITOR', '2026-01-01 10:00:00');

INSERT INTO `t_blog_settings` (`id`, `logo`, `name`, `author`, `introduction`, `avatar`, `github_homepage`, `csdn_homepage`, `gitee_homepage`, `zhihu_homepage`, `mail`, `is_comment_sensi_word_open`, `is_comment_examine_open`) VALUES
(1, '', 'LazyDaze101 的博客', 'LazyDaze101', '欢迎来到我的博客', '', '', '', '', '', '', 0, 0);
