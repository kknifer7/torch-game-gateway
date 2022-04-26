import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const route: AppRouteModule = {
  path: '/route',
  name: 'Route',
  component: LAYOUT,
  redirect: '/route/index',
  meta: {
    orderNo: 30,
    icon: 'ion:aperture-outline',
    title: '路由管理',
  },
  children: [
    {
      path: 'index',
      name: 'RouteIndex',
      meta: {
        title: '路由列表',
        icon: 'ant-design:cloud-server-outlined',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/route/index.vue'),
    },
    {
      path: 'detail/:id',
      name: 'RouteDetail',
      meta: {
        hideMenu: true,
        title: '路由详情',
        ignoreKeepAlive: false,
        showMenu: false,
        currentActiveMenu: '/route',
      },
      component: () => import('/@/views/route/RouteDetail.vue'),
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

export default route;
