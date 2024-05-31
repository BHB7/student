import { createPinia } from 'pinia'
import piniaPluginPersist from 'pinia-plugin-persist' //pinia持久化
const pinia = createPinia()
pinia.use(piniaPluginPersist)

export default pinia

export * from './modules/admin'
