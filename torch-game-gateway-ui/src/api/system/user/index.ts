import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/user/list',
  Info = '/user/info',
  Add = '/user/add',
  Update = '/user/update',
  Detele = '/user/delete',
  Limit = '/user/limit',
}

export const getUserList = (params?) => defHttp.get({ url: Api.List, params });

export const getUserInfo = (params) => defHttp.get({ url: Api.Info, params });

export const addUser = (params) =>
  defHttp.post(
    { url: Api.Add, params },
    {
      successMsg: '添加成功',
    },
  );

export const updateUser = (params) =>
  defHttp.post(
    { url: Api.Update, params },
    {
      successMsg: '更新成功',
    },
  );

export const deleteUser = (data) =>
  defHttp.post(
    { url: Api.Detele, data },
    {
      successMsg: '删除成功',
    },
  );

export const userLimit = (data) =>
  defHttp.post(
    { url: Api.Limit, data },
    {
      successMsg: '设置成功',
    },
  );
