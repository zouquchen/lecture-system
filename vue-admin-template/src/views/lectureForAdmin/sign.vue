<template>
  <div class="app-container">
    <el-row>
      <el-col :span="14">
        <!-- 讲座详情 -->
        <el-descriptions :column="1" size="1" class="margin-top" title="讲座详情" border>
          <el-descriptions-item>
            <template slot="label">讲座名称</template>{{ lecture.title }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">讲座类型</template>{{ lecture.typeName }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">主讲嘉宾</template>{{ lecture.speaker }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">开始时间</template>{{ moment(lecture.lectureStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">讲座描述</template>{{ lecture.description }}
          </el-descriptions-item>
        </el-descriptions>

        <!-- 签到统计 -->
        <el-descriptions :column="2" size="1" class="margin-top" style="margin-top:50px" title="签到统计" border>
          <el-descriptions-item>
            <template slot="label">预约数</template><el-tag>{{ lecture.reservation - lecture.store }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">签到数</template><el-tag type="success">{{ lecture.signCount }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
      <!-- 海报 -->
      <el-col :span="10">
        <div align="center">
          <el-image :src="lecture.poster" style="width: 80%" lazy/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import lectureApi from '@/api/lecture/lecture'

export default {
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
        space: '',
        speaker: '',
        reservation: '',
        store: '',
        description: '',
        poster: '',
        orderStartTime: '',
        orderEndTIme: '',
        lectureStartTime: '',
        userCount: '',
        signCount: '',
        notAttendCount: '',
        userList: []
      },
      moment: require('moment')
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
      lectureApi.getLectureInfoForAdminById(this.lecture.id).then(res => {
        this.lecture = res.lectureInfo
      }).catch(err => {
        console.log('getLectureById Error: ' + err)
      })
    },
    timeFormatter(data) { // 讲座开始时间
      const moment = require('moment')
      return moment(data.orderTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    }
  }
}
</script>
