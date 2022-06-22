import request from '@/utils/request'

export default {
  // 获得所有数据
  getAllList() {
    return request({
      url: '/lecture/listAll',
      method: 'get'
    })
  },
  // 条件分页查询讲座列表数据（for admin）
  getLectureAdminListPage(page, limit, lectureQuery) {
    return request({
      url: `/lecture/adminPageList/${page}/${limit}`,
      method: `post`,
      data: lectureQuery
    })
  },
  // 条件分页查询讲座列表数据（for user）
  getLectureUserListPage(page, limit, lectureQuery) {
    return request({
      url: `/lecture/userPageList/${page}/${limit}`,
      method: `post`,
      data: lectureQuery
    })
  },
  // 分页查询已预约讲座列表（for user）
  getLectureUserRecordPageList(page, limit) {
    return request({
      url: `/lecture/userRecordPageList/${page}/${limit}`,
      method: `get`
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
  // 根据id查询讲座，用于修改讲座信息(for user)
  getLectureInfoForUserById(id) {
    return request({
      url: `/lecture/getLectureInfoForUser/${id}`,
      method: `get`
    })
  },
  // 根据id查询讲座，用于修改或查询讲座信息(for admin)
  getLectureInfoForAdminById(id) {
    return request({
      url: `/lecture/getLectureInfoForAdmin/${id}`,
      method: `get`
    })
  },
  // 根据id获取讲座详细信息和用户预约列表，用于讲座详情页面
  getLectureInfoAndUserListForAdminById(id) {
    return request({
      url: `/lecture/getLectureInfoAndUserListForAdminById/${id}`,
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
  // 结束签到，关闭讲座
  finishLectureSignById(id) {
    return request({
      url: `/lecture/finishLectureSignById/${id}`,
      method: `post`
    })
  }
}
