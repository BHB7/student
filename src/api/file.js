import request from '@/utils/request'

// 上传图片
export const updateImg = (uploadData) => {
  console.log(uploadData)
  return request.post('/admin/uploadImg', uploadData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
