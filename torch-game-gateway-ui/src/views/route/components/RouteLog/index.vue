<template>
  <BasicTable @register="registerTable" />
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { BasicTable, ColumnChangeParam, useTable } from '/@/components/Table';
  import { columns } from './data';

  import { getRouteLogList } from '/@/api/route';

  export default defineComponent({
    components: { BasicTable },
    setup() {
      function onChange() {
        console.log('onChange', arguments);
      }

      const [registerTable, { setLoading }] = useTable({
        canResize: true,
        title: '调用日志',
        showIndexColumn: false,
        api: getRouteLogList,
        columns: columns,
        rowKey: 'id',
        showTableSetting: true,
        onChange,
        onColumnsChange: (data: ColumnChangeParam[]) => {
          console.log('ColumnsChanged', data);
        },
      });

      function changeLoading() {
        setLoading(true);
        setTimeout(() => {
          setLoading(false);
        }, 1000);
      }

      return {
        registerTable,
        changeLoading,

        onChange,
      };
    },
  });
</script>
