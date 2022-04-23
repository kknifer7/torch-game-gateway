import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/system/account/list',
  Info = '/system/account/info',
  Add = '/system/account/add',
  Update = '/system/account/update',
  Detele = '/system/account/delete',
}

export const getAccountList = (params) => defHttp.get({ url: Api.List, params });

export const getAccountInfo = (params) => defHttp.get({ url: Api.Info, params });

export const addAccount = (params) => defHttp.get({ url: Api.Add, params });

export const updateAccount = (params) => defHttp.get({ url: Api.Update, params });

export const deleteAccount = (params) => defHttp.get({ url: Api.Detele, params });
