import request from '@/utils/request'

export default {
  // 获得所有讲座类型
  getLectureTypeList() {
    return request({
      url: '/lecture/type/listAll',
      method: 'get'
    })
  }
}
