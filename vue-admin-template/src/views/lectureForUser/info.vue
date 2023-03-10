<template>
  <div class="app-container">
    <el-row>
      <el-col :span="10">
        <div align="center">
          <el-image :src="lecture.poster" style="width: 80%" lazy />
        </div>
      </el-col>
      <el-col :span="14">
        <!-- 讲座详情 -->
        <el-descriptions :column="1" size="1" class="margin-top" title="讲座详情" border>
          <template slot="extra">
            <el-button v-show="lecture.displayState == '未预约'" v-loading.fullscreen.lock="fullscreenLoading" type="primary" size="small" @click="orderLecture">预约</el-button>
            <el-button v-show="lecture.displayState == '已预约'" v-loading.fullscreen.lock="fullscreenLoading" type="danger" size="small" @click="cancelLecture">取消预约</el-button>
          </template>
          <!-- 预约情况 -->
          <el-descriptions-item>
            <template slot="label">预约情况</template>
            <el-tag :type="judgeDisplayState(lecture.displayState)">
              {{ lecture.displayState }}
            </el-tag>
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">讲座名称</template>{{ lecture.title }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">讲座类型</template>{{ lecture.typeName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user" />主讲嘉宾</template>{{ lecture.speaker }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-location-information" />讲座地点</template>{{ lecture.space }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-ice-cream-square" />主办方</template>{{ lecture.organizer }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-ice-cream-square" />承办方</template>{{ lecture.undertaker }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-ice-cream-square" />协办方</template>{{ lecture.sponsor }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user" />发布者姓名</template>{{ lecture.creatorName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user" />可预约人数</template>{{ lecture.store }} / {{ lecture.reservation }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-time" />预约时间</template>{{ lecture.orderStartTime }} — {{ lecture.orderEndTime }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-time" />开始时间</template>{{ lecture.lectureStartTime }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">讲座描述</template>{{ lecture.description }}
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-row>

    <!-- 评论详情 -->
    <el-descriptions :column="1" size="1" class="margin-top" style="margin-top:50px" title="评论内容" border />
    <el-row>
      <CommentComponent />
    </el-row>
  </div>
</template>

<script>
import lectureApi from '@/api/lecture/lecture'
import lectureOrderApi from '@/api/lecture/lectureOrder'
import CommentComponent from '@/views/components/CommentComponent'

export default {
  components: { CommentComponent },
  data() {
    return {
      // lecutre参数详情
      lecture: {
        id: '',
        title: '',
        typeName: '',
        createrName: '',
        organizer: '',
        undertaker: '',
        sponsor: '',
        state: '',
        displayState: '',
        space: '',
        speaker: '',
        reservation: '',
        store: '',
        description: '',
        poster: '',
        orderStartTime: '',
        orderEndTime: '',
        lectureStartTime: ''
      },
      moment: require('moment'),
      fullscreenLoading: false
    }
  },
  created() {
    // 获取路由id值
    if (this.$route.params && this.$route.params.id) { // 修改
      this.lecture.id = this.$route.params.id
      // 获取讲座详情
      this.getInfo()
    } else {
      console.log('获取id失败')
    }
  },
  methods: {
    getInfo() {
      console.log(this.lecture.id)
      lectureApi.getLectureInfoForUserById(this.lecture.id).then(res => {
        this.lecture = res.lectureInfo
        // 格式化时间
        this.lecture.orderStartTime = this.moment(this.lecture.orderStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
        this.lecture.orderEndTime = this.moment(this.lecture.orderEndTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
        this.lecture.lectureStartTime = this.moment(this.lecture.lectureStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')

        // 判断预约时间
        var curTime = this.moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        if (curTime > this.lecture.orderStartTime && curTime < this.lecture.orderEndTime) {
          this.orderStart = true
        }
      }).catch(err => {
        console.log('getLectureById Error: ' + err)
      })
    },
    orderLecture() {
      this.fullscreenLoading = true
      lectureOrderApi.orderLectureById(this.lecture.id).then(res => {
        this.fullscreenLoading = false
        // 刷新页面
        this.$router.go(0)
      }).catch(err => {
        this.fullscreenLoading = false
        console.log('预约失败：' + err)
      })
    },
    cancelLecture() {
      this.fullscreenLoading = true
      lectureOrderApi.cancelLectureById(this.lecture.id).then(res => {
        this.fullscreenLoading = false
        // 刷新页面
        // console.log('lecture.id' + this.lecture.id)
        this.$router.go(0)
      }).catch(err => {
        this.fullscreenLoading = false
        console.log('取消失败：' + err)
      })
    },
    // 判断预约状态，返回标签样式
    judgeDisplayState(data) {
      if (data === '未开放') return 'warning'
      if (data === '已预约') return ''
      if (data === '未预约') return 'info'
      if (data === '已签到') return 'success'
      if (data === '缺席') return 'danger'
      if (data === '已结束') return 'warning'
    }
  }
}
</script>
