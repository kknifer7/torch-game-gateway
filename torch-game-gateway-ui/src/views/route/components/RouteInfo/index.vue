<template>
  <BasicTable @register="registerTable" />
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { BasicTable, ColumnChangeParam, useTable } from '/@/components/Table';
  import { columns } from './data';
  import { useMessage } from '/@/hooks/web/useMessage';
  // import { useRoute } from 'vue-router';
  import { getRouteLogList } from '/@/api/route';

  export default defineComponent({
    components: { BasicTable },
    props: {
      serviceData: Object,
    },
    setup(props) {
      console.log('props', props);

      const { createMessage } = useMessage();
      function onChange() {
        console.log('onChange', arguments);
      }

      const [
        registerTable,
        {
          setLoading,
          getColumns,
          getDataSource,
          getRawDataSource,
          reload,
          getPaginationRef,
          setPagination,
          getSelectRows,
          getSelectRowKeys,
          setSelectedRowKeys,
          clearSelectedRowKeys,
        },
      ] = useTable({
        canResize: true,
        title: '列表',
        showIndexColumn: false,
        api: getRouteLogList,
        columns: columns,
        rowKey: 'id',
        showTableSetting: true,
        onChange,
        rowSelection: {
          type: 'checkbox',
        },
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

      function getColumn() {
        createMessage.info('请在控制台查看！');
        console.log(getColumns());
      }

      function getTableData() {
        createMessage.info('请在控制台查看！');
        console.log(getDataSource());
      }

      function getTableRawData() {
        createMessage.info('请在控制台查看！');
        console.log(getRawDataSource());
      }

      function getPagination() {
        createMessage.info('请在控制台查看！');
        console.log(getPaginationRef());
      }

      function setPaginationInfo() {
        setPagination({
          current: 2,
        });
        reload();
      }
      function getSelectRowList() {
        createMessage.info('请在控制台查看！');
        console.log(getSelectRows());
      }
      function getSelectRowKeyList() {
        createMessage.info('请在控制台查看！');
        console.log(getSelectRowKeys());
      }
      function setSelectedRowKeyList() {
        setSelectedRowKeys(['0', '1', '2']);
      }
      function clearSelect() {
        clearSelectedRowKeys();
      }

      return {
        registerTable,
        changeLoading,
        getColumn,
        getTableData,
        getTableRawData,
        getPagination,
        setPaginationInfo,
        getSelectRowList,
        getSelectRowKeyList,
        setSelectedRowKeyList,
        clearSelect,
        onChange,
      };
    },
  });
</script>
