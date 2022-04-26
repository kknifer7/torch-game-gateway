import { FormSchema } from '/@/components/Form';
const colProps = {
  span: 24,
};

export const schemas: FormSchema[] = [
  {
    label: '请求资源',
    field: 'url',
    component: 'Input',
    colProps,
    required: true,
  },
  {
    label: '请求方法',
    field: 'method',
    defaultValue: 'get',
    component: 'Select',
    componentProps: {
      options: [
        {
          label: 'GET',
          value: 'get',
        },
        {
          label: 'POST',
          value: 'post',
        },
      ],
    },
    colProps: {
      span: 4,
    },
    required: true,
  },
  {
    field: 'param',
    label: '请求参数',
    component: 'InputTextArea',
    colProps,
    componentProps: {
      rows: 4,
    },
  },
  {
    field: 'headers',
    component: 'InputTextArea',
    label: '协议头',
    colProps,
    componentProps: {
      rows: 4,
    },
  },
];
