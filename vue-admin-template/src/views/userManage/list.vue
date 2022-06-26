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
    </el-form>

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
    // 定义变量和初始值
    return {
      list: null, // 查询之后给接口返回的数据装的集合
      page: 1, // 当前页
      limit: 5, // 每页显示记录数
      userQuery: { }, // 条件封装对象
      roleList: { }, // 角色列表
      total: 0 // 总记录数
    }
  },
  created() {
    // 页面渲染之前执行，调用method定义的方法
    this.getList()
    this.getRoleList()
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
    }
  }
}
</script>
