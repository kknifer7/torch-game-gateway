import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/service/list',
  ServiceLogList = '/service/log/list',
  Page = '/service/page',
  Info = '/service/info',
  Add = '/service/add',
  Update = '/service/update',
  Detele = '/service/delete',
}

export const getServiceList = (params) => defHttp.get({ url: Api.List, params });

export const getServiceLogList = (params) => defHttp.get({ url: Api.ServiceLogList, params });

export const getServiceListByPage = (params) => defHttp.get({ url: Api.Page, params });

export const getServiceInfo = (id) => defHttp.post({ url: Api.Info, params: { id } });

export const addService = (params) => defHttp.get({ url: Api.Add, params });

export const updateService = (params) => defHttp.get({ url: Api.Update, params });

export const deleteService = (params) => defHttp.get({ url: Api.Detele, params });
