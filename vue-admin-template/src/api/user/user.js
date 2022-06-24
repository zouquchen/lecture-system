import request from '@/utils/request'

export default {
  getUserListPage(page, limit, userQuery) {
    return request({
      url: `/user/getUserList/${page}/${limit}`,
      method: `post`,
      data: userQuery
    })
  },
  getRoleList() {
    return request({
      url: `/user/getRoleList`,
      method: `get`
    })
  },
  // 获取用户详细信息（个人中心）
  getUserInfo() {
    return request({
      url: `/user/getUserInfo`,
      method: `get`
    })
  },
  // 更新用户信息
  updateUserInfo(user) {
    return request({
      url: `/user/updateUserInfo`,
      method: `post`,
      data: user
    })
  }

}
