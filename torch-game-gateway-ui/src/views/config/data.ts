import { FormSchema } from '/@/components/Form';

export const rateLimitSchemas: FormSchema[] = [
  {
    field: 'limit',
    component: 'Input',
    label: '限流',
    colProps: {
      span: 24,
    },
    componentProps: {
      onChange: (e: any) => {
        console.log(e);
      },
    },
  },
];

export const JWtSchemas: FormSchema[] = [
  {
    field: 'secretKey',
    component: 'Input',
    label: 'JWt',
    colProps: {
      span: 24,
    },
    componentProps: {
      onChange: (e: any) => {
        console.log(e);
      },
    },
  },
];

export const cacheSchemas: FormSchema[] = [
  {
    field: 'cache',
    component: 'Input',
    label: '缓存',
    colProps: {
      span: 24,
    },
    componentProps: {
      onChange: (e: any) => {
        console.log(e);
      },
    },
  },
];
