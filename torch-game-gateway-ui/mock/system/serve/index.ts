import { MockMethod } from 'vite-plugin-mock';
import { resultSuccess } from '../../_util';

const serveStat = (() => {
  const result = {
    runtime: {
      npmVersion: '8.5.5',
      nodeVersion: '16.13.0',
      os: 'Windows',
      arch: 'x64',
    },
    cpu: {
      manufacturer: 'AMD',
      brand: 'Ryzen 9 5900X 12-Core Processor',
      physicalCores: 12,
      model: '33',
      speed: 3.7,
      rawCurrentLoad: 1608942702,
      rawCurrentLoadIdle: 6476801613,
      coresLoad: [
        {
          rawLoad: '@integer(57415843,107415843)',
          rawLoadIdle: 229354265,
        },
      ],
    },
    disk: {
      size: 10485760000,
      used: '@integer(4485760000,9485760000)',
      available: 5000000000,
    },
    memory: {
      total: 8589934592,
      available: '@integer(2000000000,8000000000)',
    },
  };

  return result;
})();

export default [
  {
    url: '/basic-api/system/serve/stat',
    timeout: 100,
    method: 'get',
    response: ({}) => {
      return resultSuccess(serveStat);
    },
  },
] as MockMethod[];
