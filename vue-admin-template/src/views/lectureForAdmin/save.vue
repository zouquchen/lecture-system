<template>
  <div class="app-container">
    <el-row>
      <el-col :span="14">
        <!-- 表单-->
        <el-form ref="lecture" :model="lecture" :rules="rules" label-width="100px">
          <el-form-item label="讲座名称" prop="title">
            <el-input v-model="lecture.title" placeholder="请输入讲座名称（30字以内）" />
          </el-form-item>
          <el-form-item label="讲座类型" prop="typeId">
            <el-select v-model="lecture.typeId" placeholder="请选择活动类型">
              <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="讲座状态" prop="state">
            <el-radio-group v-model="lecture.state">
              <el-radio :label="0">发布</el-radio>
              <el-radio :label="1">结束</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="主办方" prop="organizer">
            <el-input v-model="lecture.organizer" placeholder="东南大学研究生会" />
          </el-form-item>
          <el-form-item label="承办方" prop="undertaker">
            <el-input v-model="lecture.undertaker" placeholder="东南大学研究生会" />
          </el-form-item>
          <el-form-item label="协办方" prop="sponsor">
            <el-input v-model="lecture.sponsor" placeholder="东南大学自动化学院" />
          </el-form-item>
          <el-form-item label="主讲嘉宾" prop="speaker">
            <el-input v-model="lecture.speaker" placeholder="请输入主讲嘉宾姓名" />
          </el-form-item>
          <el-form-item label="可预约人数" prop="reservation">
            <el-input v-model.number="lecture.reservation" placeholder="400" />
          </el-form-item>
          <el-form-item label="讲座地点" prop="space">
            <el-input v-model="lecture.space" placeholder="请输入讲座地点" />
          </el-form-item>
          <el-form-item label="预约时间" required>
            <el-col :span="6">
              <el-form-item prop="orderStartTime">
                <el-date-picker v-model="lecture.orderStartTime" type="datetime" placeholder="开始时间" style="width: 100%;" />
              </el-form-item>
            </el-col>
            <el-col :span="1" class="line" align="center">-</el-col>
            <el-col :span="6">
              <el-form-item prop="orderEndTime">
                <el-date-picker v-model="lecture.orderEndTime" type="datetime" placeholder="结束时间" style="width: 100%;" />
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="讲座时间" required>
            <el-col :span="6">
              <el-form-item prop="lectureStartTime">
                <el-date-picker v-model="lecture.lectureStartTime" type="datetime" placeholder="开始时间" style="width: 100%;" />
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="讲座介绍" prop="description">
            <el-input v-model="lecture.description" type="textarea" placeholder="讲座详细描述信息（200字以内）" />
          </el-form-item>

          <!-- 上传文件 -->
          <el-form-item label="讲座海报">
            <el-upload
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :on-success="handleUploadSuccess"
              :file-list="uploadElement.fileList"
              :action="BASE_API + '/oss/uploadImage'"
              :headers="uploadElement.headerSetToken"
              :limit="uploadElement.limit"
            >
              <el-button class="upload-button" size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件, 且仅能上传一张海报。</div>
            </el-upload>
          </el-form-item>

          <!-- 按钮 -->
          <el-form-item>
            <el-button type="primary" @click="onSubmit('lecture')">保存</el-button>
            <el-button @click="cancel">取消</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!-- 显示上传的图片-->
      <el-col :span="10">
        <div align="center" class="block" style="">
          <el-image :src="lecture.poster" style="width: 60%" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import lectureTypeApi from '@/api/lecture/lectureType'
import lectureApi from '@/api/lecture/lecture'

export default {
  data() {
    return {
      // lecutre参数详情
      lecture: { },
      // 获取dev.env.js里面地址
      BASE_API: process.env.VUE_APP_BASE_API,
      // 上传组件的参数
      uploadElement: {
        limit: 1,
        headerSetToken: {
          token: this.$store.getters.token // 从vuex中获取token
        },
        fileList: []
      },
      // 活动类型列表
      typeList: {},
      // 前端数据格式校验
      rules: {
        title: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        typeId: [
          { required: true, message: '请选择活动内容', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '请选择活动状态', trigger: 'blur' }
        ],
        space: [
          { required: true, message: '请输入活动地点', trigger: 'blur' },
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        reservation: [
          { required: true, message: '请输入可预约人数' },
          { type: 'number', message: '人数必须为数字值' }
        ],
        organizer: [
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        undertaker: [
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        sponsor: [
          { min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur' }
        ],
        speaker: [
          { required: true, message: '请输入主讲嘉宾' },
          { min: 2, max: 30, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        description: [
          { min: 2, max: 30, message: '长度在 2 到 200 个字符', trigger: 'blur' }
        ],
        orderStartTime: [
          { required: true, message: '请选择时间', trigger: 'change' }
        ],
        orderEndTime: [
          { required: true, message: '请选择时间', trigger: 'change' }
        ],
        lectureStartTime: [
          { required: true, message: '请选择时间', trigger: 'change' }
        ]
      }
    }
  },
  watch: { // 监听
    $route(to, from) { // 路由变化方式，路由发生变化，方法就会执行
      this.initData()
    }
  },
  created() {
    // 页面渲染之前执行，调用method定义的方法
    this.getLectureTypeList()
    this.initData()
  },
  methods: {
    // 初始化方法
    initData() {
      // 判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        // 从路径获取id值
        console.log('有id: ' + this.$route.params.id)
        this.lecture.id = this.$route.params.id
        // 调用根据id查询讲座信息
        this.getLecture(this.lecture.id)
      } else {
        // 清空表单，默认就是null
        this.lecture = {
          // id: '',
          // title: '',
          // typeId: '',
          // creatorId: '',
          // description: '',
          // organizer: '',
          // undertaker: '',
          // sponsor: '',
          // space: '',
          // speaker: '',
          // reservation: '',
          // state: '',
          poster: 'https://lecture-system.oss-cn-shanghai.aliyuncs.com/images/noPicture.png'
          // orderStartTime: '',
          // orderEndTIme: '',
          // lectureStartTime: ''
        }
      }
    },
    // 获取活动类型列表选项
    getLectureTypeList() {
      lectureTypeApi.getLectureTypeList().then(res => {
        this.typeList = res.typeList
      }).catch(err => {
        console.log('获取活动类型列表失败：' + err)
      })
    },
    // 如果是更新，获取讲座信息
    getLecture() {
      lectureApi.getLectureInfoForAdminById(this.lecture.id).then(res => {
        this.lecture = res.lectureInfo
      }).catch(err => {
        console.log('修改讲座时，获取讲座信息失败：' + err)
      })
    },
    // 添加讲座讲座
    addLecture() {
      lectureApi.addLecture(this.lecture).then(res => {
        // 提示信息
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        // 回到列表页面，路由跳转
        this.$router.push({ path: '/lectureForAdmin/table' })
      }).catch(err => {
        console.log('添加讲座失败：' + err)
      })
    },
    // 更新讲座
    updateLecture() {
      lectureApi.updateLecture(this.lecture).then(res => {
        // 提示信息
        this.$message({
          type: 'success',
          message: '修改成功'
        })
        // 回到列表页面，路由跳转
        this.$router.push({ path: '/lectureForAdmin/table' })
      }).catch(err => {
        console.log('修改讲座失败：' + err)
      })
    },
    // --------------------------------------------- 上传文件图片 ---------------------------------------------
    // 设置上传请求头
    // on-remove 文件列表移除文件时的钩子
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    // on-preview 点击文件列表中已上传的文件时的钩子
    handlePreview(file) {
      console.log(file)
    },
    // on success 上传文件成功时的钩子
    handleUploadSuccess(response, file, fileList) {
      if (response.code === 20000) {
        this.lecture.poster = response.url
      } else {
        console.log('上传失败！！')
      }
    },
    // --------------------------------------------- 创建 ---------------------------------------------
    // 点击创建按钮
    onSubmit(formName) {
      // 先校验数据格式
      this.$refs[formName].validate((valid) => {
        // 数据校验成功
        if (valid) {
          // 判断是修改还是添加
          if (this.lecture.id) {
            // 有id，更新
            this.$confirm('此操作将修改讲座信息, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.updateLecture()
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消修改'
              })
            })
          } else {
            // 无id，添加
            this.$confirm('此操作将添加新的讲座, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.addLecture()
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消添加'
              })
            })
          }
        } else {
          return false
        }
      })
    },
    cancel() {
      // 回到列表页面，路由跳转
      this.$router.push({ path: '/lectureForAdmin/table' })
    }
  }
}
</script>
