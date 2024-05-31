import { useAdminStore } from '@/stores'
// 导入封装的请求方法
import request from '@/utils/request'

const adminStore = useAdminStore()
// 登录
export const loginServer = (admin) => request.post('/admin/login', admin)

// 注册
export const registerServer = (admin) => request.post('/admin/register', admin)
// 获取管理员管理的班级
export const getAdminClassServer = () =>
  request.get(`/admin/getClasses?aid=${adminStore.getAdmin().aid}`)

// 添加班级
export const addClassServer = (classInfo) => {
  classInfo.aid = adminStore.getAdmin().aid
  return request.post('/admin/addClass', classInfo)
}

// 获取已有班级
export const getClassTotalServer = () => request.get('/admin/count')

// 班级唯一性
export const checkClassServer = (classes) =>
  request.get(`/admin/sole?classes=${classes}&aid=${adminStore.getAdmin().aid}`)

// 删除班级
export const delClassServer = (cid) =>
  request.delete(`/admin/del?classes=${cid}`)

// 编辑班级 /classes/edit"
export const editClassServer = (classes) =>
  request.put('/admin/classes/edit', classes)

// 更新个人资料
export const editAdminInfoServer = (admin) => {
  admin.aid = adminStore.getAdmin().aid
  return request.put('/admin/edit', admin)
}
