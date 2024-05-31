<script setup>
import { ref, watch, h, onMounted, computed } from 'vue'
import { useAdminStore } from '@/stores'
import { useRoute, useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  IdcardOutlined,
  ContactsOutlined,
  DownOutlined,
  HomeOutlined,
  SafetyCertificateOutlined,
  SettingOutlined,
  LeftOutlined,
  RightOutlined
} from '@ant-design/icons-vue'
import { notification } from 'ant-design-vue'
const router = useRouter()
const adminStore = useAdminStore()
const collapsed = ref(false)
const route = useRoute()
const selectedKeys = ref([route.path])
const props = defineProps({
  user: {
    type: Object,
    default: () => ({})
  }
})

// 检查 admin 是否已经初始化
const isAdminInitialized = computed(() => !!adminStore.admin)

// 动态根据 props 有没有来决定取哪里的数据
const user = computed(() => {
  // 如果 props.user 不为空，就使用 props.user；否则，如果 adminStore.admin 已经初始化，就使用 adminStore.admin；否则，返回一个空对象
  return Object.keys(props.user).length
    ? props.user
    : isAdminInitialized.value
      ? adminStore.admin
      : {}
})
onMounted(() => {
  if (new Date().getTime() - adminStore.getAdmin().now <= 1000) {
    notification.open({
      message: '系统提示',
      description: `欢迎~${adminStore.getAdmin().aname}`,
      icon: () =>
        h(SafetyCertificateOutlined, {
          style: 'color: #67C23A'
        })
    })
  }
})
// const getPathStr = computed(() => {
//   console.log(route.fullPath)
//   switch (route.path) {
//     case '/students':
//       return '学生管理'
//     case '/user':
//       return '用户管理'
//     default:
//       return ''
//   }
// })
//   监听路由变化
watch(
  () => route.path,
  (val) => {
    selectedKeys.value = [val]
  }
)
// 退出登录
const logout = () => {
  adminStore.logout()
  message.loading('退出中...')
  setTimeout(() => {
    router.go(0)
  }, 500)
}

defineOptions({
  name: 'LayoutIndex'
})
</script>
<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider
      v-model:collapsed="collapsed"
      collapsible
      style="z-index: 999"
    >
      <div class="flex justify-center items-center h-16">
        <img src="@/assets/logo.png" alt="" class="w-full" />
      </div>
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item key="/home" @click="$router.push('/home')">
          <HomeOutlined style="font-size: 18px" />
          <span>首页</span>
        </a-menu-item>
        <a-menu-item key="/students" @click="$router.push('/students')">
          <ContactsOutlined style="font-size: 18px" />
          <span>学生管理</span>
        </a-menu-item>
        <a-menu-item key="/user" @click="$router.push('/user')">
          <IdcardOutlined style="font-size: 18px" />
          <span>我的</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header
        style="
          background: #fff;
          padding: 0;
          border-bottom: 1px solid rgb(235, 235, 235);
        "
        class="flex justify-between items-center"
      >
        <div>
          <LeftOutlined @click="$router.go(-1)" class="ml-10 text-xl icon" />
          <RightOutlined @click="$router.go(1)" class="mx-5 text-xl icon" />
        </div>
        <h1 class="text-3xl uppercase">学生管理系统</h1>
        <div class="mr-8">
          <div style="display: inline-block">
            <a-progress
              :percent="50"
              :steps="5"
              size="small"
              stroke-color="#52c41a"
            />
          </div>
          <!-- 头像 -->
          <a-avatar
            :size="40"
            :src="
              user.aavatar ||
              'https://xsgames.co/randomusers/avatar.php?g=pixel&key=2'
            "
          />
          <!-- 下拉菜单 -->
          <a-dropdown class="mr-4">
            <a class="ant-dropdown-link" @click.prevent>
              {{ adminStore.getAdmin().aname }}
              <DownOutlined />
            </a>
            <template #overlay>
              <a-menu>
                <a-menu-item key="/user" @click="$router.push('/user')">
                  <a-button type="link" block>我的资料</a-button>
                </a-menu-item>
                <a-menu-item key="/students" @click="$router.push('/students')">
                  <a-button type="link" block>学生管理</a-button>
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="3" @click="logout">
                  <a-button type="link" danger>退出登录</a-button></a-menu-item
                >
              </a-menu>
            </template>
          </a-dropdown>
          <!-- 设置 -->
          <SettingOutlined class="setIcon" style="font-size: 20px" />
        </div>
      </a-layout-header>
      <a-layout-content style="margin: 0 16px">
        <div :style="{ padding: '24px', minHeight: '360px' }">
          <!-- <a-watermark :content="adminStore.getAdmin().aname"> -->
          <router-view v-slot="{ Component }">
            <keep-alive>
              <component :is="Component" v-if="$route.meta.keepAlive" />
            </keep-alive>
            <component :is="Component" v-if="!$route.meta.keepAlive" />
          </router-view>

          <!-- </a-watermark> -->
        </div>
      </a-layout-content>
      <a-layout-footer
        style="
          text-align: center;
          position: fixed;
          bottom: 0;
          left: 0;
          right: 0;
          z-index: 0;
        "
      >
        student-web ©2024 Created by 7z
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<style lang="scss" scoped>
.icon {
  cursor: pointer;
  transition: all 0.3s;
  &:hover {
    color: #79bbff;
    transform: scale(1.1);
  }
}
.setIcon {
  &:hover {
    animation: r 0.5s ease-in-out forwards;
  }
  @keyframes r {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(120deg);
    }
  }
}
.logo {
  height: 32px;
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}
[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>
