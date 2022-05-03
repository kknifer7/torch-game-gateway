<template>
  <PageWrapper dense contentFullHeight fixedHeight contentClass="flex">
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate"> 新增路由 </a-button>
        <a-button type="primary" @click="handleSync"> 同步路由 </a-button>
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:note-edit-line',
              tooltip: '编辑路由',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
    <RouteModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts" setup name="RouteList">
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';

  import { deleteRoute, getRouteList, routeSync } from '/@/api/route';

  import RouteModal from './RouteModal.vue';
  import { useModal } from '/@/components/Modal';

  import { columns, searchFormSchema } from './route.data';

  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload, deleteTableDataRecord }] = useTable({
    title: '路由列表',
    api: getRouteList,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: [],
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: false,
    actionColumn: {
      width: 120,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
      fixed: undefined,
    },
  });

  function handleCreate() {
    openModal(true, {
      isUpdate: false,
    });
  }

  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
    });
  }

  function handleDelete(record: Recordable) {
    deleteRoute({ id: record.id });
    deleteTableDataRecord(record.id);
  }

  function handleSuccess() {
    reload();
  }

  function handleSync() {
    routeSync();
  }
</script>
