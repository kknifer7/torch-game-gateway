<template>
  <PageWrapper dense contentFullHeight fixedHeight contentClass="flex">
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate"> 新增配置 </a-button>
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:note-edit-line',
              tooltip: '编辑配置',
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
    <ConfigModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts" setup name="ConfigList">
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';

  import { deleteConfig, getConfigList } from '/@/api/config';

  import ConfigModal from './ConfigModal.vue';
  import { useModal } from '/@/components/Modal';

  import { columns } from './config.data';

  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload, deleteTableDataRecord }] = useTable({
    title: '配置列表',
    rowKey: 'id',
    api: getConfigList,
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

  async function handleDelete(record: Recordable) {
    await deleteConfig({ id: record.id });
    deleteTableDataRecord(record.id);
  }

  function handleSuccess() {
    reload();
  }
</script>
