<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>个人信息</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="dialogFormVisible = true">编辑</el-button>

        <el-dialog :visible.sync="dialogFormVisible" title="修改信息">
          <el-form :model="userInfoForUpdate" :rules="rules">
            <el-form-item :label-width="formLabelWidth" label="用户名" prop="username">
              <el-input v-model="userInfoForUpdate.username" autocomplete="off" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="性别">
              <el-select v-model="userInfoForUpdate.sex" placeholder="请选择">
                <el-option label="男" value="0" />
                <el-option label="女" value="1" />
              </el-select>
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="邮箱" prop="email">
              <el-input v-model="userInfoForUpdate.email" autocomplete="off" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="手机号" prop="phoneNumber">
              <el-input v-model="userInfoForUpdate.phoneNumber" autocomplete="off" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateUserInfo">确 定</el-button>
          </div>
        </el-dialog>
      </div>
      <el-col :span="2">
        <div>
          <el-image :src="userInfo.avatar" style="width: 100px; height: 100px" />
        </div>
      </el-col>
      <el-col :span="20">
        <div class="text item">
          <h2> {{ userInfo.username }} </h2>
          <el-descriptions :column="3" class="margin-top" title="">
            <el-descriptions-item>
              <template slot="label"><i class="el-icon-user" />性别</template>{{ userInfo.sex == null ? '' : (userInfo.sex == 0 ? '男' : '女') }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><i class="el-icon-message" />邮箱</template>{{ userInfo.email }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label"><i class="el-icon-mobile-phone" />手机号</template>{{ userInfo.phoneNumber }}
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </el-col>

    </el-card>

    <el-card class="box-card" style="margin-top:30px">
      <div slot="header" class="clearfix">
        <span>讲座统计</span>
      </div>
      <div class="text item">
        <el-descriptions :column="2" class="margin-top" title="">
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-star-off" />总场次</template>{{ orderCount }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-magic-stick" />未开始</template>{{ notOpen }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-circle-check" />出席次数</template>{{ signCount }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-circle-close" />缺席次数</template>{{ notAttendCount }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
  </div>

</template>

<script>
import userApi from '@/api/user/user'
import lectureUserRecordApi from '@/api/lecture/lectureUserRecord'

export default {
  data() {
    return {
      userInfo: {
        username: '',
        email: '',
        phoneNumber: '',
        sex: '',
        avatar: ''
      },
      userInfoForUpdate: {},
      orderCount: '',
      notAttendCount: '',
      signCount: '',
      notOpen: '',
      dialogFormVisible: false,
      formLabelWidth: '120px',
      rules: {
        usernmae: [
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        phoneNumber: [
          { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getInfo()
    this.getDataOfUserRecord()
  },
  methods: {
    getInfo() {
      // 获得用户详情
      userApi.getUserInfo().then(res => {
        this.userInfo = res.userInfo
        this.userInfoForUpdate = { ...this.userInfo }
      }).catch(err => {
        console.log('getUserInfo Error: ' + err)
      })
    },
    updateUserInfo() {
      this.dialogFormVisible = false
      userApi.updateUserInfo(this.userInfoForUpdate).then(res => {
        this.$router.go(0)
      }).catch(err => {
        console.log('updateUserInfo Error: ' + err)
      })
    },
    getDataOfUserRecord() {
      // 获得用户预约详情
      lectureUserRecordApi.getDataOfUserRecord().then(res => {
        this.orderCount = res.orderCount
        this.notAttendCount = res.notAttendCount
        this.notOpen = res.notOpen
        this.signCount = res.signCount
      }).catch(err => {
        console.log('getDataOfUserRecord Error: ' + err)
      })
    }

  }
}
</script>
