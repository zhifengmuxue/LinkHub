# LinkHub 项目

LinkHub 是一个基于 Vue 3 和 Spring Boot 的短链接生成工具。前端使用 Vue 3 和 Ant Design Vue 构建，后端使用 Spring Boot 提供 API 服务。

---

## 项目结构

### 前端 (`linkhub-frontend`)
- **技术栈**：Vue 3 + TypeScript + Vite + Ant Design Vue
- **主要功能**：
  - 生成短链接
  - 管理链接
  - 工具页面
- **目录结构**：
  ```
  linkhub-frontend/
  ├── src/
  │   ├── assets/            # 静态资源
  │   ├── components/        # 组件
  │   ├── views/             # 页面视图
  │   ├── router/            # 路由配置
  │   ├── main.ts            # 入口文件
  │   └── App.vue            # 根组件
  ├── vite.config.ts         # Vite 配置
  └── env.d.ts               # 环境类型声明
  ```

### 后端 (`linkhub-backend`)
- **技术栈**：Spring Boot + MySQL + MyBatis
- **主要功能**：
  - 提供短链接生成 API
  - 管理链接数据
- **目录结构**：
  ```
    linkhub-backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── top/
    │   │   │       └── zfmx/
    │   │   │           └── linkhub/
    │   │   │               ├── application/      # 应用层
    │   │   │               ├── domain/           # 领域层
    │   │   │               ├── infrastructure/   # 基础设施层
    │   │   │               └── interfaces/       # 接口层
    │   │   └── resources/   # 配置文件
    └── pom.xml              # Maven 配置文件
  ```

---

## 快速开始

### 前端运行
1. 进入 `linkhub-frontend` 目录：
   ```bash
   cd linkhub-frontend
   ```
2. 安装依赖：
   ```bash
   npm install
   ```
3. 启动开发服务器：
   ```bash
   npm run dev
   ```
4. 访问 `http://localhost:5174`。

---

### 后端运行
1. 确保已安装 JDK 17 和 Maven。
2. 进入 `linkhub-backend` 目录：
   ```bash
   cd linkhub-backend
   ```
3. 编译项目：
   ```bash
   mvn clean install
   ```
4. 启动应用：
   ```bash
   mvn spring-boot:run
   ```
5. 后端服务将运行在 `http://localhost:8080`。

---

## 功能说明

### 前端功能
- **首页**：展示欢迎页面。
- **LinkHub**：生成和管理短链接。
- **工具**：提供短链接生成工具。
- **设置**：用户设置页面。

### 后端功能
- **短链接生成**：通过 `/api/link/generate` 接口生成短链接。
- **链接管理**：提供链接的增删改查功能。

---

## 技术细节

### 前端
- **路由配置**：使用 `vue-router` 管理页面路由。
- **请求代理**：通过 Vite 配置代理，解决跨域问题。
- **UI 框架**：使用 Ant Design Vue 构建界面。

### 后端
- **API 设计**：RESTful 风格 API。
- **数据库**：使用 MySQL 存储链接数据。
- **数据访问**：通过 MyBatis 实现数据访问。

---

## 贡献指南
1. Fork 项目仓库。
2. 创建新分支：`git checkout -b feature/your-feature`。
3. 提交更改：`git commit -m 'Add some feature'`。
4. 推送分支：`git push origin feature/your-feature`。
5. 提交 Pull Request。

---

## 许可证
本项目采用 MIT 许可证。详情请参阅 [LICENSE](LICENSE) 文件。