import request from '@/utils/request'

export default {
  // 用户签到
  orderLectureById(lectureId, username) {
    return request({
      url: `/lecture/userRecord/userSign/${lectureId}/${username}`,
      method: 'post'
    })
  },
  // 根据id查询已经预约并签到此讲座的所有用户
  getSignedUserList(lectureId) {
    return request({
      url: `/lecture/userRecord/getSignedUserList/${lectureId}`,
      method: 'get'
    })
  }
}
