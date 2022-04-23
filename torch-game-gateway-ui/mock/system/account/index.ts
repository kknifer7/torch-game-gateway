import { MockMethod } from 'vite-plugin-mock';
import { resultPageSuccess } from '../../_util';

const accountList = (() => {
  const result: any[] = [];
  result.push(
    {
      id: `${0}`,
      username: '@first',
      // roleNames: ['管理员'],
      authorities: ['服务@integer(0,9)', '服务@integer(0,9)'],
      createAt: '@datetime',
    },
    {
      id: `${1}`,
      username: '@first',
      // roleNames: ['用户'],
      authorities: [
        '服务@integer(0,9)',
        '服务@integer(0,9)',
        '服务@integer(0,9)',
        '服务@integer(0,9)',
        '服务@integer(0,9)',
        '服务@integer(0,9)',
        '服务@integer(0,9)',
        '服务@integer(0,9)',
      ],
      createAt: '@datetime',
    },
  );
  return result;
})();

export default [
  {
    url: '/basic-api/system/account/list',
    timeout: 100,
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query;
      return resultPageSuccess(page, pageSize, accountList);
    },
  },
] as MockMethod[];
