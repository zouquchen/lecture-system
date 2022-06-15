import request from '@/utils/request'

export default {
  // 获得所有讲座类型：讲座类型id与讲座类型名称
  getLectureTypeList() {
    return request({
      url: '/lecture/type/listAll',
      method: 'get'
    })
  }
}
