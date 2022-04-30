import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const service: AppRouteModule = {
  path: '/service',
  name: 'Service',
  component: LAYOUT,
  redirect: '/service/serviceList',
  meta: {
    orderNo: 30,
    icon: 'ion:aperture-outline',
    title: '服务管理',
  },
  children: [
    {
      path: 'serviceList',
      name: 'ServiceList',
      meta: {
        title: '服务列表',
        icon: 'ant-design:cloud-server-outlined',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/service/index.vue'),
    },
    {
      path: 'routeList',
      name: 'RouteList',
      meta: {
        title: '路由列表',
        icon: 'ant-design:cloud-server-outlined',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/route/index.vue'),
    },
    {
      path: 'call-log',
      name: 'CallLog',
      meta: {
        title: '调用日志',
        icon: 'ant-design:cloud-server-outlined',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/call-log/index.vue'),
    },
    // {
    //   path: 'detail/:name',
    //   name: 'ServiceDetail',
    //   meta: {
    //     hideMenu: true,
    //     title: '服务详情',
    //     ignoreKeepAlive: false,
    //     showMenu: false,
    //     currentActiveMenu: '/serivce',
    //   },
    //   component: () => import('/@/views/service/ServiceDetail.vue'),
    // },
    // {
    //   path: 'test',
    //   name: 'RouteTest',
    //   meta: {
    //     title: '路由测试',
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('/@/views/route/RouteTest.vue'),
    // },
  ],
};

export default service;
