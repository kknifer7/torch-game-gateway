import { Tag } from 'ant-design-vue';
import { h } from 'vue';
import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
// import { formatToDateTime } from '/@/utils/dateUtil';

export const columns: BasicColumn[] = [
  {
    title: 'ID',
    dataIndex: 'id',
    width: 60,
  },
  {
    title: '服务名',
    dataIndex: 'name',
    width: 100,
  },
  // {
  //   title: '应用',
  //   dataIndex: 'app',
  //   width: 100,
  // },
  {
    title: '主机端口',
    dataIndex: 'host',
    width: 150,
    customRender: ({ record }) => {
      const text = record.host + ':' + record.port;
      return h('span', text);
    },
  },
  {
    title: '路径',
    dataIndex: 'url',
  },
  {
    title: '协议类型',
    dataIndex: 'protocol',
    width: 80,
  },
  {
    title: '描述',
    dataIndex: 'desc',
    width: 120,
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: 80,
    customRender: ({ record }) => {
      const status = record.status;
      const enable = status === true;
      const color = enable ? 'green' : 'red';
      const text = enable ? '启用' : '停用';
      return h(Tag, { color: color }, () => text);
    },
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'name',
    label: '服务名',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    field: 'url',
    label: '路径',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    field: 'protocol',
    label: '协议',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'Select',
    componentProps: {
      options: [
        { label: '启用', value: true },
        { label: '停用', value: false },
      ],
    },
    colProps: { span: 6 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'name',
    label: '服务名',
    required: true,
    component: 'Input',
  },
  {
    field: 'host',
    label: '主机',
    required: true,
    component: 'Input',
    colProps: { span: 12 },
  },
  {
    field: 'port',
    label: '端口',
    required: true,
    component: 'Input',
    colProps: { span: 12 },
  },
  {
    field: 'protocol',
    label: '协议',
    required: true,
    component: 'Input',
  },
  {
    field: 'url',
    label: '路径',
    required: true,
    component: 'Input',
    colProps: { span: 24 },
  },
  {
    field: 'status',
    label: '状态',
    component: 'RadioButtonGroup',
    defaultValue: true,
    componentProps: {
      options: [
        { label: '启用', value: true },
        { label: '停用', value: false },
      ],
    },
  },
  {
    label: '描述',
    field: 'desc',
    component: 'InputTextArea',
    colProps: { span: 24 },
  },
];
