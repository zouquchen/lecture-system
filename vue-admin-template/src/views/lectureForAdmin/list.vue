<template>
  <div class="app-container">
    <!-- 条件查询-->
    <el-form :inline="true" class="demo-form-inline" style="margin-left: 20px; margin-top: 12px;">
      <el-form-item label="讲座名称">
        <el-input v-model="lectureQuery.title" placeholder="请输入名称"/>
      </el-form-item>
      <el-form-item label="讲座时间">
        <el-date-picker
          v-model="lectureQuery.startTime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
          type="datetime"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="lectureQuery.endTime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
          type="datetime"
        />
      </el-form-item>
      <el-form-item label="讲座类型">
        <el-select v-model="lectureQuery.typeId" placeholder="讲座类型">
          <el-option label="全部" value=""/>
          <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格内容-->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="活动名称" align="center"/>

      <el-table-column label="状态" width="80" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.state == '1' ? 'danger' : 'success'">{{ scope.row.state == '1' ? '已结束' : '已发布' }}</el-tag>
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
import lectureApi from '@/api/lecture/lecture.js'
import lectureTypeApi from '@/api/lecture/lectureType'

export default {
  data() {
    // 定义变量和初始值
    return {
      list: null, // 查询之后给接口返回的数据装的集合
      page: 1, // 当前页
      limit: 5, // 每页显示记录数
      lectureQuery: { }, // 条件封装对象
      typeList: { }, // 讲座类型列表
      total: 0 // 总记录数
    }
  },
  created() {
    // 页面渲染之前执行，调用method定义的方法
    this.getLectureTypeList()
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      lectureApi.getLectureAdminListPage(this.page, this.limit, this.lectureQuery).then(res => {
        // 请求成功
        this.list = res.records
        this.total = res.total
      }).catch(err => {
        // 请求失败
        console.log(err)
      })
    },
    // 获取活动类型列表选项
    getLectureTypeList() {
      lectureTypeApi.getLectureTypeList().then(res => {
        this.typeList = res.typeList
      }).catch(err => {
        console.log('获取活动类型列表失败：' + err)
      })
    },
    // 清空方法
    resetData() {
      // 表单输入项数据清空
      this.lectureQuery = {}
      // 查询所有讲座数据
      this.getList()
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
