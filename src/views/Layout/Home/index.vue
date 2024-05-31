<script setup>
import * as echarts from 'echarts'
import { onMounted, ref, reactive, computed } from 'vue'
import {
  getAdminClassServer,
  addClassServer,
  checkClassServer,
  delClassServer,
  editClassServer
} from '@/api/admin'
import { FormOutlined, CloseOutlined } from '@ant-design/icons-vue'
import { pageSelectListServer } from '@/api/student'
import { useAdminStore } from '@/stores'
import { message, Modal } from 'ant-design-vue'

const adminStore = useAdminStore()
// 全局状态
const isEdit = ref(false)
/// 声明定义一下echart
let echart = echarts
// eharts dom
const echartRef = ref()
onMounted(() => {
  initEhart()
  getClasses()
})
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
// 获取班级人数
const getClassesNum = async (id) => {
  const res = await pageSelectListServer({ sclass: id })
  return res.total
}
const chart = ref(null)
// 图表数据配置
let options = reactive({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    bottom: '5%',
    left: 'center'
  },
  series: [
    {
      name: '班级人数',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      padAngle: 0,
      itemStyle: {
        borderRadius: 5
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: true
      },
      data: []
    }
  ]
})
// 初始化 ehart
const initEhart = () => {
  // 2. 判断 dom 是否为空或未定义
  if (chart.value != null && chart.value != '' && chart.value != void 0) {
    // 3. 已存在则调用 dispose() 方法销毁
    console.log('销毁')
    console.log(options.series[0].data)
    chart.value.dispose()
  }
  chart.value = echart.init(echartRef.value)
  chart.value.setOption(options)
}
const classes = ref([])
// 获取以管理的班级
const getClasses = async () => {
  const res = await getAdminClassServer()
  classes.value = res.data
  // 在添加图表数据添加之前 先清空之前的图饼数据
  options.series[0].data = []
  // 循环获取班级人数
  for (let i = 0; i < classes.value.length; i++) {
    const element = classes.value[i]
    // 用户所管理的班级人数 和 用户所管理的班级 添加到data中
    // console.log(options.series[0].data)
    /// 没有清空 大概知道了
    // push的 所以要清空以前的数据，行那你先试试
    options.series[0].data.push({
      value: await getClassesNum(element.classes),
      name: element.classes
    })
  }
  // console.log(data.value)
  initEhart()
}
// 表单
const formRef = ref()
let form = ref({
  classes: '',
  cont: ''
})

// 通用函数 添加班级 / 编辑班级
const aEClass = async () => {
  formRef.value.validateFields().then(async () => {
    if (!isEdit.value) {
      console.log('添加班级')
      const res = await addClassServer(form.value)
      console.log('班级添加：', res)
      if (res.code === '200') {
        // 添加成功
        message.success('添加成功')
      }
    } else {
      const res = await editClassServer(form.value)
      if (res.code === '200') {
        message.success('修改成功')
        isEdit.value = false
      } else {
        message.error('修改失败')
      }
    }
    // 添加成功后 重新获取班级
    getClasses()
    // 清空表单
    form.value.classes = ''
    form.value.cont = ''
    // 关闭抽屉
    onClose()
  })
}
const rules = {
  classes: [
    { required: true, message: '请选择班级', trigger: 'change' },
    // 自定义校验
    {
      validator: async (rule, value) => {
        // 如果是编辑 并且没有改变 就不校验
        if (isEdit.value) {
          if (value === form.value.classes) {
            return Promise.resolve()
          }
        }
        const res = await checkClassServer(value)
        if (res.code === '500') {
          return Promise.reject('班级已存在')
        } else {
          return Promise.resolve()
        }
      }
    }
  ],
  cont: [{ required: true, message: '请输入班级描述', trigger: 'blur' }]
}
const open = ref(false)
const showDrawer = () => {
  open.value = true
}
const onClose = () => {
  form.value.classes = '' // 清空表单
  form.value.cont = '' // 清空表单
  open.value = false
}
// 删除
const delClass = async (id) => {
  console.log(id)
  Modal.confirm({
    title: '删除',
    content: '确定要删除该班级吗？',
    centered: true,
    cancelText: '取消',
    okText: '确定',
    onOk: async () => {
      message.loading('删除中...', 0.5)
      await delClassServer(id)
      // 刷新列表
      getClasses()
      // 刷新图表
      initEhart()
    },
    onCancel: () => {
      message.info('取消删除')
    }
  })
}
// 添加
const addClass = () => {
  isEdit.value = false
  showDrawer()
}
// 编辑
const editClass = (item) => {
  isEdit.value = true
  console.log(item)
  // 回显
  form.value = { ...item }
  // 显示侧边
  showDrawer()
}
</script>
<template>
  <a-layout-content>
    <div
      class="w-full h-30 border-spacing-3 flex justify-between items-center bg-white p-10"
    >
      <div class="start h-full flex justify-between items-center">
        <a-avatar
          :size="144"
          :src="
            user.aavatar ||
            'https://xsgames.co/randomusers/avatar.php?g=pixel&key=2'
          "
        />
        <div class="ml-3">
          <div class="text-2xl text-black">{{ adminStore.admin.aname }}</div>
          <!-- 角色 -->
          <div class="my-2">
            <a-tag color="#108ee9">管理员</a-tag>
            <span class="text-xl mx-2">aid:</span
            ><a> {{ adminStore.admin.aid }}</a>
          </div>
        </div>
      </div>
      <div class="end">
        <a-button type="dashed" @click="$router.push('editadmin')"
          >编辑</a-button
        >
      </div>
    </div>
    <div class="main">
      <!-- 左边 -->
      <div class="l w-4/6 bg-white">
        <a-card title="我管理的班级" :bordered="false" style="width: 100%">
          <template #extra><a @click="addClass">添加班级</a></template>
          <!-- item -->
          <div v-if="classes.length > 0" class="w-full">
            <a-card-grid
              style="
                width: 30%;
                cursor: pointer;
                float: left;
                margin: 10px 10px;
              "
              v-for="item in classes"
              :key="item.id"
              @click.stop="
                $router.push({
                  path: '/class',
                  query: { classId: item.classes }
                })
              "
            >
              <div class="text-wrap w-full box">
                <!-- edit del -->
                <div class="set">
                  <FormOutlined class="ico" @click.stop="editClass(item)" />
                  <CloseOutlined
                    class="ico"
                    @click.stop="delClass(item.classes)"
                  />
                </div>
                <!-- 头 -->
                <div class="top flex items-center">
                  <a-avatar style="background-color: #1890ff" :size="40">{{
                    item.classes
                  }}</a-avatar>
                  <span class="ml-5 text-xl">{{ item.classes }}</span>
                </div>
                <!-- 描述内容 -->
                <p
                  class="pt-4 text-gray-600"
                  style="word-wrap: break-word; word-break: break-all"
                >
                  描述：{{ item.cont }}
                </p>
              </div>
            </a-card-grid>
          </div>
          <!-- 空状态 -->
          <a-empty v-else />
        </a-card>
      </div>
      <!-- 右边 -->
      <div class="r h-20 bg-white">
        <!-- 数据展示 -->
        <a-card title="数据" :bordered="false" class="mt-5">
          <div class="h-80 w-auto bg-white" ref="echartRef"></div>
        </a-card>
        <!-- 日历 -->
        <a-card
          title="日历"
          :bordered="false"
          class="mt-5"
          style="height: 400px"
        >
          <a-calendar :fullscreen="false" />
        </a-card>
      </div>
    </div>
  </a-layout-content>
  <!-- 侧边 -->
  <a-drawer
    :title="isEdit ? '编辑班级' : '添加班级'"
    :width="720"
    :open="open"
    :body-style="{ paddingBottom: '80px' }"
    :footer-style="{ textAlign: 'right' }"
    @close="onClose"
  >
    <a-form :model="form" :rules="rules" layout="vertical" ref="formRef">
      <a-row :gutter="16">
        <a-col :span="12">
          <a-form-item label="班级" name="classes">
            <!-- 班级 -->
            <a-input v-model:value="form.classes" placeholder="Basic usage" />
          </a-form-item>
        </a-col>
      </a-row>

      <a-col :span="24">
        <a-form-item label="描述" name="cont">
          <a-textarea
            v-model:value="form.cont"
            :rows="4"
            placeholder="请输入描述信息"
          />
        </a-form-item>
      </a-col>
    </a-form>
    <template #extra>
      <a-space>
        <a-button @click="onClose">取消</a-button>
        <a-button type="primary" @click="aEClass">{{
          isEdit ? '修改' : '添加'
        }}</a-button>
      </a-space>
    </template>
  </a-drawer>
</template>
<style lang="scss" scoped>
.main {
  height: 100vh;
  margin: 20px 0;
  display: flex;
  justify-content: space-between;
  .r {
    width: 32%;
  }
  .l {
    .box {
      position: relative;
      &:hover {
        .set {
          opacity: 1;
        }
      }
      .set {
        opacity: 0;
        height: 30px;
        width: 55px;
        display: flex;
        justify-content: space-between;
        top: -2px;
        right: 0;
        position: absolute;
        .ico {
          font-size: 20px;
          &:nth-child(1) {
            &:hover {
              color: #79bbff;
            }
          }
          &:nth-child(2) {
            &:hover {
              color: #ff0000;
            }
          }
        }
      }
    }
  }
}
</style>
