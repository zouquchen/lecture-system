import request from '@/utils/request'

export default {
  // 获得所有讲座类型
  orderLectureById(id) {
    return request({
      url: `/lectureOrder/orderLectureById/${id}`,
      method: 'post'
    })
  }
}
