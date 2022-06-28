<template>
  <div class="app-container">
    <!-- 条件查询-->
    <el-form :inline="true" class="demo-form-inline" style="margin-left: 20px; margin-top: 12px;">
      <el-form-item label="用户名称">
        <el-input v-model="userQuery.username" placeholder="请输入名称" />
      </el-form-item>
      <el-form-item label="用户角色">
        <el-select v-model="userQuery.roleId" placeholder="用户角色">
          <el-option label="全部" value="" />
          <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>

      <el-form-item style="float: right">
        <el-button type="success" icon="el-icon-plus" @click="dialogFormVisible = true">添加用户</el-button>
      </el-form-item>
    </el-form>

    <!-- 添加用户 -->
    <el-dialog :visible.sync="dialogFormVisible" title="添加用户">
      <!--  <el-alert title="默认密码: 123456" type="success" />-->
      <el-form ref="newUser" :model="newUser" :rules="rules">
        <el-form-item :label-width="formLabelWidth" label="用户名" prop="username">
          <el-input v-model="newUser.username" autocomplete="off" />
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="密码" prop="password">
          <el-input v-model="newUser.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="密码" prop="checkPass">
          <el-input v-model="newUser.checkPass" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="角色" prop="role">
          <el-select v-model="newUser.role" placeholder="请选择">
            <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="性别">
          <el-select v-model="newUser.sex" placeholder="请选择">
            <el-option label="男" :value="0" />
            <el-option label="女" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="邮箱" prop="email">
          <el-input v-model="newUser.email" autocomplete="off" />
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="手机号" prop="phoneNumber">
          <el-input v-model="newUser.phoneNumber" autocomplete="off" />
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="上传头像">
          <el-upload
            class="avatar-uploader"
            :before-upload="beforeAvatarUpload"
            :on-success="handleUploadSuccess"
            :action="BASE_API + '/oss/uploadImage'"
            :headers="uploadElement.headerSetToken"
            :show-file-list="false"
          >
            <img v-if="newUser.avatar" :src="newUser.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button @click="resetForm('newUser')">重置</el-button>
        <el-button type="primary" @click="submit('newUser')">添 加</el-button>
      </div>
    </el-dialog>

    <!-- 表格内容-->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" align="center" />

      <el-table-column prop="role" label="角色" width="200" align="center" />

      <el-table-column prop="status" label="状态" width="150" align="center" />

      <el-table-column prop="email" label="邮箱" width="200" align="center" />

      <el-table-column prop="phoneNumber" label="电话" width="150" align="center" />

      <el-table-column prop="sex" label="性别" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.sex == null ? '' : (scope.row.sex == 0 ? '男' : '女') }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" icon="el-icon-delete" @click="deleteUserById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <el-pagination
      :total="total"
      :page-size="limit"
      :current-page="page"
      background
      layout="total, prev, pager, next, jumper"
      style="padding: 30px 0; text-align: center"
      @current-change="getList"
    />
  </div>
</template>

<script>
import userApi from '@/api/user/user.js'

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.newUser.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 定义变量和初始值
    return {
      list: null, // 查询之后给接口返回的数据装的集合
      page: 1, // 当前页
      limit: 10, // 每页显示记录数
      userQuery: { }, // 条件封装对象
      roleList: { }, // 角色列表
      total: 0, // 总记录数
      dialogFormVisible: false,
      formLabelWidth: '120px',
      BASE_API: process.env.VUE_APP_BASE_API, // 获取dev.env.js里面地址
      newUser: {
        username: '',
        password: '123456',
        checkPass: '123456',
        role: '',
        email: '',
        phoneNumber: '',
        sex: '',
        avatar: 'http://www.weixintouxiang.cn/weixin/20140607090832328.gif'
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色权限' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        phoneNumber: [
          { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      },
      // 上传组件的参数
      uploadElement: {
        limit: 1,
        headerSetToken: {
          token: this.$store.getters.token // 从vuex中获取token
        },
        fileList: []
      }
    }
  },
  created() {
    // 页面渲染之前执行，调用method定义的方法
    this.getList()
    this.getRoleList()
    console.log(process.env.VUE_APP_BASE_API)
  },
  methods: {
    getList(page = 1) {
      this.page = page
      userApi.getUserListPage(this.page, this.limit, this.userQuery).then(res => {
        // 请求成功
        this.list = res.records
        this.total = res.total
      }).catch(err => {
        // 请求失败
        console.log(err)
      })
    },
    // 获取权限列表
    getRoleList() {
      userApi.getRoleList().then(res => {
        this.roleList = res.roleList
      }).catch(err => {
        console.log('获取角色列表失败：' + err)
      })
    },
    // 清空方法
    resetData() {
      // 表单输入项数据清空
      this.userQuery = {}
      // 查询所有讲座数据
      this.getList()
    },
    // 添加用户
    addUser() {
      userApi.addUser(this.newUser).then(res => {
        // 提示信息
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        this.$router.go(0)
      })
    },
    // 提交表格
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.addUser()
        } else {
          return false
        }
      })
    },
    // 重置表格
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    // 删除用户
    deleteUserById(id) {
      userApi.deleteUser(id).then(res => {
        // 提示信息
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.$router.go(0)
      })
    },
    // --------------------------------------------- 上传文件图片 ---------------------------------------------
    // 上车前校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    // on success 上传文件成功时的钩子
    handleUploadSuccess(response, file, fileList) {
      if (response.code === 20000) {
        this.newUser.avatar = response.url
      } else {
        console.log('上传失败！！')
      }
    }
  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
