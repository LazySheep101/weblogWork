-- 把演示图片 URL 写进业务表（图片文件在前端 public/images，本地用 Vite 访问）
-- 访问地址示例：http://localhost:5173/images/avatar.jpg
USE `weblog`;

UPDATE `t_blog_settings`
SET
  `logo` = '/images/logo.jpg',
  `avatar` = '/images/avatar.jpg',
  `name` = 'LazyDaze101 的博客',
  `author` = 'LazyDaze101',
  `introduction` = '欢迎来到我的博客'
WHERE `id` = 1;

UPDATE `t_article` SET `cover` = '/images/cover-springboot.jpg' WHERE `id` = 1;
UPDATE `t_article` SET `cover` = '/images/cover-mybatis.jpg' WHERE `id` = 2;
UPDATE `t_article` SET `cover` = '/images/cover-vue.jpg' WHERE `id` = 3;
UPDATE `t_article` SET `cover` = '/images/cover-docker.jpg' WHERE `id` = 4;
UPDATE `t_article` SET `cover` = '/images/cover-jdk.jpg' WHERE `id` = 5;
UPDATE `t_article` SET `cover` = '/images/cover-security.jpg' WHERE `id` = 6;

UPDATE `t_wiki` SET `cover` = '/images/cover-wiki.jpg' WHERE `id` = 1;

-- 评论头像（可选）
UPDATE `t_comment` SET `avatar` = '/images/avatar.jpg' WHERE `id` = 1;
