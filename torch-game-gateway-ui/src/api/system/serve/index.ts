import { defHttp } from '/@/utils/http/axios';

enum Api {
  Stat = '/serve/stat',
  LimitList = '/serve/limit/list',
  RouteList = '/serve/route/list',
  DeleteLimit = '/serve/limit/delete',
  Analysis = '/serve/analysis',
}

export const getServeStat = () => defHttp.get<API.ServeStat>({ url: Api.Stat });

export const getLimitList = (data?) => defHttp.post({ url: Api.LimitList, data });

export const getRouteList = (data?) => defHttp.post({ url: Api.RouteList, data });

export const deleteLimit = (data) => defHttp.post({ url: Api.DeleteLimit, data });

export const getAnalysis = () => defHttp.get({ url: Api.Analysis });
