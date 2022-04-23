import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const service: AppRouteModule = {
  path: '/service',
  name: 'Service',
  component: LAYOUT,
  redirect: '/service/index',
  meta: {
    orderNo: 30,
    icon: 'ion:aperture-outline',
    title: '服务管理',
  },
  children: [
    {
      path: 'index',
      name: 'ServiceIndex',
      meta: {
        title: '服务列表',
        icon: 'ant-design:cloud-server-outlined',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/service/index.vue'),
    },
    {
      path: 'detail/:id',
      name: 'ServiceDetail',
      meta: {
        hideMenu: true,
        title: '服务详情',
        ignoreKeepAlive: false,
        showMenu: false,
        currentActiveMenu: '/service',
      },
      component: () => import('/@/views/service/ServiceDetail.vue'),
    },
    // {
    //   path: 'test',
    //   name: 'ServiceTest',
    //   meta: {
    //     title: '服务测试',
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('/@/views/service/ServiceTest.vue'),
    // },
  ],
};

export default service;
