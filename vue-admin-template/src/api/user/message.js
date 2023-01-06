import request from '@/utils/request'

export default {
  // 获取系统消息数量
  getMessageCount() {
    return request({
      url: `/user/message/getMessageCount`,
      method: `get`
    })
  },
  // 获取系统消息
  getMessage() {
    return request({
      url: `/user/message/getMessage`,
      method: `get`
    })
  }

}
