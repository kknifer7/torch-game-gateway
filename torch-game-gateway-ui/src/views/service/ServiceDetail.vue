<template>
  <PageWrapper :title="`服务详情`" content="这是服务详情页面。" contentBackground @back="goBack">
    <template #footer>
      <a-tabs default-active-key="detail" v-model:activeKey="currentKey">
        <a-tab-pane v-for="item in componentsList" :key="item.key" :tab="item.name" />
      </a-tabs>
    </template>
    <div class="m-4 desc-wrap">
      <div v-for="item in componentsList" :key="item.key">
        <keep-alive>
          <component :is="Component" v-if="currentKey == item.key" :service-data="serviceData" />
        </keep-alive>
      </div>
    </div>
  </PageWrapper>
</template>

<script lang="ts">
  import { defineComponent, ref, computed, defineAsyncComponent } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import { PageWrapper } from '/@/components/Page';
  import { useTabs } from '/@/hooks/web/useTabs';
  import { Tabs } from 'ant-design-vue';
  import { getServiceInfo } from '/@/api/service';

  export default defineComponent({
    name: 'ServiceDetail',
    components: {
      PageWrapper,
      ATabs: Tabs,
      ATabPane: Tabs.TabPane,
    },
    setup() {
      const route = useRoute();
      const router = useRouter();

      const componentsList = [
        {
          name: '服务信息',
          key: 'detail',
          component: defineAsyncComponent(() => import('./components/ServiceInfo/index.vue')),
        },
        {
          name: '服务测试',
          key: 'test',
          component: defineAsyncComponent(() => import('./components/ServiceTest/index.vue')),
        },
        {
          name: '调用日志',
          key: 'log',
          component: defineAsyncComponent(() => import('./components/ServiceLog/index.vue')),
        },
      ];

      const Component = computed(
        () => componentsList.find((c) => c.key === currentKey.value)?.component,
      );

      const serviceId = ref(route.params?.id);
      const serviceData = ref({});
      const currentKey = ref('detail');
      const { setTitle } = useTabs();

      setTitle('详情：服务' + serviceId.value);

      async function fetchData() {
        serviceData.value = await getServiceInfo(serviceId.value);
      }
      fetchData();

      function goBack() {
        router.back();
      }
      return { componentsList, Component, serviceId, serviceData, currentKey, goBack };
    },
  });
</script>

<style></style>
