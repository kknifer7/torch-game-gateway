<template>
  <PageWrapper :title="`服务详情`" content="这是服务详情页面。" contentBackground @back="goBack">
    <template #footer>
      <Tabs default-active-key="detail" v-model:activeKey="currentKey">
        <Tabs.TabPane v-for="item in componentsList" :key="item.key" :tab="item.name" />
      </Tabs>
    </template>
    <div class="m-4 desc-wrap">
      <div v-for="item in componentsList" :key="item.key">
        <keep-alive>
          <component
            :is="Component"
            v-if="currentKey == item.key"
            :service-name="serviceName"
            :service-data="serviceData"
          />
        </keep-alive>
      </div>
    </div>
  </PageWrapper>
</template>

<script lang="ts" setup name="ServiceDetail">
  import { ref, computed, defineAsyncComponent } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import { PageWrapper } from '/@/components/Page';
  import { useTabs } from '/@/hooks/web/useTabs';
  import { Tabs } from 'ant-design-vue';
  import { getServiceInfo } from '/@/api/service';

  const route = useRoute();
  const router = useRouter();

  const componentsList = [
    {
      name: '路由列表',
      key: 'detail',
      component: defineAsyncComponent(() => import('./components/RouteInfo/index.vue')),
    },
    {
      name: '服务测试',
      key: 'test',
      component: defineAsyncComponent(() => import('./components/RouteTest/index.vue')),
    },
    // {
    //   name: '调用日志',
    //   key: 'log',
    //   component: defineAsyncComponent(() => import('./components/RouteLog/index.vue')),
    // },
  ];

  const Component = computed(
    () => componentsList.find((c) => c.key === currentKey.value)?.component,
  );

  const serviceName = ref(route.params?.name);
  const serviceData = ref({});
  const currentKey = ref('detail');
  const { setTitle } = useTabs();

  setTitle('详情：服务' + serviceName.value);

  async function fetchData() {
    serviceData.value = await getServiceInfo(serviceName.value);
  }
  fetchData();

  function goBack() {
    router.back();
  }
</script>

<style></style>
