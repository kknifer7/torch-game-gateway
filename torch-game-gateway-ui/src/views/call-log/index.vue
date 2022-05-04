<template>
  <BasicTable @register="registerTable" />
</template>
<script lang="ts" setup name="CallLog">
  import { BasicTable, useTable } from '/@/components/Table';
  import { getCallLogList } from '/@/api/call-log';

  import { columns } from './call-log.data';

  const [registerTable, {}] = useTable({
    title: '调用日志',
    rowKey: 'id',
    api: getCallLogList,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: [],
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
  });
</script>
