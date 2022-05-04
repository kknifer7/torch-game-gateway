<template>
  <BasicTable @register="registerTable">
    <template #toolbar>
      <a-button type="primary" @click="handleCreate"> 新增路由 </a-button>
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
</template>
<script lang="ts" setup name="RouteInfo">
  import { onMounted, ref } from 'vue';
  import { BasicTable, useTable } from '/@/components/Table';
  import { columns } from './data';
  import { deleteRoute } from '/@/api/route';
  import { useModal } from '/@/components/Modal';
  import RouteModal from '../../../route/RouteModal.vue';
  import { getServiceInfo } from '/@/api/service';

  const props = defineProps({
    serviceName: String,
  });
  console.log(props.serviceName);

  const routeList = ref([]);
  onMounted(async () => {
    const serviceData = await getServiceInfo(props.serviceName);
    debugger;
    if (serviceData.routes?.length > 0) {
      routeList.value = serviceData.routes;
    }
  });

  const [registerModal, { openModal }] = useModal();
  const [registerTable, { setLoading, deleteTableDataRecord }] = useTable({
    canResize: true,
    title: '路由列表',
    showIndexColumn: false,
    dataSource: routeList.value,
    columns: columns,
    rowKey: 'id',
    showTableSetting: true,
    rowSelection: {
      type: 'checkbox',
    },
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
    await deleteRoute([record.id]);
    deleteTableDataRecord(record.id);
  }

  function handleSuccess() {}

  function changeLoading() {
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
    }, 1000);
  }
</script>
