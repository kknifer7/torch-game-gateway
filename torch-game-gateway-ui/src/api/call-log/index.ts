import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/call-log/list',
}

export const getCallLogList = (params) => defHttp.get({ url: Api.List, params });
