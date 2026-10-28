# Weblog Vue3

> Weblog 前端工程 · By [LazyDaze101](https://github.com/LazySheep101)

基于 **Vue 3 + Vite** 的前后端分离博客前台 / 管理后台 SPA，配合后端 [weblog](https://github.com/LazySheep101/weblog)（Spring Boot）使用。

---

## ✨ 功能特性

### 前台

- 首页文章列表、详情（Markdown / 代码高亮）
- 分类、标签、归档浏览
- 知识库列表与目录阅读
- 公告、评论
- 公共聊天室（WebSocket）
- 博客设置信息展示、暗色模式

### 后台（`/#/login` 登录后）

- 仪表盘统计（ECharts）
- 文章 / 分类 / 标签管理
- 知识库与目录编辑
- 评论审核、公告管理
- 博客设置、图片上传

---

## 🛠️ 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Vue 3、Vite 5、Vue Router（Hash）、Pinia |
| UI | Element Plus、Tailwind CSS、Flowbite、Animate.css |
| 请求 | Axios |
| 编辑 / 展示 | md-editor-v3、highlight.js、v-viewer |
| 图表 | ECharts |
| 工程化 | unplugin-auto-import、unplugin-vue-components、gzip 压缩、打包分析 |

---

## 📁 目录结构

```text
weblog-vue3/
├── public/                 # 静态资源（favicon、tac 验证码 SDK 等）
├── src/
│   ├── api/                # 接口封装（admin / frontend）
│   ├── assets/             # 样式与静态资源
│   ├── components/         # 通用组件
│   ├── composables/        # 组合式工具
│   ├── layouts/            # 前台 / 后台布局
│   ├── pages/
│   │   ├── frontend/       # 前台页面
│   │   └── admin/          # 后台页面
│   ├── router/             # 路由
│   ├── stores/             # Pinia
│   ├── axios.js            # Axios 实例
│   ├── permission.js       # 路由守卫
│   ├── App.vue
│   └── main.js
├── .env.development.example
├── .env.production.example
├── vite.config.js
├── package.json
└── README.md
```

---

## 📋 环境要求

- Node.js **18+**（推荐 LTS）
- npm / pnpm / yarn 任一
- 后端服务已启动（默认开发代理到 `http://localhost:8081`）

---

## 🚀 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 配置环境变量

仓库中只提供 `*.example`，复制后按需修改：

```bash
cp .env.development.example .env.development
cp .env.production.example .env.production
```

| 变量 | 说明 | 开发示例 |
|------|------|----------|
| `VITE_APP_BASE_API` | 接口前缀 | `/api` |
| `VITE_APP_WS_HOST` | WebSocket Host | `localhost:8081` |

生产示例：

```env
VITE_APP_BASE_API='http://your_domain_or_ip/api'
VITE_APP_WS_HOST='your_domain_or_ip'
```

> 真实 `.env.development` / `.env.production` 已加入 `.gitignore`，请勿提交。

### 3. 启动开发

```bash
npm run dev
```

- 前台：`http://localhost:5173/#/`
- 后台登录：`http://localhost:5173/#/login`

默认演示账号（以后端种子数据为准）：`admin` / `admin`

### 4. 开发代理说明

`vite.config.js` 中将浏览器请求的 `/api` 转发到本机后端，并去掉前缀：

```js
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8081',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, ''),
    },
  },
}
```

因此开发时 `VITE_APP_BASE_API='/api'` 即可，无需写死后端完整地址。

---

## 📦 生产构建

```bash
npm run build
```

产物在 `dist/`，部署到 Nginx（或任意静态服务器）的 html 根目录。

预览构建结果：

```bash
npm run preview
```

### 与 Nginx 配合（简要）

```text
浏览器 → Nginx:80
         ├─ /       → 前端 dist
         ├─ /api/   → 反代后端（如 8080）
         └─ /ws/    → WebSocket 反代
```

打包前请确认 `.env.production` 中的 API / WS 地址指向线上域名或 IP。

---

## 🔗 主要路由

| 路径 | 说明 |
|------|------|
| `/#/` | 首页 |
| `/#/article/:id` | 文章详情 |
| `/#/category/list` | 分类 |
| `/#/tag/list` | 标签 |
| `/#/archive/list` | 归档 |
| `/#/wiki/list` | 知识库 |
| `/#/chat` | 聊天室 |
| `/#/login` | 登录 |
| `/#/admin/index` | 后台仪表盘 |

---

## ❓ 常见问题

**1. 接口跨域 / 404？**  
开发环境确认后端已启动，且 Vite proxy 端口与后端一致（如 `8081`）。生产环境确认 Nginx 已配置 `/api` 反代。

**2. 白屏？**  
F12 看 Console：常见为资源路径错误、接口异常，或首页文章 `category` 为空导致渲染报错。确认 `dist` 完整上传（含 `assets`、`tac`）。

**3. 验证码不显示？**  
确认 `public/tac` 已随项目打包并部署到站点根路径下。

**4. WebSocket 连不上？**  
检查 `VITE_APP_WS_HOST`，以及 Nginx `/ws/` 是否开启了 Upgrade 相关头。

---

## 📄 License

本项目仅供学习与交流使用。若参考或衍生，请保留原作者信息。

---

## 📮 联系

- GitHub：[@LazySheep101](https://github.com/LazySheep101)
- 后端仓库：[weblog](https://github.com/LazySheep101/weblog)

若本项目对你有帮助，欢迎 ⭐ Star。
