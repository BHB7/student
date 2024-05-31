<script setup>
import UserInfo from '@/components/UserInfo.vue'
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue'
import { updateImg } from '@/api/file'
import { useAdminStore } from '@/stores'
import { editAdminInfoServer } from '@/api/admin'
import router from '@/router'

const adminStore = useAdminStore()
const userData = ref({
  aname: '',
  adesc: '',
  aavatar: '' // 头像
})
// 验证规则
const rules = {
  aname: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
  ],
  adesc: [
    { required: true, message: '请输入描述', trigger: 'blur' },
    { min: 2, max: 120, message: '长度在 2 到 120 个字符', trigger: 'blur' }
  ],
  aavatar: [{ required: true, message: '请上传头像', trigger: 'blur' }]
}

const fileList = ref([])
const loading = ref(false)

// 头像预显示
const showActive = () => {
  fileList.value.forEach((file) => {
    // file 转 URL
    const url = URL.createObjectURL(file.originFileObj) // 将后缀名添加到 URL 中
    console.log(url)
    userData.value.aavatar = url
    adminStore.setAvatar(url)
  })
}
const formRef = ref()

// 上传处理逻辑
const handleUpload = async () => {
  formRef.value.validateFields().then(async () => {
    if (!fileList.value[0] || !fileList.value[0].originFileObj) {
      // 确保文件对象不为空
      message.error('请选择文件')
      return
    }
    const originFileObj = fileList.value[0].originFileObj // 获取文件对象
    console.log(originFileObj) // 检查文件对象是否正确获取
    loading.value = true
    try {
      let uploadData = new FormData()
      uploadData.append('file', originFileObj) // 将文件添加到 FormData 对象中
      uploadData.append('userId', userData.value.aname) // 假设 userId 就是 userData.value.aname
      uploadData.append('aid', adminStore.getAdmin().aid)

      // 发送带有 FormData 的请求
      const res = await updateImg(uploadData)
      if (res.code === '200') {
        userData.value.aavatar = res.data
        adminStore.setAvatar(res.data)
        message.success('上传成功')
      } else {
        message.error('上传失败')
      }
    } catch (error) {
      console.error('文件上传失败:', error)
      message.error('文件上传失败，请稍后尝试')
    } finally {
      loading.value = false
    }
    // 更新资料
    const res = await editAdminInfoServer(userData.value)
    console.log(res)
    setTimeout(() => {
      message.success('更新成功')
      router.push('/user')
    }, 1000)
  })
}

// 上传前处理
const beforeUpload = (file) => {
  // 清空之前的文件列表，只保留当前上传的文件
  fileList.value = [file]
  return false // 阻止默认上传行为
}
</script>

<template>
  <div class="w-full flex justify-around flex-wrap">
    <div style="width: 50%">
      <p class="text-center text-3xl m-5">预览</p>
      <UserInfo :user="userData" />
    </div>

    <a-divider
      type="vertical"
      style="height: 100vh; border-color: #79bbff"
      dashed
    />

    <div class="h-screen flex items-center">
      <a-form ref="formRef" :model="userData" :rules="rules">
        <a-form-item label="昵称" name="aname">
          <a-input v-model:value="userData.aname" />
        </a-form-item>

        <a-form-item label="描述" name="adesc">
          <a-textarea
            show-count
            maxlength="120"
            v-model:value="userData.adesc"
            :rows="4"
          />
        </a-form-item>

        <a-form-item label="上传头像" name="aactive">
          <a-upload
            v-model:file-list="fileList"
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :before-upload="beforeUpload"
            @change="showActive"
          >
            <img
              v-if="userData.aavatar"
              :src="userData.aavatar"
              class="avatar"
              alt="avatar"
            />
            <div v-else>
              <LoadingOutlined v-if="loading" />
              <PlusOutlined v-else />
              <div class="ant-upload-text">上传头像</div>
            </div>
          </a-upload>
        </a-form-item>

        <a-form-item>
          <a-button class="mx-4" type="primary" @click="handleUpload"
            >修改</a-button
          >
          <a-button type="dashed" @click="$router.push('/')">取消</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.avatar {
  width: 100px;
  height: 100px;
  object-fit: cover;
}
</style>
