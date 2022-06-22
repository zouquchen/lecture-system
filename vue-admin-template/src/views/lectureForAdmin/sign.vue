<template>
  <div class="app-container">
    <el-row>
      <el-col :span="14">
        <!-- 讲座详情 -->
        <el-descriptions :column="1" size="1" class="margin-top" title="讲座详情" border>
          <!-- 关闭签到按钮 -->
          <template slot="extra">
            <el-popover v-model="closeButtonVisible" placement="top" width="160">
              <p>你确定要关闭签到系统吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button type="text" size="mini" @click="closeButtonVisible = false">取消</el-button>
                <el-button type="primary" size="mini" @click="closeSign">确定</el-button>
              </div>
              <el-button slot="reference" type="danger">关闭签到</el-button>
            </el-popover>
          </template>
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
          <!-- 签到统计内容 -->
          <el-descriptions-item :span="2">
            <template slot="label">账号</template>
            <el-row>
              <el-input v-model="username" placeholder="用户账号" style="width:200px"/>
              <el-button type="success" @click="userSign">签到</el-button>
            </el-row>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">预约数</template><el-tag>{{ lecture.reservation - lecture.store }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">签到数</template><el-tag type="success">{{ lecture.signCount }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>

        <!-- 已签到用户 -->
        <el-descriptions class="margin-top" style="margin-top:50px" title="已签到用户" border/>
        <el-table :data="recordList" height="500" border style="width: 100%">
          <el-table-column label="签到时间">
            <template slot-scope="scope">{{ moment(scope.row.signTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss') }}</template>
          </el-table-column>
          <el-table-column prop="username" label="用户"/>
        </el-table>
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
import lectureUserRecordApi from '@/api/lecture/lectureUserRecord'

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
      recordList: [],
      username: '',
      closeButtonVisible: false,
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
        console.log('getLectureInfoForSignById Error: ' + err)
      })
      lectureUserRecordApi.getSignedUserList(this.lecture.id).then(res => {
        this.recordList = res.recordList
      }).catch(err => {
        console.log('getSignedUserList Error: ' + err)
      })
    },
    // 用户签到
    userSign() {
      lectureUserRecordApi.orderLectureById(this.lecture.id, this.username).then(res => {
        this.$router.go(0)
      }).catch(err => {
        console.log('签到失败：' + err)
      })
    },
    // 关闭签到
    closeSign() {
      this.closeButtonVisible = false
      lectureApi.finishLectureSignById(this.lecture.id).then(res => {
        this.$router.push({ path: '/' })
      }).catch(err => {
        console.log('签到关闭失败：' + err)
      })
    }
  }
}
</script>
