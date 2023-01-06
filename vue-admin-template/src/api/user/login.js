import request from '@/utils/request'
import aes from '@/utils/aes'

export function login(username, password) {
  password = aes.encrypt(password, null, null)
  return request({
    url: '/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo() {
  return request({
    // 这里不需要token了，因为token已经放到header里，实现了验证
    // 在后端从SecurityContextHolder里面解析出loginUser对象
    url: '/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
