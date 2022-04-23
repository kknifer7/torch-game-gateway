import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/system/role/list',
  Page = '/system/role/page',
  Info = '/system/role/info',
  Add = '/system/role/add',
  Update = '/system/role/update',
  Detele = '/system/role/delete',
}

export const getRoleList = (params) => defHttp.get({ url: Api.List, params });

export const getRoleListByPage = (params) => defHttp.get({ url: Api.Page, params });

export const getRoleInfo = (params) => defHttp.get({ url: Api.Info, params });

export const addRole = (params) => defHttp.get({ url: Api.Add, params });

export const updateRole = (params) => defHttp.get({ url: Api.Update, params });

export const deleteRole = (params) => defHttp.get({ url: Api.Detele, params });
