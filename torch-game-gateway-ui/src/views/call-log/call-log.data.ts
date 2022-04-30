import { Tag } from 'ant-design-vue';
import { h } from 'vue';
import { getServiceList } from '/@/api/service';
import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { formatToDateTime } from '/@/utils/dateUtil';

export const columns: BasicColumn[] = [
  // {
  //   title: 'ID',
  //   dataIndex: 'id',
  //   width: 60,
  // },
  {
    title: '网关ID',
    dataIndex: 'gatewayId',
    defaultHidden: true,
    width: 60,
  },
  {
    title: '网关主机端口',
    dataIndex: 'gatewayHost',
    width: 100,
    customRender: ({ record }) => {
      const text = record.gatewayHost + ':' + record.gatewayPort;
      return h('span', text);
    },
  },
  {
    title: '网关端口',
    dataIndex: 'gatewayPort',
    ifShow: false,
    width: 60,
  },
  {
    title: '网关URI',
    dataIndex: 'gatewayUri',
    width: 120,
  },
  {
    title: '路由ID',
    dataIndex: 'routeId',
    defaultHidden: true,
    width: 60,
  },
  {
    title: '路由主机端口',
    dataIndex: 'routeHost',
    customRender: ({ record }) => {
      const text = record.routeHost + ':' + record.routePort;
      return h('span', text);
    },
    width: 120,
  },
  {
    title: '路由端口',
    dataIndex: 'routePort',
    ifShow: false,
    width: 60,
  },
  {
    title: '路由URI',
    dataIndex: 'routeUrl',
    width: 120,
  },
  {
    title: '服务名',
    dataIndex: 'serviceName',
    width: 100,
  },
  {
    title: '调用结果',
    dataIndex: 'success',
    width: 80,
    customRender: ({ record }) => {
      const success = record.success;
      const enable = success === true;
      const color = enable ? 'green' : 'red';
      const text = enable ? '成功' : '失败';
      return h(Tag, { color: color }, () => text);
    },
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
    width: 150,
    dataIndex: 'timestamp',
    format: (text: string) => {
      return formatToDateTime(text);
    },
  },
];
