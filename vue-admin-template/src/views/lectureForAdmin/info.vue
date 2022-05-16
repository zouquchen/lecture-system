<template>
  <div class="app-container">
    <el-row>
      <el-col :span="10">
        <div align="center">
          <el-image :src="lecture.poster" style="width: 80%" lazy/>
        </div>
      </el-col>
      <el-col :span="14">
        <el-descriptions :column="1" size="1" class="margin-top" title="讲座详情" border>
          <template slot="extra">
            <router-link :to="'/lectureForAdmin/edit/' + lecture.id">
              <el-button type="primary" size="small">修改</el-button>
            </router-link>
          </template>
          <el-descriptions-item>
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
            <template slot="label"><i class="el-icon-user"/>可预约人数</template>{{ lecture.store }} / {{ lecture.reservation }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-time"/>预约时间</template>
            {{ moment(lecture.orderStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }} —
            {{ moment(lecture.orderEndTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-time"/>开始时间</template>{{ moment(lecture.lectureStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">讲座描述</template>{{ lecture.description }}
          </el-descriptions-item>
        </el-descriptions>
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
    }
  }
}
</script>
