import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/route/list',
  LogList = '/route/log/list',
  Page = '/route/page',
  Info = '/route/info',
  Add = '/route/add',
  Update = '/route/update',
  Detele = '/route/delete',
  Sync = '/route/sync',
}

export const getRouteList = (params) => defHttp.get({ url: Api.List, params });

export const getRouteLogList = (params) => defHttp.get({ url: Api.LogList, params });

export const getRouteInfo = (id) => defHttp.post({ url: Api.Info, params: { id } });

export const addRoute = (params) =>
  defHttp.post(
    { url: Api.Add, params },
    {
      successMsg: '添加成功',
    },
  );

export const updateRoute = (params) =>
  defHttp.post(
    { url: Api.Update, params },
    {
      successMsg: '更新成功',
    },
  );

export const deleteRoute = (data) =>
  defHttp.post(
    { url: Api.Detele, data },
    {
      successMsg: '删除成功',
    },
  );

export const routeSync = () =>
  defHttp.get(
    { url: Api.Sync },
    {
      successMsg: '同步成功',
    },
  );
