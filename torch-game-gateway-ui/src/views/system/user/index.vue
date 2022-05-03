<template>
  <PageWrapper dense contentFullHeight fixedHeight contentClass="flex">
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate">新增账号</a-button>
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:lock-outlined',
              tooltip: '限流',
              onClick: handleLimit.bind(null, record),
            },
            {
              icon: 'clarity:note-edit-line',
              tooltip: '编辑用户资料',
              onClick: handleEdit.bind(null, record),
            },
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              tooltip: '删除此账号',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
    <UserModal @register="registerModal" @success="handleSuccess" />
    <LimitModal @register="registerLimitModal" />
  </PageWrapper>
</template>
<script lang="ts" setup name="UserManagement">
  import { defineComponent, reactive } from 'vue';

  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { deleteUser, getUserList } from '/@/api/system/user';
  import { PageWrapper } from '/@/components/Page';

  import { useModal } from '/@/components/Modal';
  import UserModal from './UserModal.vue';
  import LimitModal from './LimitModal.vue';

  import { columns, searchFormSchema } from './user.data';

  const [registerModal, { openModal }] = useModal();
  const [registerLimitModal, { openModal: openLimitModal }] = useModal();

  const searchInfo = reactive<Recordable>({});
  const [registerTable, { reload, deleteTableDataRecord }] = useTable({
    title: '账号列表',
    api: getUserList,
    rowKey: 'id',
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: [],
      autoSubmitOnEnter: true,
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    handleSearchInfoFn(info) {
      console.log('handleSearchInfoFn', info);
      return info;
    },
    actionColumn: {
      width: 120,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
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
    deleteUser({ id: record.id });
    deleteTableDataRecord(record.id);
  }

  function handleSuccess({}) {
    reload();
  }

  function handleLimit(record: Recordable) {
    openLimitModal(true, {
      record,
    });
  }
</script>
