import { Tag } from 'ant-design-vue';
import { h } from 'vue';
import { BasicColumn } from '/@/components/Table/src/types/table';

export const columns: BasicColumn[] = [
  {
    title: 'ID',
    dataIndex: 'id',
    fixed: 'left',
    width: 60,
  },
  {
    title: '操作者',
    dataIndex: 'username',
    width: 100,
  },
  {
    title: '方法',
    dataIndex: 'method',
    width: 150,
  },
  {
    title: '协议头',
    dataIndex: 'headers',
    width: 150,
  },
  {
    title: '参数',
    dataIndex: 'param',
    width: 150,
  },

  {
    title: '耗时',
    dataIndex: 'consumeTime',
    width: 80,
    customRender: ({ record }) => {
      return h(Tag, {}, () => `${record.consumeTime}ms`);
    },
  },
  {
    title: '调用时间',
    dataIndex: 'timestamp',
    width: 150,
  },
];
