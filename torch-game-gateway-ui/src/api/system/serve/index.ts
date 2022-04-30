import { defHttp } from '/@/utils/http/axios';

enum Api {
  Stat = '/serve/stat',
}

export const getServeStat = () => defHttp.get<API.ServeStat>({ url: Api.Stat });
