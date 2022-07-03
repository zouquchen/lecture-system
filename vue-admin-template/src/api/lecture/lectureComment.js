import request from '@/utils/request'

export default {
  // 根据讲座id查询讲座的所有评论
  getComment(id) {
    return request({
      url: `/lecture/comment/getComment/${id}`,
      method: 'get'
    })
  },
  // 针对于讲座或评论发布评论
  addComment(lectureId, parentId, rootParentId, content) {
    return request({
      url: `/lecture/comment/addComment`,
      method: 'post',
      data: {
        lectureId,
        parentId,
        rootParentId,
        content
      }
    })
  },
  // 删除评论
  deleteComment(id) {
    return request({
      url: `/lecture/comment/deleteComment/${id}`,
      method: 'post'
    })
  },
  // 编辑修改评论
  updateComment(comment) {
    return request({
      url: `/lecture/comment/updateComment`,
      method: 'post',
      data: comment
    })
  }
}
