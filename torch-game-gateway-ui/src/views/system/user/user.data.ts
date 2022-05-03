import { Space, Tag } from 'ant-design-vue';
import { h } from 'vue';
import { getServiceList } from '/@/api/service';
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
    dataIndex: 'services',
    customRender: ({ record }) => {
      const services = (record.services as any[]).map((r) => r.name);
      return h(Space, {}, () =>
        services.map((r) => {
          return h(Tag, { color: 'green' }, () => r);
        }),
      );
    },
  },
  // {
  //   title: '状态',
  //   dataIndex: 'status',
  //   width: 120,
  // },
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

export const limitFormSchema: FormSchema[] = [
  {
    field: 'userId',
    label: '用户ID',
    component: 'Input',
    show: false,
  },
  {
    field: 'limit',
    label: '每秒令牌数',
    component: 'Input',
  },
  {
    field: 'limitingTimeout',
    label: '超时时间',
    component: 'Input',
  },
  {
    field: 'limitingTimeUnit',
    label: '超时时间单位',
    component: 'Select',
    componentProps: {
      options: [
        { label: 'MILLISECONDS', value: 'MILLISECONDS' },
        { label: 'SECONDS', value: 'SECONDS' },
        { label: 'MINUTES', value: 'MINUTES' },
        { label: 'HOURS', value: 'HOURS' },
      ],
    },
    colProps: { span: 24 },
  },
];
