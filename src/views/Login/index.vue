<script setup>
import { onMounted, ref, reactive, h } from 'vue'
import { loginServer, registerServer } from '@/api/admin'
import { message } from 'ant-design-vue'
import { useAdminStore } from '@/stores'
import { useRouter } from 'vue-router'
import { notification } from 'ant-design-vue'
import { UserOutlined, LockOutlined, ApiOutlined } from '@ant-design/icons-vue'
const adminStore = useAdminStore()
const router = useRouter()
let formState = ref({
  aid: '',
  apwd: '',
  remember: false
})
onMounted(() => {
  if (Object.keys(adminStore.getAdmin()).length) {
    // 获取本地存储
    console.log('本地存储有数据')
    if (adminStore.getAdmin().remember) {
      formState.value = adminStore.getAdmin()
    }
  }
})
// 登录注册 / 切换
const signOp = reactive(['登录', '注册'])
const signSel = ref(signOp[0])
// 登录 / 注册
const onFinish = async (values) => {
  if (signSel.value === '登录') {
    message.loading('登录中...', 0.5)
    let res = await loginServer(values)
    console.log(res)
    // 登录失败
    if (res.code === '500') {
      message.error(res.msg)
      return
    }
    formState.value.aname = res.data.aname
    formState.value.now = new Date().getTime()
    formState.value.aavatar = res.data.aavatar
    formState.value.adesc = res.data.adesc
    adminStore.setAdmin(formState.value)
    message.success('登录成功~')
  } else {
    // 注册
    message.loading('注册中...', 0.5)
    const res = await registerServer(values)
    notification.open({
      message: '系统提示',
      description: h('div', null, [
        h('p', null, '注册成功~请使用系统生成的唯一账号登录'),
        h('span', null, '账号：', h('a', null, res.data.aid)),
        h('p', null, '请牢记!')
      ]),
      icon: () =>
        h(ApiOutlined, {
          style: 'color: #67C23A'
        }),
      duration: 0
    })
    formState.value.aid = res.data.aid
    setTimeout(() => {
      // 切换 为 登录
      signSel.value = signOp[0]
    }, 500)
  }
  // 跳转
  router.push('/')
}

const onFinishFailed = (errorInfo) => {
  console.log('Failed:', errorInfo)
}

const signForm = reactive({
  aid: '',
  apwd: '',
  aname: '',
  now: new Date().getTime()
})
</script>
<template>
  <div class="w-full h-screen flex justify-center items-center main">
    <div
      class="shadow-xl rounded-2xl backdrop-blur-md"
      style="background-color: rgba(255, 255, 255, 0.1); overflow: hidden"
    >
      <div class="w-full">
        <!-- 注册 -->
        <a-segmented
          v-model:value="signSel"
          block
          :options="signOp"
          size="large"
          class="sel"
        />
      </div>
      <!-- 主体 登录 -->
      <div class="px-20" v-if="signSel === '登录'">
        <h2 class="text-center m-10">账号登录</h2>
        <a-form
          :model="formState"
          name="normal_login"
          class="login-form"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
        >
          <a-form-item
            name="aid"
            :rules="[{ required: true, message: '请输入学号!' }]"
          >
            <a-input v-model:value="formState.aid">
              <template #prefix>
                <UserOutlined class="site-form-item-icon" />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item
            name="apwd"
            :rules="[{ required: true, message: '请输入密码!' }]"
          >
            <a-input-password v-model:value="formState.apwd">
              <template #prefix>
                <LockOutlined class="site-form-item-icon" />
              </template>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-form-item name="remember" no-style>
              <a-checkbox v-model:checked="formState.remember"
                >记住密码</a-checkbox
              >
            </a-form-item>
            <a class="login-form-forgot" href="">忘记密码</a>
          </a-form-item>

          <a-form-item>
            <div class="w-full flex justify-center">
              <a-button class="w-20" type="primary" html-type="submit">
                登录
              </a-button>
            </div>
          </a-form-item>
        </a-form>
      </div>
      <!-- 注册 -->
      <div class="px-20" v-else>
        <h2 class="text-center m-10">账号注册</h2>
        <a-form
          :model="signForm"
          name="normal_login"
          class="login-form"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
        >
          <a-form-item
            name="aname"
            :rules="[{ required: true, message: '请输入账号名!' }]"
          >
            <a-input v-model:value="signForm.aname">
              <template #prefix>
                <UserOutlined class="site-form-item-icon" />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item
            name="apwd"
            :rules="[{ required: true, message: '请输入密码!' }]"
          >
            <a-input-password v-model:value="signForm.apwd">
              <template #prefix>
                <LockOutlined class="site-form-item-icon" />
              </template>
            </a-input-password>
          </a-form-item>

          <!-- <a-form-item>
            <a-form-item name="remember" no-style>
              <a-checkbox v-model:checked="formState.remember"
                >记住密码</a-checkbox
              >
            </a-form-item>
            <a class="login-form-forgot" href="">忘记密码</a>
          </a-form-item> -->

          <a-form-item>
            <div class="w-full flex justify-center">
              <a-button class="w-20" type="primary" html-type="submit">
                注册
              </a-button>
            </div>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.main {
  transition: all 0.4s;
  background-color: rgba(163, 211, 207, 0.9);
  background-image: radial-gradient(
      closest-side,
      rgba(235, 105, 78, 1),
      rgba(235, 105, 78, 0)
    ),
    radial-gradient(closest-side, rgb(15, 127, 255), rgba(235, 105, 78, 0)),
    radial-gradient(closest-side, rgb(129, 204, 223), rgba(243, 10, 178, 0)),
    radial-gradient(closest-side, rgb(105, 125, 139), rgba(255, 135, 245, 0)),
    radial-gradient(closest-side, rgb(75, 70, 84), rgba(248, 195, 148, 0));
  background-size:
    130vmax 130vmax,
    80vmax 80vmax,
    90vmax 90vmax,
    110vmax 110vmax,
    90vmax 90vmax;
  background-position:
    -80vmax -80vmax,
    60vmax -30vmax,
    10vmax 10vmax,
    -30vmax -10vmax,
    -50vmax -50vmax;
  background-repeat: no-repeat;
  animation: movement 10s linear infinite;
  @keyframes movement {
    0%,
    100% {
      background-size:
        130vmax 130vmax,
        80vmax 80vmax,
        90vmax 90vmax,
        110vmax 110vmax,
        90vmax 90vmax;
      background-position:
        -80vmax -80vmax,
        60vmax -30vmax,
        10vmax 10vmax,
        -30vmax -10vmax,
        -50vmax -50vmax;
    }
    25% {
      background-size:
        100vmax 100vmax,
        90vmax 90vmax,
        100vmax 100vmax,
        90vmax 90vmax,
        60vmax 60vmax;
      background-position:
        -60vmax -90vmax,
        50vmax -40vmax,
        0vmax -20vmax,
        -40vmax -20vmax,
        -40vmax 60vmax;
    }
    50% {
      background-size:
        80vmax 80vmax,
        110vmax 110vmax,
        80vmax 80vmax,
        60vmax 60vmax,
        80vmax 80vmax;
      background-position:
        -50vmax -70vmax,
        40vmax -30vmax,
        10vmax 0vmax,
        20vmax 10vmax,
        30vmax 70vmax;
    }
    75% {
      background-size:
        90vmax 90vmax,
        90vmax 90vmax,
        100vmax 100vmax,
        90vmax 90vmax,
        70vmax 70vmax;
      background-position:
        -50vmax -40vmax,
        50vmax -30vmax,
        20vmax 0vmax,
        -10vmax 10vmax,
        40vmax 60vmax;
    }
  }
  .sel {
    border: none;
    backdrop-filter: blur(10px);
    background-color: transparent;
  }
}
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
</style>
