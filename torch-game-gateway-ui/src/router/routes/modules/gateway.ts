import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const gateway: AppRouteModule = {
  path: '/gateway',
  name: 'Gateway',
  component: LAYOUT,
  redirect: '/gateway/serviceList',
  meta: {
    orderNo: 30,
    icon: 'ant-design:gateway-outlined',
    title: '网关管理',
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
        icon: 'ri:route-fill',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/route/index.vue'),
    },
    {
      path: 'call-log',
      name: 'CallLog',
      meta: {
        title: '调用日志',
        icon: 'icon-park-outline:log',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/call-log/index.vue'),
    },
    {
      path: 'cluster/monitor',
      name: 'ClusterMonitor',
      meta: {
        title: '集群监控',
        icon: 'ant-design:database-outlined',
        ignoreKeepAlive: true,
      },
      component: () => import('/@/views/system/cluster/monitor/index.vue'),
    },
    {
      path: 'limit/:domain',
      name: 'LimitManagement',
      meta: {
        hideMenu: true,
        title: '限流器管理',
        ignoreKeepAlive: false,
        showMenu: false,
        currentActiveMenu: '/gateway',
      },
      component: () => import('/@/views/system/limit/index.vue'),
    },
    {
      path: 'detail/:name',
      name: 'ServiceDetail',
      meta: {
        hideMenu: true,
        title: '服务详情',
        ignoreKeepAlive: false,
        showMenu: false,
        currentActiveMenu: '/gateway',
      },
      component: () => import('/@/views/service/ServiceDetail.vue'),
    },
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

export default gateway;
