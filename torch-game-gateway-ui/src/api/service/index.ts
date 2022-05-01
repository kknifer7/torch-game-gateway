import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/service/list',
  LogList = '/service/log/list',
  RouteList = '/service/route/list',
  Page = '/service/page',
  Info = '/service/info',
  Add = '/service/add',
  Update = '/service/update',
  Detele = '/service/delete',
}

export const getServiceList = (params) => defHttp.get({ url: Api.List, params });

export const getServiceLogList = (params) => defHttp.get({ url: Api.LogList, params });

export const getServiceListByPage = (params) => defHttp.get({ url: Api.RouteList, params });

export const getRouteListByPage = (params) => defHttp.get({ url: Api.RouteList, params });

export const getServiceInfo = (name) => defHttp.get({ url: Api.Info, params: { name } });

export const addService = (params) =>
  defHttp.post(
    { url: Api.Add, params },
    {
      successMsg: '添加成功',
    },
  );

export const updateService = (params) =>
  defHttp.post(
    { url: Api.Update, params },
    {
      successMsg: '更新成功',
    },
  );

export const deleteService = (data) =>
  defHttp.post(
    { url: Api.Detele, data },
    {
      successMsg: '删除成功',
    },
  );
