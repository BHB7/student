import axios from 'axios'
import { message } from 'ant-design-vue'
const request = axios.create({
  // 全局配置 基地址
  baseURL: 'http://localhost:9090',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})
// 添加请求拦截器
request.interceptors.request.use(
  function (config) {
    // 在发送请求之前做些什么
    return config
  },
  function (error) {
    // 对请求错误做些什么
    console.log(error)
    message.error('请求失败')
    return Promise.reject(error)
  }
)

// 添加响应拦截器
request.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    if (response.status === 200) {
      if (response.data.code === 500) {
        message.error(response.data.msg)
        return Promise.reject(response)
      }
      return response.data
    }
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    message.error('服务器异常')
    return Promise.reject(error)
  }
)

export default request
export const baseURL = request.defaults.baseURL
