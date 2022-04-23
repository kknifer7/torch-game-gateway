import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const config: AppRouteModule = {
  path: '/config',
  name: 'Config',
  component: LAYOUT,
  redirect: '/config/index',
  meta: {
    orderNo: 20,
    icon: 'ion:link-outline',
    title: '基础配置',
    hideChildrenInMenu: true,
  },
  children: [
    {
      path: 'index',
      name: 'Config',
      meta: {
        title: '基础配置',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/config/index.vue'),
    },
  ],
};

export default config;
