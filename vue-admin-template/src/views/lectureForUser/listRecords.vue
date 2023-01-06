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

      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="judgeDisplayState(scope.row.displayState)">{{ scope.row.displayState }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="title" label="活动名称" align="center" />

      <el-table-column prop="typeName" label="类型" width="60" align="center" />

      <el-table-column prop="organizer" label="主办方" width="200" align="center" />

      <el-table-column prop="space" label="地点" width="150" align="center" />

      <el-table-column :formatter="lectureStartFormatter" label="开始时间" width="100" align="center" />

      <el-table-column :formatter="orderTimeFormatter" label="预约时间" width="100" align="center" />

      <el-table-column :formatter="signTimeFormatter" label="签到时间" width="100" align="center" />

      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <router-link :to="'/lectureForUser/info/' + scope.row.lectureId">
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
      limit: 10, // 每页显示记录数
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
      lecture.getLectureUserRecordPageList(this.page, this.limit).then(res => {
        // 请求成功
        this.list = res.records
        this.total = res.total
      }).catch(err => {
        // 请求失败
        console.log(err)
      })
    },
    // 时间格式Formatter
    lectureStartFormatter(data) { // 讲座开始时间
      const moment = require('moment')
      return moment(data.lectureStartTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    },
    orderTimeFormatter(data) { // 预约时间
      const moment = require('moment')
      return moment(data.orderTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    },
    signTimeFormatter(data) { // 签到时间
      if (data.signTime === null) {
        return ''
      }
      const moment = require('moment')
      return moment(data.signTime).utcOffset(480).format('YYYY-MM-DD HH:mm:ss')
    },
    judgeDisplayState(data) {
      if (data === '未开放') return 'warning'
      if (data === '已预约') return ''
      if (data === '未预约') return 'info'
      if (data === '已签到') return 'success'
      if (data === '缺席') return 'danger'
      if (data === '已结束') return 'warning'
    }

  }
}
</script>
