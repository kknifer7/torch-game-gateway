<template>
  <PageWrapper dense contentFullHeight fixedHeight contentClass="flex">
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate"> 新增服务 </a-button>
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            // {
            //   icon: 'clarity:info-standard-line',
            //   tooltip: '查看路由详情',
            //   onClick: handleView.bind(null, record),
            // },
            {
              icon: 'clarity:note-edit-line',
              tooltip: '编辑服务',
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
    <ServiceModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts" setup name="ServiceList">
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';

  import ServiceModal from './ServiceModal.vue';
  import { useModal } from '/@/components/Modal';
  import { useGo } from '/@/hooks/web/usePage';

  import { deleteService, getServiceList } from '/@/api/service';
  import { columns, searchFormSchema } from './service.data';

  const go = useGo();
  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload, deleteTableDataRecord }] = useTable({
    title: '服务列表',
    rowKey: 'id',
    api: getServiceList,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
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
    await deleteService({ id: record.id });
    deleteTableDataRecord(record.id);
  }

  function handleSuccess() {
    reload();
  }

  function handleView(record: Recordable) {
    go('/service/detail/' + record.name);
  }
</script>
