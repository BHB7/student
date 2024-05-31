// 导入封装的请求方法
import request from '@/utils/request'

// 获取学生列表
export const getStudentListServer = () => request.get('/student/list')

// 分页获取学生列表
export const pageGetStudentListServer = (page) => {
  return request.get(
    `/student/listPage?pageNow=${page.pageNow}&pageSize=${page.pageSize}`
  )
}

// 删除学生
export const deleteStudentServer = (id) =>
  request.delete(`/student/delete/${id}`)

// 添加学生
export const addStudentServer = (student) => {
  console.log(student)
  return request.post('/student/add', student)
}
// 判断账号是否存在
export const checkSidServer = (sid) => request.get(`/student/only?sid=${sid}`)
// 修改学生信息
export const updateStudentServer = (student) =>
  request.put('/student/update', student)

// 分页模糊查询
export const pageSelectListServer = (page) => {
  // 拼接参数 如果没有参数就默认第一页 默认每页显示10000条 默认查询所有
  let params = `/student/listP?pageNow=${page.pageNow === void 0 ? 1 : page.pageNow}&pageSize=${page.pageSize === void 0 ? 10000 : page.pageSize}`
  // 根据条件动态添加参数
  if (page.sid) {
    params += `&sid=${page.sid}`
  }
  if (page.sname) {
    params += `&sname=${page.sname}`
  }
  if (page.ssex) {
    params += `&ssex=${page.ssex}`
  }
  if (page.sclass) {
    params += `&sclass=${page.sclass}`
  }
  return request.get(params)
}
