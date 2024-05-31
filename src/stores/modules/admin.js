import { defineStore } from 'pinia'
import { ref } from 'vue'
export const useAdminStore = defineStore(
  'admin',
  () => {
    const admin = ref({})

    const setAdmin = (adminN) => {
      console.log(adminN)
      admin.value = { ...adminN }
    }
    const getAdmin = () => {
      return admin.value
    }
    const setAvatar = (url) => {
      admin.value.aavatar = url
    }
    const logout = () => {
      admin.value = {}
    }

    return {
      logout,
      setAvatar,
      admin,
      setAdmin,
      getAdmin
    }
  },
  {
    persist: {
      enabled: true,
      strategies: [
        {
          key: 'admin_7z',
          storage: localStorage
        }
      ]
    }
  }
)
