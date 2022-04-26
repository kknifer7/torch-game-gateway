import { MockMethod } from 'vite-plugin-mock';
import { resultPageSuccess } from '../../_util';

const userList = (() => {
  const result: any[] = [];
  result.push(
    {
      id: `${0}`,
      username: '@first',
      // roleNames: ['管理员'],
      authorities: ['路由@integer(0,9)', '路由@integer(0,9)'],
      createAt: '@datetime',
    },
    {
      id: `${1}`,
      username: '@first',
      // roleNames: ['用户'],
      authorities: [
        '路由@integer(0,9)',
        '路由@integer(0,9)',
        '路由@integer(0,9)',
        '路由@integer(0,9)',
        '路由@integer(0,9)',
        '路由@integer(0,9)',
        '路由@integer(0,9)',
        '路由@integer(0,9)',
      ],
      createAt: '@datetime',
    },
  );
  return result;
})();

export default [
  {
    url: '/api/user/list',
    timeout: 100,
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query;
      return resultPageSuccess(page, pageSize, userList);
    },
  },
] as MockMethod[];
