import { Space, Tag } from 'ant-design-vue';
import { h } from 'vue';
import { getServiceList } from '/@/api/service/index';
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
    title: '服务',
    dataIndex: 'authorities',
    customRender: ({ record }) => {
      const authorities = record.authorities;
      return h(Space, {}, () =>
        authorities.map((r) => {
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
    field: 'account',
    label: '用户名',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'authorities',
    label: '服务',
    component: 'Input',
    colProps: { span: 8 },
  },
];

export const accountFormSchema: FormSchema[] = [
  {
    field: 'account',
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
    required: true,
    ifShow: false,
  },
  {
    label: '服务',
    field: 'authorities',
    component: 'ApiSelect',
    componentProps: {
      api: getServiceList,
      labelField: 'name',
      valueField: 'id',
      mode: 'multiple',
      'max-tag-count': 4,
    },
    colProps: { span: 24 },
    required: true,
  },
];
