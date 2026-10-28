# Weblog

> 前后端分离的个人博客系统 · By [LazyDaze101](https://github.com/LazySheep101)

基于 **Spring Boot 3.5 + Vue 3** 的博客 / 知识库项目，支持文章管理、分类标签、评论、公告、公共聊天室、MinIO 图床与 Lucene 站内搜索。

| 子目录 | 说明 | 技术 |
|--------|------|------|
| [`weblog-api`](./weblog-api) | 后端 | Spring Boot 3.5、Java 17、MyBatis-Plus、JWT、Lucene、MinIO |
| [`weblog-spa`](./weblog-spa) | 前端 | Vue 3、Vite 5、Pinia、Element Plus、Tailwind |

---

## 功能概览

**前台**：文章 / 分类 / 标签 / 归档、知识库、公告、评论、公共聊天室（WebSocket）、暗色模式  

**后台**（`/#/login`）：仪表盘、内容 CRUD、评论审核、博客设置、图片上传（MinIO）、行为式验证码登录

演示账号（以 `init.sql` 种子数据为准）：

| 账号 | 密码 | 角色 |
|------|------|------|
| `admin` | `admin` | 管理员 |
| `test` | `test` | 游客 |

---

## 目录结构

```text
weblog/
├── README.md
├── weblog-api/               # 后端
│   ├── weblog-web/           # 启动入口、配置模板、SQL
│   ├── weblog-module-*/      # admin / common / jwt / search
│   └── pom.xml
└── weblog-spa/               # 前端
    ├── src/
    ├── public/
    └── package.json
```

---

## 快速开始

### 环境要求

- JDK **17+**、Maven **3.8+**
- Node.js **18+**（推荐 20 LTS）
- MySQL **8.0**
- （可选）MinIO

### 后端

```bash
cd weblog-api

cp weblog-web/src/main/resources/application.yml.example \
   weblog-web/src/main/resources/application.yml

cp weblog-web/src/main/resources/application-dev.yml.example \
   weblog-web/src/main/resources/application-dev.yml

cp weblog-web/src/main/resources/application-prod.yml.example \
   weblog-web/src/main/resources/application-prod.yml
```

填写本地 MySQL、MinIO、JWT、邮箱等配置后，创建库 `weblog`（utf8mb4）并执行：

```text
weblog-web/src/main/resources/db/init.sql
```

```bash
mvn clean package -DskipTests
java -jar weblog-web/target/weblog-web-0.0.1-SNAPSHOT.jar
```

### 前端

```bash
cd weblog-spa

cp .env.development.example .env.development
cp .env.production.example .env.production

npm install
npm run dev
```

| 变量 | 说明 | 开发示例 |
|------|------|----------|
| `VITE_APP_BASE_API` | 接口前缀 | `/api` |
| `VITE_APP_WS_HOST` | WebSocket Host | `localhost:8081` |

- 前台：`http://localhost:5173/#/`
- 后台：`http://localhost:5173/#/login`

---

## 常见问题

**接口 404 / 跨域？**  
确认后端已启动，且 Vite 中 `/api` 代理端口与后端一致。

**WebSocket 连不上？**  
检查 `.env.development` 里的 `VITE_APP_WS_HOST`。

**验证码不显示？**  
确认 `public/tac` 可通过静态路径访问。

---

## License

仅供学习与交流。参考或衍生请保留原作者信息。

## 联系

- GitHub：[@LazySheep101](https://github.com/LazySheep101)
