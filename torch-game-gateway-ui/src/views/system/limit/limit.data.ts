import { Tag } from 'ant-design-vue';
import { h } from 'vue';
import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';

export const columns: BasicColumn[] = [
  {
    title: 'ID',
    dataIndex: 'id',
    width: 120,
  },
  {
    title: '限流器类型',
    dataIndex: 'type',
    width: 120,
    customRender: ({ record }) => {
      const type = record.type;
      const typeMap = {
        用户: 'blue',
        路由: 'cyan',
      };
      const color = typeMap[type] ?? 'yellow';
      return h(Tag, { color }, () => type);
    },
  },
  {
    title: '用户',
    dataIndex: 'username',
    width: 120,
  },
  {
    title: '服务名',
    dataIndex: 'serviceName',
    width: 120,
  },
  {
    title: '路由',
    dataIndex: 'url',
    width: 120,
  },
  {
    title: '每秒令牌数',
    dataIndex: 'limit',
    width: 120,
  },
  {
    title: '超时时间',
    dataIndex: 'limitingTimeout',
  },
  {
    title: '超时单位',
    dataIndex: 'limitingTimeUnit',
    width: 120,
  },
];

export const searchFormSchema: FormSchema[] = [];
