import type { AppRouteModule } from '/@/router/types';

import { LAYOUT } from '/@/router/constant';

const system: AppRouteModule = {
  path: '/system',
  name: 'System',
  component: LAYOUT,
  redirect: '/system/user',
  meta: {
    orderNo: 40,
    icon: 'ant-design:setting-outlined',
    title: '系统管理',
  },
  children: [
    {
      path: 'user',
      name: 'UserManagement',
      meta: {
        icon: 'ant-design:user-outlined',
        title: '用户管理',
        ignoreKeepAlive: false,
      },
      component: () => import('/@/views/system/user/index.vue'),
    },
    {
      path: 'monit',
      name: 'ServeMonit',
      meta: {
        title: '服务监控',
        icon: 'ant-design:database-outlined',
        ignoreKeepAlive: true,
      },
      component: () => import('/@/views/system/monitor/serve/index.vue'),
    },
    // {
    //   path: 'role',
    //   name: 'RoleManagement',
    //   meta: {
    //     title: '角色管理',
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('/@/views/system/role/index.vue'),
    // },
  ],
};

export default system;
