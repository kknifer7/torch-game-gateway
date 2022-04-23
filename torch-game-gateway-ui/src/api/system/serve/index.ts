import { defHttp } from '/@/utils/http/axios';

enum Api {
  Stat = '/system/serve/stat',
}

export const getServeStat = () => defHttp.get<API.ServeStat>({ url: Api.Stat });
