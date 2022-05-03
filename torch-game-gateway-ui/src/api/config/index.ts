import { defHttp } from '/@/utils/http/axios';

enum Api {
  List = '/config/list',
  Add = '/config/add',
  Update = '/config/update',
  Detele = '/config/delete',
}

export const getConfigList = (params) => defHttp.get({ url: Api.List, params });

export const addConfig = (params) =>
  defHttp.post(
    { url: Api.Add, params },
    {
      successMsg: '添加成功',
    },
  );

export const updateConfig = (params) =>
  defHttp.post(
    { url: Api.Update, params },
    {
      successMsg: '更新成功',
    },
  );

export const deleteConfig = (data) =>
  defHttp.post(
    { url: Api.Detele, data },
    {
      successMsg: '删除成功',
    },
  );
