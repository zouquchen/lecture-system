<template>
  <div class="app-container">
    <template>
      <el-descriptions :column="3" size="1" class="margin-top" title="讲座详情" border>
        <template slot="extra">
          <router-link :to="'/lectureForAdmin/edit/' + lecture.id">
            <el-button type="primary" size="small">修改</el-button>
          </router-link>
        </template>
        <el-descriptions-item :span="3">
          <template slot="label">讲座名称</template>{{ lecture.title }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">讲座类型</template>{{ lecture.typeName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"/>主讲嘉宾</template>{{ lecture.speaker }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-location-information"/>讲座地点</template>{{ lecture.space }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-ice-cream-square"/>主办方</template>{{ lecture.organizer }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-ice-cream-square"/>承办方</template>{{ lecture.undertaker }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-ice-cream-square"/>协办方</template>{{ lecture.sponsor }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"/>发布者姓名</template>{{ lecture.creatorName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"/>可预约人数</template>{{ lecture.reservation }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"/>剩余预约人数</template>{{ lecture.store }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-time"/>预约开始时间</template>{{ lecture.orderStartTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-time"/>预约结束时间</template>{{ lecture.orderEndTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-time"/>讲座开始时间</template>{{ lecture.lectureStartTime }}
        </el-descriptions-item>
        <el-descriptions-item :span="3">
          <template slot="label">讲座描述</template>{{ lecture.description }}
        </el-descriptions-item>
        <el-descriptions-item :span="3">
          <template slot="label">海报</template>
          <div class="demo-image__lazy">
            <el-image :src="lecture.poster" lazy/>
          </div>
        </el-descriptions-item>
      </el-descriptions>
    </template>
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
        lectureStartTime: ''
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
      lectureApi.getLectureInfoById(this.lecture.id).then(res => {
        this.lecture = res.lectureInfo
      }).catch(err => {
        console.log('getLectureById Error: ' + err)
      })
    },
    orderStartFormatter(data) { // 预约开始时间
      const moment = require('moment')
      return moment(data.orderStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    },
    orderEndFormatter(data) { // 预约结束时间
      const moment = require('moment')
      return moment(data.orderEndTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    },
    lectureStartFormatter(data) { // 讲座开始时间
      const moment = require('moment')
      return moment(data.lectureStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    }
  }
}
</script>
