import { MockMethod } from 'vite-plugin-mock';
import { resultPageSuccess, resultSuccess } from '../../_util';

const roleList = (() => {
  const result: any[] = [];
  result.push(
    {
      id: 0,
      name: '管理员',
      value: 'admin',
      createAt: '@datetime',
    },
    {
      id: 1,
      name: '用户',
      value: 'user',
      createAt: '@datetime',
    },
  );
  return result;
})();

export default [
  {
    url: '/api/role/list',
    timeout: 100,
    method: 'get',
    response: () => {
      return resultSuccess(roleList);
    },
  },
  {
    url: '/api/role/page',
    timeout: 100,
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query;
      return resultPageSuccess(page, pageSize, roleList);
    },
  },
] as MockMethod[];
