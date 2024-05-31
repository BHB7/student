import { createRouter, createWebHistory } from 'vue-router'
import { useAdminStore } from '@/stores'
import { h } from 'vue'
import { MehOutlined } from '@ant-design/icons-vue'
import { notification } from 'ant-design-vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/Layout/index.vue'),
      redirect: '/home',
      // 布局 in 二级路由
      children: [
        {
          path: '/home',
          name: 'dashboard',
          component: () => import('@/views/Layout/Home/index.vue')
        },
        {
          path: '/students',
          name: 'students',
          component: () => import('@/views/Layout/Students/index.vue'),
          meta: {
            // 缓存
            keepAlive: true
          }
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('@/views/Layout/User/index.vue')
        },
        // 班级详情
        {
          path: '/class',
          name: 'class',
          component: () => import('@/views/Layout/Class/index.vue')
        }
      ]
    },
    // 登录
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login/index.vue')
    },
    // 编辑
    {
      path: '/editadmin',
      name: 'EditAdmin',
      component: () => import('@/views/EditAdmin/index.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  } else {
    const adminInfo = useAdminStore().getAdmin()
    if (Object.keys(adminInfo).length !== 0) {
      // 判断是否过期 1天（86400秒）
      // 注意这里乘以1000，因为getTime()返回的是毫秒
      if (new Date().getTime() - adminInfo.now > 86400 * 100) {
        notification.open({
          message: '系统提示',
          description: '登录已过期，请重新登录',
          icon: () =>
            h(MehOutlined, {
              style: 'color: #F56C6C'
            })
        })
        setTimeout(() => {
          next('/login')
        }, 500)
      } else {
        next()
      }
    } else {
      next('/login')
    }
  }
})

export default router
