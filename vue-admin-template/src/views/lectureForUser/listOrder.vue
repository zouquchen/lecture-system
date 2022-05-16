<!-- 用户（学生）可预约讲座列表-->
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

      <el-table-column label="状态" width="80" align="center">
        <template slot-scope="scope">
          <el-tag :type="judgeState(scope.row.orderStartTime)? 'success' : 'danger'">
            {{ judgeState(scope.row.orderStartTime) ? '可预约' : '未开放' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="typeName" label="类型" width="60" align="center"/>

      <el-table-column prop="organizer" label="主办方" width="200" align="center"/>

      <el-table-column prop="space" label="地点" width="150" align="center"/>

      <el-table-column label="剩余数量" width="100" align="center">
        <template slot-scope="scope">
          <el-tag type="info">{{ scope.row.store + " / " + scope.row.reservation }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column :formatter="orderStartFormatter" label="预约开始" width="100" align="center"/>

      <el-table-column :formatter="orderEndFormatter" label="预约结束" width="100" align="center"/>

      <el-table-column :formatter="lectureStartFormatter" label="开始时间" width="100" align="center"/>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/lectureForUser/info/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-document">详情</el-button>
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
      lecture.getLectureUserListPage(this.page, this.limit, this.lectureQuery).then(res => {
        // 请求成功
        this.list = res.records
        this.total = res.total
      }).catch(err => {
        // 请求失败
        console.log(err)
      })
    },
    // 获取当前时间
    getCurrentTime() {
      const moment = require('moment')
      var date = new Date()
      var cur = moment(date).format('YYYY-MM-DD HH:mm:ss')
      console.log(cur)
      return cur
    },
    // 格式化时间
    formatterDateTime(date) {
      const moment = require('moment')
      var cur = moment(date).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
      console.log(cur)
      return cur
    },
    // 判断状态为：true: 可预约 fasle: 未开放
    judgeState(date) {
      const moment = require('moment')
      var cur = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      var start = moment(date).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
      return cur > start
    },
    // 时间格式Formatter
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