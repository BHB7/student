<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
// 引入学生接口
import {
  addStudentServer,
  updateStudentServer,
  deleteStudentServer,
  checkSidServer,
  pageSelectListServer
} from '@/api/student'
import { BorderlessTableOutlined } from '@ant-design/icons-vue'
import { message, Modal } from 'ant-design-vue'

const route = useRoute()
let modalTitle = ref('新增学生')
// 模态框确认按钮文本
let okText = ref('添加')
// 模态框显示状态
let modalVisible = ref(false)
let isLoading = ref(true)
const columns = [
  {
    title: '序号',
    dataIndex: 'index',
    key: 'index'
  },
  {
    title: '学号',
    dataIndex: 'sid',
    key: 'sid'
  },
  {
    title: '姓名',
    dataIndex: 'sname',
    key: 'sname'
  },
  {
    title: '班级',
    dataIndex: 'sclass',
    key: 'sclass'
  },
  {
    title: '性别',
    key: 'ssex',
    dataIndex: 'ssex'
  },
  {
    title: '操作',
    key: 'action'
  }
]
const data = ref([])
const total = ref(0)
// 获取学生列表
const getStudents = async () => {
  isLoading.value = true
  const res = await pageSelectListServer(page.value)
  setTimeout(() => {
    isLoading.value = false
  }, 500)
  total.value = res.total
  data.value = res.data
}
// 分页 参数
const page = ref({
  pageNow: 1,
  pageSize: 5,
  sclass: route.query.classId,
  sid: '',
  sname: '',
  ssex: ''
})
const onPageChange = async (pageNowN, pageSizeN) => {
  page.value.pageNow = pageNowN
  page.value.pageSize = pageSizeN
  getStudents()
}
getStudents()

// 表单
let formState = ref({
  sname: '',
  sid: '',
  sclass: route.query.classId,
  ssex: ''
})
// 表单 dom
const formRef = ref()
// 验证规则
const rules = {
  sname: [
    {
      required: true,
      message: '请输入姓名',
      trigger: 'blur'
    }
  ],
  sid: [
    {
      required: true,
      message: '请输入学号', // 22200204
      trigger: 'blur'
    },
    {
      pattern: /^[0-9]{8}$/,
      message: '请输入8位数字',
      trigger: 'blur'
    },
    // 异步获取sid 是否重复
    {
      validator: async (rule, value) => {
        if (modalTitle.value === '编辑学生') {
          return Promise.resolve()
        }
        const res = await checkSidServer(value)
        console.log(res)
        if (res.code === '500') {
          return Promise.reject('学号已存在')
        }
        return Promise.resolve()
      }
    }
  ],
  ssex: [
    {
      required: true,
      message: '请选择性别',
      trigger: 'change'
    }
  ]
}

// 新增
const showAddModal = () => {
  okText.value = '添加'
  modalTitle.value = '新增学生'
  modalVisible.value = true
}
// 添加学生的方法
const addStudent = async () => {
  isLoading.value = true
  message.loading('添加中...', 0.5)
  await addStudentServer(formState.value)
}
// 编辑
const showEditModal = (row) => {
  okText.value = '保存'
  modalTitle.value = '编辑学生'
  modalVisible.value = true
  formState.value = { ...row }
}
// 编辑学生的方法
const editStudent = async () => {
  isLoading.value = true
  message.loading('更新中...', 0.5)
  await updateStudentServer(formState.value)
}
// 删除
const del = async (row) => {
  modalTitle.value = '删除学生'
  Modal.confirm({
    title: modalTitle.value,
    content: '确定要删除该学生吗？',
    centered: true,
    cancelText: '取消',
    okText: '确定',
    onOk: async () => {
      isLoading.value = true
      message.loading('删除中...', 0.5)
      const res = await deleteStudentServer(row.id)
      isLoading.value = false
      if (res.code === '200') {
        message.success('删除成功', 2)
        // 刷新列表
        getStudents()
      } else {
        message.error('删除失败, 一会儿再试试吧~', 2)
      }
    },
    onCancel: () => {
      message.info('取消删除')
    }
  })
}
// 提交
const onSubmit = async () => {
  formRef.value
    .validateFields()
    .then(async () => {
      if (modalTitle.value === '新增学生') {
        await addStudent(formState.value)
        isLoading.value = false
        message.success('添加成功~', 2)
        formState.value = {}
        // 关闭弹窗
        modalVisible.value = false
        // 刷新列表
        getStudents()
      } else if (modalTitle.value === '编辑学生') {
        await editStudent(formState.value)
        isLoading.value = false
        message.success('编辑完成~', 2)
        formState.value = {}
        // 关闭弹窗
        modalVisible.value = false
        // 刷新列表
        getStudents()
      }
    })
    .catch((err) => {
      console.log(err)
    })
}
// 搜索
// 搜索值 临时值
const selTemp = ref('')
const selectOp = ref([
  { value: 'sid', label: '学号' },
  { value: 'sname', label: '姓名' },
  { value: 'ssex', label: '性别' }
])
// 搜索类型
const selectV = ref('sid')
const timer = ref(null)
const onSearch = async () => {
  clearTimeout(timer.value)
  // 防抖
  timer.value = setTimeout(async () => {
    console.log('搜索')
    page.value = {
      pageNow: 1,
      pageSize: 5,
      sclass: route.query.classId,
      sid: '',
      sname: '',
      ssex: ''
    }
    // 根据 selectV.value 的值设置对应的 page.value 属性
    switch (selectV.value) {
      case 'sid':
        page.value.sid = selTemp.value
        break
      case 'sname':
        page.value.sname = selTemp.value
        break
      case 'sclass':
        page.value.sclass = selTemp.value
        break
      case 'ssex':
        page.value.ssex =
          selTemp.value === '男' ? '0' : selTemp.value === '女' ? '1' : ''
        break
      default:
        break
    }
    isLoading.value = true
    const res = await pageSelectListServer(page.value)
    setTimeout(() => {
      isLoading.value = false
    }, 500)
    total.value = res.total
    data.value = res.data
  }, 500)
}
const handleChange = () => {
  console.log('搜索类型改变')
  selTemp.value = ''
}
// 编辑状态退出时 清空表单
const cancelModal = () => {
  formState.value = {}
}
defineOptions({
  name: 'StudentsIndex'
})
</script>

<template>
  <div class="w-full m-5 text-start text-2xl">
    <h1><BorderlessTableOutlined /> {{ route.query.classId }}班-详情</h1>
  </div>
  <div class="w-full flex items-center bg-white rounded-t-lg mb-5">
    <a-select
      class="m-7"
      ref="select"
      v-model:value="selectV"
      :options="selectOp"
      style="width: 120px"
      @change="handleChange"
    >
    </a-select>
    <a-input-search
      class="w-1/6"
      @search="onSearch"
      v-model:value="selTemp"
      placeholder="请输入搜索内容"
      enter-button
    />
    <!-- 新增按钮 -->
    <a-button type="primary" class="m-2" @click="showAddModal"
      >添加学生</a-button
    >
  </div>
  <!-- 表格 -->
  <a-table
    :pagination="false"
    :columns="columns"
    :data-source="data"
    :loading="isLoading"
  >
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.key === 'index'">{{ index + 1 }}</template>
      <template v-if="column.key === 'sname'">
        <a>
          {{ record.sname }}
        </a>
      </template>
      <template v-else-if="column.key === 'ssex'">
        <a-tag
          :color="
            record.ssex === '0'
              ? 'blue'
              : record.ssex === '1'
                ? 'pink'
                : 'green'
          "
          >{{
            record.ssex === '0' ? '男' : record.ssex === '1' ? '女' : '未知'
          }}</a-tag
        >
      </template>
      <template v-else-if="column.key === 'action'">
        <span class="flex justify-start items-center">
          <a-button
            type="primary"
            size="small"
            class="m-2"
            @click="showEditModal(record)"
            >编辑</a-button
          >
          <a-button danger size="small" class="m-2" @click="del(record)"
            >删除</a-button
          >
        </span>
      </template>
      <template v-else-if="column.key === 'sclass'">
        <span class="flex justify-start items-center">
          <a>
            {{ record.sclass }}
          </a>
        </span>
      </template>
    </template>
  </a-table>
  <!-- 分页 -->
  <div class="w-full mt-5 flex justify-center items-center">
    <a-pagination
      v-model:current="page.pageNow"
      show-quick-jumper
      showLessItems
      showSizeChanger
      :pageSizeOptions="['1', '5', '10', '20', '30']"
      v-model:pageSize="page.pageSize"
      :total="total"
      @change="onPageChange"
    />
  </div>
  <!-- 弹窗 -->
  <a-modal
    @cancel="cancelModal"
    :okText="okText"
    cancelText="取消"
    v-model:open="modalVisible"
    :title="modalTitle"
    centered
    @ok="onSubmit"
  >
    <!-- 表单 -->
    <a-form ref="formRef" :rules="rules" :model="formState">
      <a-form-item label="学生姓名" name="sname">
        <a-input v-model:value="formState.sname" />
      </a-form-item>
      <a-form-item label="学生学号" name="sid">
        <a-input v-model:value="formState.sid" />
      </a-form-item>
      <a-form-item label="性别" name="ssex">
        <a-radio-group v-model:value="formState.ssex">
          <a-radio value="0">男</a-radio>
          <a-radio value="1">女</a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
