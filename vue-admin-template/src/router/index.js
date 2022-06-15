import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/index',
    name: 'Index',
    hidden: true,
    children: [{
      path: 'index',
      component: () => import('@/views/index/index')
    }]
  },

  {
    // 地址输出
    path: '/lectureForAdmin',
    component: Layout,
    // 重定向地址
    redirect: '/lectureForAdmin/table',
    name: '讲座管理',
    // title：显式标签，icon：显示图标
    meta: { title: '讲座管理', icon: 'example' },
    children: [
      {
        path: 'table',
        name: '讲座列表（管理员）',
        component: () => import('@/views/lectureForAdmin/list.vue'),
        meta: { title: '讲座列表', icon: 'table' }
      },
      {
        path: 'add',
        name: '添加讲座',
        component: () => import('@/views/lectureForAdmin/save.vue'),
        meta: { title: '添加讲座', icon: 'tree' }
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
      }
    ]
  },

  {
    path: '/lectureForUser',
    component: Layout,
    redirect: '/lectureForUser/listOrder',
    name: '讲座预约',
    meta: { title: '讲座预约', icon: 'example' },
    children: [
      {
        path: 'listOrder',
        name: '讲座列表（用户）',
        component: () => import('@/views/lectureForUser/listOrder.vue'),
        meta: { title: '讲座列表', icon: 'table' }
      },
      {
        path: 'listRecords',
        name: '我的讲座',
        component: () => import('@/views/lectureForUser/listRecords.vue'),
        meta: { title: '我的讲座', icon: 'tree' }
      },
      {
        path: 'info/:id',
        name: '讲座详情（用户）',
        component: () => import('@/views/lectureForUser/info.vue'),
        meta: { title: '讲座详情' },
        hidden: true
      },
      {
        path: 'infoRecord/:id',
        name: '已预约讲座详情（用户）',
        component: () => import('@/views/lectureForUser/infoRecord.vue'),
        meta: { title: '讲座详情' },
        hidden: true
      }
    ]
  },

  {
    path: '/authManage',
    component: Layout,
    redirect: '/authManage/index',
    name: '权限管理',
    meta: { title: '权限管理', icon: 'example' },
    children: [
      {
        path: 'index',
        name: '用户权限',
        component: () => import('@/views/authManage/index'),
        meta: { title: '用户权限', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/authManage/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/webManage',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '网站管理',
        component: () => import('@/views/webManage/index'),
        meta: { title: '网站管理', icon: 'table' }
      }
    ]
  },

  {
    path: '/personalCenter',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '用户中心',
        component: () => import('@/views/personalCenter/index'),
        meta: { title: '用户中心', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: 'menu2' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
