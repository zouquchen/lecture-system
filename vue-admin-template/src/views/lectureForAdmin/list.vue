<template>
  <div class="app-container">

    <!--表格内容-->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="活动名称" align="center"/>

      <el-table-column prop="state" label="状态" width="80" align="center"/>

      <el-table-column prop="typeName" label="类型" width="60" align="center"/>

      <el-table-column prop="organizer" label="主办方" width="200" align="center"/>

      <el-table-column prop="space" label="地点" width="150" align="center"/>

      <el-table-column prop="reservation" label="容纳人数" width="80" align="center"/>

      <el-table-column prop="store" label="剩余数量" width="80" align="center"/>

      <el-table-column :formatter="orderStartFormatter" label="预约时间" width="100" align="center"/>

      <el-table-column :formatter="orderEndFormatter" label="预约时间" width="100" align="center"/>

      <el-table-column :formatter="lectureStartFormatter" label="开始时间" width="100" align="center"/>

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/lectureForAdmin/info/' + scope.row.id">
            <el-button type="info" size="mini" icon="el-icon-document">查看</el-button>
          </router-link>
          <router-link :to="'/lectureForAdmin/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
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
import lecture from '@/api/lecture/lecture.js'

export default {
  data() {
    // 定义变量和初始值
    return {
      list: null, // 查询之后给接口返回的数据装的集合
      page: 1, // 当前页
      limit: 5, // 每页显示记录数
      lectureQuery: { }, // 条件封装对象
      total: 0 // 总记录数
    }
  },
  created() {
    // 页面渲染之前执行，调用method定义的方法
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      lecture.getLectureListPage(this.page, this.limit, this.teacherQuery).then(res => {
        // 请求成功
        this.list = res.records
        this.total = res.total
      }).catch(err => {
        // 请求失败
        console.log(err)
      })
    },

    // 时间格式Formatter
    orderStartFormatter(data) { // 预约开始时间
      // 需要导入moment依赖，npm install moment，在main.js导入依赖
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
