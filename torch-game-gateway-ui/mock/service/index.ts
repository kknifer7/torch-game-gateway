import { MockMethod } from 'vite-plugin-mock';
import * as Mock from 'mockjs';
import { resultPageSuccess, resultSuccess } from '../_util';

const serviceList = (() => {
  const result: any[] = [
    {
      id: '1',
      name: 'service-99',
      desc: '中国移动-用户协议获取服务',
      protocol: 'http',
      host: '@domain',
      port: 80,
      url: 'https://www.10086.cn/web_notice/protocol/5003337_673_1937.json',
      status: '@boolean',
      creationDatetime: '@datetime',
      updateDatetime: '@datetime',
      extra: 111,
    },
    {
      id: '2',
      name: 'service-01',
      desc: '本地测试服务-http',
      protocol: 'http',
      host: 'localhost',
      port: 8080,
      url: 'http://localhost:8080/val',
      status: '@boolean',
      creationDatetime: '@datetime',
      updateDatetime: '@datetime',
      extra: 111,
    },
    {
      id: '3',
      name: 'service-02',
      desc: '本地测试服务-rpc',
      protocol: 'rpc',
      host: 'localhost',
      port: 8082,
      url: null,
      status: '@boolean',
      creationDatetime: '@datetime',
      updateDatetime: '@datetime',
      extra: 111,
    },
  ];
  for (let index = 4; index < 10; index++) {
    result.push({
      id: `${index}`,
      name: 'service-@integer(0,99)',
      desc: '@cword(0,10)',
      protocol: 'http',
      host: 'localhost',
      port: '@integer(1000,9999)',
      url: '@url',
      status: '@boolean',
      creationDatetime: '@datetime',
      updateDatetime: '@datetime',
      extra: 111,
    });
  }

  return result;
})();

const serviceInfo = (() => {
  const result: any[] = [];
  for (let index = 0; index < 10; index++) {
    result.push({
      id: `${index}`,
      name: '路由@integer(0,9)',
      host: '127.0.0.1',
      port: '@integer(1000,9999)',
      url: '/order',
      createAt: '@datetime',
    });
  }

  return {
    id: `0`,
    name: '服务@integer(0,9)',
    version: '1.0.@integer(0,9)',
    app: '@cword(2,5)',
    registrySource: '应用级',
    createAt: '@datetime',
    routerList: result,
  };
})();

const routerList = (() => {
  const result: any[] = [];

  return result;
})();

const serviceLogList = (() => {
  const result: any[] = [];
  for (let index = 0; index < 10; index++) {
    result.push({
      id: `${index}`,
      username: '@first', // 操作者
      method: Mock.mock({
        'array|1': ['GET', 'POST'],
      }).array, // 请求方法
      param: '{}', // 请求参数
      success: '@boolean', // 调用结果
      consumeTime: '@integer(0,1000)', // 耗时
      timestamp: '@datetime', // 调用时间
    });
  }

  return result;
})();

export default [
  {
    url: '/basic-api/service/page',
    timeout: 100,
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query;
      return resultPageSuccess(page, pageSize, serviceList);
    },
  },
  {
    url: '/basic-api/service/list',
    timeout: 100,
    method: 'get',
    response: ({}) => {
      return resultSuccess(serviceList);
    },
  },
  {
    url: '/basic-api/service/info',
    timeout: 100,
    method: 'post',
    response: ({}) => {
      return resultSuccess(serviceInfo);
    },
  },
  {
    url: '/basic-api/service/log/list',
    timeout: 100,
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query;
      return resultPageSuccess(page, pageSize, serviceLogList);
    },
  },
  {
    url: '/basic-api/service/router/list',
    timeout: 100,
    method: 'get',
    response: ({ query }) => {
      const { page = 1, pageSize = 10 } = query;
      return resultPageSuccess(page, pageSize, routerList);
    },
  },
] as MockMethod[];
