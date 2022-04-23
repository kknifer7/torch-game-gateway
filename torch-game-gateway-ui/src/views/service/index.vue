<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate"> 新增服务 </a-button>
      </template>
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'clarity:info-standard-line',
              tooltip: '查看服务详情',
              onClick: handleView.bind(null, record),
            },
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
  </div>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';

  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { getServiceList } from '/@/api/service';

  import { useModal } from '/@/components/Modal';
  import ServiceModal from './ServiceModal.vue';
  import { useGo } from '/@/hooks/web/usePage';

  import { columns, searchFormSchema } from './service.data';

  export default defineComponent({
    name: 'ServiceSearch',
    components: { BasicTable, ServiceModal, TableAction },
    setup() {
      const go = useGo();
      const [registerModal, { openModal }] = useModal();
      const [registerTable, { reload }] = useTable({
        title: '服务列表',
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

      function handleDelete(record: Recordable) {
        console.log(record);
      }

      function handleSuccess() {
        reload();
      }

      function handleView(record: Recordable) {
        go('/service/detail/' + record.id);
      }

      return {
        registerTable,
        registerModal,
        handleCreate,
        handleEdit,
        handleDelete,
        handleSuccess,
        handleView,
      };
    },
  });
</script>
