import request from '@/utils/request'

export default {
  // 根据讲座id预约讲座，因为用户已经登陆，根据springSecurity内的登录信息获取用户id
  orderLectureById(id) {
    return request({
      url: `/lectureOrder/orderLectureById/${id}`,
      method: 'post'
    })
  },
  // 根据讲座id取消讲座，因为用户已经登陆，根据springSecurity内的登录信息获取用户id
  cancelLectureById(id) {
    return request({
      url: `/lectureOrder/cancelLectureById/${id}`,
      method: 'post'
    })
  }
}
