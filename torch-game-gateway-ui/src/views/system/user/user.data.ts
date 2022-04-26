import { Space, Tag } from 'ant-design-vue';
import { h } from 'vue';
import { getRouteList } from '/@/api/route';
import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { formatToDateTime } from '/@/utils/dateUtil';

export const columns: BasicColumn[] = [
  {
    title: '用户名',
    dataIndex: 'username',
    width: 120,
  },
  {
    title: '路由',
    dataIndex: 'routes',
    customRender: ({ record }) => {
      const routes = (record.routes as any[]).map((r) => r.name);
      return h(Space, {}, () =>
        routes.map((r) => {
          return h(Tag, { color: 'green' }, () => r);
        }),
      );
    },
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
    field: 'routes',
    label: '路由',
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
    label: '路由',
    field: 'routes',
    component: 'ApiSelect',
    componentProps: {
      api: getRouteList,
      labelField: 'name',
      valueField: 'id',
      mode: 'multiple',
      'max-tag-count': 4,
    },
    colProps: { span: 24 },
  },
];
