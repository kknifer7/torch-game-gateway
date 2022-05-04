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
      const color = type === '未知' ? 'yellow' : 'blue';
      return h(Tag, { color }, () => type);
    },
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
