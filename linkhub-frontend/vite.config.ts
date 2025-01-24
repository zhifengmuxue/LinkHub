import { fileURLToPath, URL } from 'node:url';
import { defineConfig, loadEnv } from 'vite'; // 添加 loadEnv
import vue from '@vitejs/plugin-vue';
import vueDevTools from 'vite-plugin-vue-devtools';

export default defineConfig(({ mode }) => { // 添加 mode 参数
  // 加载环境变量
  const env = loadEnv(mode, process.cwd(), 'VITE_');

  return {
    plugins: [vue(), vueDevTools()],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    server: {
      proxy: {
        '/api': {
          target: env.VITE_API_TARGET, // 使用 env 对象读取环境变量
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    },
  };
});