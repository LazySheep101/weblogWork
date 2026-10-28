# Weblog

> 前后端分离的个人博客系统 · By [LazyDaze101](https://github.com/LazySheep101)

基于 **Spring Boot 3.5 + Vue 3** 的博客 / 知识库项目，支持文章管理、分类标签、评论、公告、公共聊天室、MinIO 图床与 Lucene 站内搜索，适合学习全栈与云服务器部署。

> 本仓库为**后端**工程。前端工程一般位于同级目录 `weblog-vue3`（可单独维护）。

---

## ✨ 功能特性

### 前台

- 文章列表 / 详情（Markdown 渲染、代码高亮）
- 分类、标签、归档浏览
- 知识库（目录树 + 文档阅读）
- 公告展示、访客评论
- 公共聊天室（WebSocket）
- 博客信息展示（Logo、作者、社交链接等）

### 后台（需登录）

- 仪表盘：文章 / 分类 / 标签 / PV 统计（ECharts）
- 文章、分类、标签 CRUD
- 知识库与目录管理
- 评论审核、公告管理
- 博客设置、图片上传（MinIO）
- 行为式验证码登录（JWT）

---

## 🛠️ 技术栈

| 层级 | 技术 |
|------|------|
| 前端 | Vue 3、Vite 5、Vue Router、Pinia、Element Plus、Tailwind CSS、Axios、ECharts、md-editor-v3 |
| 后端 | Spring Boot 3.5、Java 17、Spring Security、JWT、MyBatis-Plus、Knife4j、WebSocket |
| 存储 | MySQL 8、MinIO、Lucene |
| 部署 | Docker（MySQL / MinIO / Nginx）、Nginx 反向代理 |

---

## 📁 项目结构

```text
weblog-springboot/              # 本仓库根目录
├── weblog-web/                 # 启动入口、配置、SQL
├── weblog-module-admin/        # 管理端业务
├── weblog-module-common/       # 公共实体、工具、枚举
├── weblog-module-jwt/          # JWT 认证
├── weblog-module-search/       # Lucene 搜索
├── pom.xml
├── .gitignore
└── README.md
```

同级常见前端目录（不在本仓库内时需自行放置）：

```text
weblog-vue3/
├── src/pages/frontend/
├── src/pages/admin/
└── src/layouts/
```

---

## 🧩 模块说明（后端）

| 模块 | 说明 |
|------|------|
| `weblog-web` | Spring Boot 启动模块，聚合依赖，对外提供 HTTP / WS |
| `weblog-module-admin` | 后台管理接口与服务 |
| `weblog-module-common` | DO、Mapper、通用响应、异常等 |
| `weblog-module-jwt` | 登录认证、Token 签发与过滤器 |
| `weblog-module-search` | 基于 Lucene 的站内检索 |

---

## 📋 环境要求

- JDK **17+**
- Maven **3.8+**
- Node.js **18+**（前端，推荐 LTS）
- MySQL **8.0**
- （可选）Docker、MinIO

---

## 🚀 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/LazySheep101/weblog.git
cd weblog
```

### 2. 准备配置文件（敏感信息不进 Git）

仓库中只提供 `*.example` 模板，请复制后填写自己的账号密码：

```bash
cp weblog-web/src/main/resources/application.yml.example \
   weblog-web/src/main/resources/application.yml

cp weblog-web/src/main/resources/application-dev.yml.example \
   weblog-web/src/main/resources/application-dev.yml

cp weblog-web/src/main/resources/application-prod.yml.example \
   weblog-web/src/main/resources/application-prod.yml
```

前端（若使用同级 `weblog-vue3`）：

```bash
cp ../weblog-vue3/.env.development.example ../weblog-vue3/.env.development
cp ../weblog-vue3/.env.production.example ../weblog-vue3/.env.production
```

**已被忽略、勿提交的文件：**

- `application.yml` / `application-dev.yml` / `application-prod.yml`
- 前端 `.env.development` / `.env.production`

### 3. 初始化数据库

1. 创建库：`weblog`（utf8mb4）
2. 执行建表脚本：

```text
weblog-web/src/main/resources/db/init.sql
```

3. （可选）封面图相关脚本：

```text
weblog-web/src/main/resources/db/seed-images.sql
```

默认演示账号（以你导入的种子数据为准）：

| 账号 | 密码 | 角色 |
|------|------|------|
| `admin` | `admin` | 管理员 |
| `test` | `test` | 游客 |

> 若库表较旧，请确认 `t_category` / `t_tag` 已有 `articles_total` 字段。

### 4. 启动后端

```bash
mvn clean package -DskipTests
# 开发环境默认 spring.profiles.active=dev，端口见 application-dev.yml（如 8081）
java -jar weblog-web/target/weblog-web-0.0.1-SNAPSHOT.jar
```

也可在 IDE 中运行 `WeblogWebApplication`。

- 接口文档（Knife4j，以实际配置为准）：`http://localhost:8081/doc.html`

### 5. 启动前端

```bash
cd ../weblog-vue3
npm install
npm run dev
```

- 本地访问：`http://localhost:5173`
- 后台登录：`http://localhost:5173/#/login`

开发环境通过 Vite 将 `/api` 代理到后端，例如：

```js
// vite.config.js
proxy: {
  '/api': {
    target: 'http://localhost:8081',
    changeOrigin: true,
    rewrite: (path) => path.replace(/^\/api/, ''),
  },
}
```

---

## 🌐 生产部署（简要）

典型拓扑：

```text
浏览器 → Nginx(:80)
           ├─ /          静态前端（dist）
           ├─ /api/      反代 → 宿主机 Spring Boot(:8080)
           └─ /ws/       WebSocket → 同上
         MySQL / MinIO 可用 Docker 单独运行
```

### Nginx 示例

```nginx
server {
    listen 80;
    server_name your_ip_or_domain;

    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api/ {
        proxy_pass http://172.17.0.1:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location /ws/ {
        proxy_pass http://172.17.0.1:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
    }
}
```

### 前端打包

```bash
cd ../weblog-vue3
npm run build
# 将 dist/ 内容部署到 Nginx html 目录
```

### 后端生产启动

```bash
java -jar weblog-web/target/weblog-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

请保证 `application-prod.yml` 中 MySQL、MinIO、Lucene 路径等与服务器一致。

---

## 🔗 相关地址（按你的环境修改）

| 用途 | 地址示例 |
|------|----------|
| 前台 | `http://your_host/#/` |
| 后台登录 | `http://your_host/#/login` |
| MinIO Console | `http://your_host:9001`（若已映射） |

---

## ❓ 常见问题

**1. 前端白屏？**  
打开浏览器 F12 → Console。常见原因：静态资源 404、接口失败、或文章 `category` 为空导致渲染报错。确认 Nginx 已正确挂载 `dist`，后端 `/api` 可通。

**2. 接口 403 / 连不上？**  
检查 Nginx `proxy_pass`、后端是否监听 `8080`、安全组是否放行 80（及 MinIO 端口）。

**3. 登录验证码不显示？**  
确认前端 `public/tac`（或打包后的 `tac`）已部署，且 `application.yml` 中 captcha 配置完整。

**4. 为什么配置文件不在仓库里？**  
避免泄露数据库密码、JWT、邮箱授权码等。请始终使用 `*.example` 复制后本地填写。

---

## 📸 截图

> 可自行补充前台首页、文章详情、后台仪表盘截图。

```text
docs/images/home.png
docs/images/admin.png
```

---

## 📄 License

本项目仅供学习与交流使用。若参考或衍生，请保留原作者信息。

---

## 🙏 致谢

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [Element Plus](https://element-plus.org/)
- [MyBatis-Plus](https://baomidou.com/)
- 以及开源社区相关教程与组件作者

---

## 📮 联系

- GitHub：[@LazySheep101](https://github.com/LazySheep101)
- 项目仓库：[weblog](https://github.com/LazySheep101/weblog)

若本项目对你有帮助，欢迎 ⭐ Star。
