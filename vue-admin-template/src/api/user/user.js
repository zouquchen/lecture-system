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
  }
}
