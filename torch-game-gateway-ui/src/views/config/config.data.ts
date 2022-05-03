import { Tag } from 'ant-design-vue';
import { h } from 'vue';
import { getServiceList } from '/@/api/service';
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
    title: '配置名',
    dataIndex: 'name',
    width: 120,
  },
  {
    title: '配置键',
    dataIndex: 'kee',
    width: 120,
  },
  {
    title: '配置值',
    dataIndex: 'val',
    width: 100,
  },
  {
    title: '描述',
    dataIndex: 'remark',
    width: 120,
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'name',
    label: '属性名',
    required: true,
    component: 'Input',
  },
  {
    field: 'kee',
    label: '属性键',
    required: true,
    component: 'Input',
  },
  {
    field: 'val',
    label: '属性值',
    required: true,
    component: 'Input',
  },
  {
    label: '描述',
    field: 'remark',
    component: 'InputTextArea',
    colProps: { span: 24 },
  },
];
