import { getServiceList } from '/@/api/service';
import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { formatToDateTime } from '/@/utils/dateUtil';

export const columns: BasicColumn[] = [
  {
    title: '限流器类型（用户或路由）',
    dataIndex: 'type',
    width: 120,
  },
  {
    title: '服务',
    dataIndex: 'services',
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: 120,
  },
  {
    title: '创建时间',
    dataIndex: 'createAt',
    width: 150,
    format: (text) => {
      return formatToDateTime(text);
    },
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'username',
    label: '用户名',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'services',
    label: '服务',
    component: 'Input',
    colProps: { span: 8 },
  },
];

export const userFormSchema: FormSchema[] = [
  {
    field: 'username',
    label: '用户名',
    component: 'Input',
    rules: [
      {
        required: true,
        message: '请输入用户名',
      },
    ],
  },
  {
    field: 'pwd',
    label: '密码',
    component: 'InputPassword',
    componentProps: {
      placeholder: '无需修改请留空',
    },
  },
  {
    label: '服务',
    field: 'services',
    component: 'ApiSelect',
    componentProps: {
      api: getServiceList,
      labelField: 'name',
      valueField: 'id',
      mode: 'multiple',
      'max-tag-count': 4,
    },
    colProps: { span: 24 },
  },
];
