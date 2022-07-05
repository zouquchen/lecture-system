<template>
  <div class="app-container">
    <!-- 发表评论 -->
    <el-row>
      <el-card class="box-card" shadow="hover" style="padding-bottom:10px">
        <el-col :span="1">
          <div><el-avatar :src="user.avatar" /></div>
        </el-col>
        <el-col :span="21">
          <el-input v-model="comment.content" placeholder="请发表有价值的评论~" />
        </el-col>
        <el-col :span="2">
          <el-button type="primary" style="float: right" @click="addComment(null, null, comment.content)">发表<i class="el-icon-chat-dot-square el-icon--right" /></el-button>
        </el-col>
      </el-card>
    </el-row>

    <!-- 评论列表 -->
    <div v-for="commentVo in list" :key="commentVo.id">
      <el-card class="box-card" shadow="never" style="margin-top: 10px">
        <!-- 发表评论的用户信息 -->
        <div slot="header">
          <el-row type="flex" align="middle">
            <el-col :span="1">
              <div><el-avatar :src="commentVo.avatar" /></div>
            </el-col>
            <el-col :span="4"><p style="font-weight:bold">{{ commentVo.username }}</p></el-col>
            <el-col :span="10">
              <p style="font-size: 13px;color: #999">{{ moment(commentVo.createTime).utcOffset(480).format('YYYY年MM月DD日 HH:mm:ss') }}</p>
            </el-col>
            <el-col :span="9">
              <el-button icon="el-icon-thumb" style="float: right" :type="commentVo.liked ? 'danger' : null" circle @click="likeButton(commentVo)">{{ commentVo.likes }}</el-button>
              <el-button icon="el-icon-chat-dot-square" style="float: right; margin-right:10px" circle @click="showCommentInput(commentVo.id)" />
              <el-button v-show="user.name == commentVo.username" icon="el-icon-delete" style="float: right" circle @click="deleteComment(commentVo.id)" />
            </el-col>
          </el-row>
        </div>

        <!-- 评论的内容 -->
        <div>{{ commentVo.content }} </div>

        <!-- 评论的评论，点击评论按钮显示 -->
        <el-row v-show="commentVo.id == vShowId" style="margin-top:10px">
          <el-card class="box-card" shadow="hover" style="padding-bottom:10px; background-color: WhiteSmoke">
            <el-col :span="1">
              <div><el-avatar :src="user.avatar" /></div>
            </el-col>
            <el-col :span="21">
              <el-input v-model="reply.content" :placeholder="'回复:' + commentVo.username" />
            </el-col>
            <el-col :span="2">
              <el-button type="info" style="float: right" @click="addComment(commentVo.id, commentVo.id, reply.content)">发表<i class="el-icon-chat-dot-square el-icon--right" /></el-button>
            </el-col>
          </el-card>
        </el-row>

        <!-- 评论的回复 -->
        <el-row v-for="children in commentVo.children" :key="children.id">
          <el-card class="box-card" shadow="never" style="margin-top: 10px; margin-right: 0px; background-color: WhiteSmoke">
            <div slot="header" style="padding: 0px 0px">
              <el-row type="flex" align="middle">
                <el-col :span="1">
                  <div><el-avatar :src="children.avatar" /></div>
                </el-col>
                <el-col :span="4" style="display: inline-block; float: left">
                  <p style="color: #999">{{ children.username }} 回复 {{ children.parentName }}</p>
                </el-col>
                <el-col :span="10">
                  <p style="font-size: 13px;color: #999">{{ moment(children.createTime).utcOffset(480).format('YYYY年MM月DD日 HH:mm:ss') }} </p>
                </el-col>
                <el-col :span="9">
                  <el-button icon="el-icon-thumb" style="float: right" :type="children.liked ? 'danger' : null " circle @click="likeButton(children)"> {{ children.likes }}</el-button>
                  <el-button icon="el-icon-chat-dot-square" style="float: right; margin-right:10px" circle @click="showCommentInput(children.id)" />
                  <el-button v-show="user.name == children.username" icon="el-icon-delete" style="float: right" circle @click="deleteComment(children.id)" />
                </el-col>
              </el-row>
            </div>
            <div>{{ children.content }} </div>
          </el-card>

          <!-- 评论的回复的评论，点击评论按钮显示 -->
          <el-row v-show="children.id == vShowId" style="margin-top:10px">
            <el-card class="box-card" shadow="hover" style="padding-bottom:10px; background-color: WhiteSmoke">
              <el-col :span="1">
                <div><el-avatar :src="user.avatar" /></div>
              </el-col>
              <el-col :span="21">
                <el-input v-model="reply.content" :placeholder="'回复:' + children.username" />
              </el-col>
              <el-col :span="2">
                <el-button type="info" style="float: right" @click="addComment(children.id, children.rootParentId, reply.content)">发表<i class="el-icon-chat-dot-square el-icon--right" /></el-button>
              </el-col>
            </el-card>
          </el-row>
        </el-row>
      </el-card>
    </div>

  </div>
</template>

<script>
import lectureCommentApi from '@/api/lecture/lectureComment'

export default {
  data() {
    return {
      lectureId: '',
      user: {
        name: '',
        avatar: ''
      },
      comment: {
        lectureId: '',
        parentId: '',
        rootParentId: '',
        content: ''
      },
      reply: {
        lectureId: '',
        parentId: '',
        rootParentId: '',
        content: ''
      },
      list: {
        id: '',
        userId: '',
        username: '',
        avatar: '',
        lectureId: '',
        parentId: '',
        parentName: '',
        rootParentId: '',
        rootParentName: '',
        content: '',
        likes: '',
        liked: '',
        createTime: '',
        updateTime: '',
        children: ''
      },
      vShowId: '',
      moment: require('moment'),
      dialogVisible: false,
      formLabelWidth: '120px'
    }
  },
  created() {
    this.user = this.$store.state.user
    if (this.$route.params && this.$route.params.id) {
      this.lectureId = this.$route.params.id
      // 获取评论
      this.getComment()
    } else {
      console.log('获取id失败')
    }
  },
  methods: {
    // 获得评论
    getComment() {
      lectureCommentApi.getComment(this.lectureId).then(res => {
        this.list = res.list
      }).catch(err => {
        console.log('getComment Error: ' + err)
      })
    },
    // 显示评论对话框
    showCommentInput(parentId) {
      this.reply.content = ''
      if (this.vShowId === parentId) {
        this.vShowId = ''
      } else {
        this.vShowId = parentId
      }
    },
    // 添加评论
    addComment(parentId, rootParentId, content) {
      lectureCommentApi.addComment(this.lectureId, parentId, rootParentId, content).then(res => {
        this.$message({
          type: 'success',
          message: '评论成功'
        })
        this.$router.go(0) // 组件使用 重新渲染
      }).catch(() => {
        this.$message({
          type: 'danger',
          message: '评论失败'
        })
      })
      this.vShowId = ''
    },

    deleteComment(id) {
      lectureCommentApi.deleteComment(id).then(res => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.$router.go(0) // 组件使用 重新渲染
      }).catch(() => {
        this.$message({
          type: 'danger',
          message: '删除失败'
        })
      })
    },
    // 点赞
    commentLike(comment) {
      lectureCommentApi.commentLike(comment.id).then(res => {
        comment.likes = comment.likes + 1
        comment.isLike = true
        this.$message({
          type: 'success',
          message: '点赞成功'
        })
      }).catch(() => {
        this.$message({
          type: 'danger',
          message: '点赞失败'
        })
      })
    },
    // 取消点赞
    commentCancelLike(comment) {
      lectureCommentApi.commentCancelLike(comment.id).then(res => {
        comment.likes = comment.likes - 1
        comment.isLike = false
        this.$message({
          type: 'success',
          message: '取消成功'
        })
      }).catch(() => {
        this.$message({
          type: 'danger',
          message: '取消失败'
        })
      })
    },
    // 点击点赞按钮
    likeButton(comment) {
      if (comment.isLike) {
        this.commentCancelLike(comment)
      } else {
        this.commentLike(comment)
      }
    }
  }
}
</script>

<style>
.el-card__body {
    padding: 20px 0px 20px 20px;
}
.el-card__header {
  padding: 10px 10px 2px 20px;
  border-bottom: 1px solid #EBEEF5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
</style>
