<template>
  <div class="app-container">
    <el-row>
      <el-col :span="14">
        <!-- 讲座详情 -->
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
            <template slot="label"><i class="el-icon-time" />预约时间</template>
            {{ moment(lecture.orderStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }} —
            {{ moment(lecture.orderEndTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-time" />开始时间</template>{{ moment(lecture.lectureStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">讲座描述</template>{{ lecture.description }}
          </el-descriptions-item>
        </el-descriptions>

        <!-- 预约统计 -->
        <el-descriptions :column="1" size="1" class="margin-top" style="margin-top:50px" title="预约统计" border>
          <el-descriptions-item>
            <template slot="label">总预约人数</template>{{ lecture.userCount }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">签到人数</template>{{ lecture.signCount }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">缺席人数</template>{{ lecture.notAttendCount }}
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
      <!-- 海报 -->
      <el-col :span="10">
        <div align="center">
          <el-image :src="lecture.poster" style="width: 80%" lazy />
        </div>
      </el-col>
    </el-row>

    <!-- 预约情况 -->
    <el-descriptions :column="1" size="1" class="margin-top" style="margin-top:50px" title="预约情况" border />
    <el-row>
      <el-col :span="24">
        <el-table :data="lecture.userList" border fit style="width: 100%" max-height="500">
          <el-table-column fixed prop="userId" label="用户id" align="center" />
          <el-table-column prop="username" label="用户名" align="center" />
          <el-table-column :formatter="timeFormatter" label="预约时间" align="center" />
          <el-table-column prop="displayState" label="参与情况" align="center" />
        </el-table>
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
      // 获得讲座详情
      lectureApi.getLectureInfoAndUserListForAdminById(this.lecture.id).then(res => {
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
