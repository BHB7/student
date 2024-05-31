<script setup>
import { useAdminStore } from '@/stores'
import { EditOutlined, EllipsisOutlined } from '@ant-design/icons-vue'
import { defineProps, computed } from 'vue'

const props = defineProps({
  user: {
    type: Object,
    default: () => ({})
  }
})

const adminStore = useAdminStore()

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
</script>

<template>
  <a-card
    :bodyStyle="{ color: 'white', height: '200px' }"
    hoverable
    class="w-full main"
  >
    <template #cover>
      <div class="cover-box">
        <img src="@/assets/lgb.jpg" alt="" />
      </div>
    </template>
    <template #actions>
      <EditOutlined
        @click="$router.push('/editadmin')"
        style="font-size: 18px"
      />
      <EllipsisOutlined style="font-size: 18px" />
    </template>
    <a-card-meta>
      <template #avatar>
        <a-avatar
          :size="144"
          :src="
            user.aavatar ||
            'https://xsgames.co/randomusers/avatar.php?g=pixel&key=2'
          "
        />
      </template>
      <!-- 昵称 -->
      <template #title>
        <div class="mt-10">{{ user.aname || adminStore.admin.aname }}</div>
      </template>
      <!-- 描述 -->
      <template #description>
        <div class="">
          {{ user.adesc || '这个人很懒，什么都没写' }}
        </div>
      </template>
    </a-card-meta>
  </a-card>
</template>

<style lang="scss" scoped>
.ant-card-meta {
  margin: 40px 0;
}
.main {
  margin-top: 50px;
  position: relative;
  z-index: 1;
  background: linear-gradient(
    0deg,
    rgba(255, 255, 255, 0.629),
    rgba(0, 174, 255, 0.3)
  );
  .cover-box {
    top: 0;
    left: 0;
    width: 100%;
    height: 100px;
    z-index: -1;
    background: transparent;
    position: absolute;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }
}
</style>
