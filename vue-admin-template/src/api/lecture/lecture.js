import request from '@/utils/request'

export default {
  // 获得所有数据
  getAllList() {
    return request({
      url: '/lecture/listAll',
      method: 'get'
    })
  },
  // 分页查询数据
  getLectureListPage(page, limit, lectureQuery) {
    return request({
      url: `/lecture/adminPageList/${page}/${limit}`,
      method: `post`,
      data: lectureQuery
    })
  },
  // 添加讲座
  addLecture(lectureInfo) {
    return request({
      url: `/lecture/addLecture`,
      method: `post`,
      data: lectureInfo
    })
  },
  // 根据id查询讲座，用于修改讲座信息
  getLecutreById(id) {
    return request({
      url: `/lecture/getLecture/${id}`,
      method: `get`
    })
  },
  // 更新讲座
  updateLecture(lectureInfo) {
    return request({
      url: `/lecture/updateLecture`,
      method: `post`,
      data: lectureInfo
    })
  },
  // 根据id查询讲座详细信息，用于详情页面
  getLectureInfoById(id) {
    return request({
      url: `/lecture/getLectureInfo/${id}`,
      method: `get`
    })
  }
}
