<template>
  <PageWrapper title="限流器管理" contentBackground @back="goBack">
    <BasicTable @register="registerTable">
      <template #action="{ record }">
        <TableAction
          :actions="[
            {
              icon: 'ant-design:delete-outlined',
              color: 'error',
              tooltip: '删除该限流器',
              disabled: record.type === '路由',
              popConfirm: {
                title: '是否确认删除',
                confirm: handleDelete.bind(null, record),
              },
            },
          ]"
        />
      </template>
    </BasicTable>
  </PageWrapper>
</template>
<script lang="ts" setup name="LimitManagement">
  import { ref } from 'vue';
  import { useRoute } from 'vue-router';
  import { columns } from './limit.data';
  import { getLimitList, getRouteList, deleteLimit } from '/@/api/system/serve';
  import { getUserList } from '/@/api/system/user';
  import { PageWrapper } from '/@/components/Page';
  import { BasicTable, TableAction, useTable } from '/@/components/Table';
  import { useGo } from '/@/hooks/web/usePage';

  const route = useRoute();
  const go = useGo();

  const domain = ref(route.params?.domain);

  const limitData = ref([]);

  const fetchData = async () => {
    const limitList = await getLimitList({ domain: domain.value });
    const userList = await getUserList();
    const routeList = await getRouteList({ domain: domain.value });

    const userIds = userList.map((u) => u.id);
    const routeIds = routeList.map((r) => r.id);
    limitData.value = limitList.map((l) => {
      let type = '';
      if (userIds.includes(l.id)) {
        type = '用户';
      } else if (routeIds.includes(l.id)) {
        type = '路由';
      } else {
        type = '未知';
      }

      return {
        ...l,
        type,
      };
    });
  };
  fetchData();

  const [registerTable, { reload, deleteTableDataRecord }] = useTable({
    title: '限流器列表',
    rowKey: 'id',
    dataSource: limitData,
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: [],
      autoSubmitOnEnter: true,
    },
    showIndexColumn: false,
    useSearchForm: false,
    bordered: true,
    actionColumn: {
      width: 80,
      title: '操作',
      dataIndex: 'action',
      slots: { customRender: 'action' },
    },
  });

  async function handleDelete(record: Recordable) {
    await deleteLimit({ userId: record.id, domain: domain.value });
    deleteTableDataRecord(record.id);
  }

  function goBack() {
    go('/gateway/cluster/monitor');
  }
</script>
