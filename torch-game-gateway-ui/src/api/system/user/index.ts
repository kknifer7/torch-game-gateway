import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/user/list',
  Info = '/user/info',
  Add = '/user/add',
  Update = '/user/update',
  Detele = '/user/delete',
}

export const getUserList = (params) => defHttp.get({ url: Api.List, params });

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

export const deleteUser = (ids) =>
  defHttp.post(
    { url: Api.Detele, params: ids },
    {
      successMsg: '删除成功',
    },
  );