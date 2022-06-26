import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/index',
    hidden: true,
    children: [{
      path: 'index',
      component: () => import('@/views/index/index')
    }]
  },
  {
    path: '/personalCenter',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '个人中心',
        component: () => import('@/views/personalCenter/index'),
        meta: { title: '个人中心', icon: 'user-fill' }
      }
    ]
  }

]

export const asyncRoutes = [
  {
    // 地址输出
    path: '/lectureForAdmin',
    component: Layout,
    // 重定向地址
    redirect: '/lectureForAdmin/table',
    name: '讲座管理',
    // title：显式标签，icon：显示图标
    meta: { title: '讲座管理', icon: 'all-fill', roles: ['admin', 'manager'] },
    children: [
      {
        path: 'table',
        name: '讲座列表（管理员）',
        component: () => import('@/views/lectureForAdmin/list.vue'),
        meta: { title: '讲座列表', icon: 'menu' }
      },
      {
        path: 'add',
        name: '添加讲座',
        component: () => import('@/views/lectureForAdmin/save.vue'),
        meta: { title: '添加讲座', icon: 'plus-square' }
      },
      {
        path: 'edit/:id',
        name: '修改讲座',
        component: () => import('@/views/lectureForAdmin/save.vue'),
        meta: { title: '修改讲座' },
        hidden: true
      },
      {
        path: 'info/:id',
        name: '讲座详情（管理员）',
        component: () => import('@/views/lectureForAdmin/info.vue'),
        meta: { title: '讲座详情' },
        hidden: true
      },
      {
        path: 'sign/:id',
        name: '讲座签到',
        component: () => import('@/views/lectureForAdmin/sign.vue'),
        meta: { title: '讲座签到' },
        hidden: true
      }
    ]
  },
  {
    path: '/lectureForUser',
    component: Layout,
    redirect: '/lectureForUser/listOrder',
    name: '讲座预约',
    meta: { title: '讲座预约', icon: 'example', roles: ['student'] },
    children: [
      {
        path: 'listOrder',
        name: '讲座列表（用户）',
        component: () => import('@/views/lectureForUser/listOrder.vue'),
        meta: { title: '讲座列表', icon: 'menu' }
      },
      {
        path: 'listRecords',
        name: '我的讲座',
        component: () => import('@/views/lectureForUser/listRecords.vue'),
        meta: { title: '我的讲座', icon: 'collection' }
      },
      {
        path: 'info/:id',
        name: '讲座详情（用户）',
        component: () => import('@/views/lectureForUser/info.vue'),
        meta: { title: '讲座详情' },
        hidden: true
      }
    ]
  },
  {
    path: '/userManage',
    component: Layout,
    redirect: '/userManage/index',
    name: '用户管理',
    meta: { title: '用户管理', icon: 'user-group-fill', roles: ['admin'] },
    children: [
      {
        path: 'list',
        name: '用户列表',
        component: () => import('@/views/userManage/list'),
        meta: { title: '用户列表', icon: 'menu' }
      },
      {
        path: 'save',
        name: '添加用户',
        component: () => import('@/views/userManage/save'),
        meta: { title: '添加用户', icon: 'plus-square' }
      }
    ]
  },
  {
    path: '/webManage',
    component: Layout,
    meta: { roles: ['admin'] },
    children: [
      {
        path: 'index',
        name: '网站管理',
        component: () => import('@/views/webManage/index'),
        meta: { title: '网站管理', icon: 'cog-fill' }
      }
    ]
  }

]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
