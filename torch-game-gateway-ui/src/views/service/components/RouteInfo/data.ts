import { Tag } from 'ant-design-vue';
import { h } from 'vue';
import { BasicColumn } from '/@/components/Table/src/types/table';

export const columns: BasicColumn[] = [
  // {
  //   title: 'ID',
  //   dataIndex: 'id',
  //   fixed: 'left',
  //   width: 60,
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
    title: 'url',
    dataIndex: 'url',
    width: 150,
  },
  {
    title: 'extra',
    dataIndex: 'extra',
    width: 150,
  },
  {
    title: '状态',
    dataIndex: 'status',
    width: 80,
    customRender: ({ record }) => {
      const status = record.status;
      const enable = ~~status === 1;
      const color = enable ? 'green' : 'red';
      const text = enable ? '启用' : '停用';
      return h(Tag, { color: color }, () => text);
    },
  },
  {
    title: '备注',
    dataIndex: 'desc',
    width: 150,
    sorter: true,
  },
];
