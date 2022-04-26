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
          <component :is="Component" v-if="currentKey == item.key" :route-data="routeData" />
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
  import { getRouteInfo } from '/@/api/route';

  export default defineComponent({
    name: 'RouteDetail',
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
          component: defineAsyncComponent(() => import('./components/RouteInfo/index.vue')),
        },
        {
          name: '服务测试',
          key: 'test',
          component: defineAsyncComponent(() => import('./components/RouteTest/index.vue')),
        },
        {
          name: '调用日志',
          key: 'log',
          component: defineAsyncComponent(() => import('./components/RouteLog/index.vue')),
        },
      ];

      const Component = computed(
        () => componentsList.find((c) => c.key === currentKey.value)?.component,
      );

      const routeId = ref(route.params?.id);
      const routeData = ref({});
      const currentKey = ref('detail');
      const { setTitle } = useTabs();

      setTitle('详情：服务' + routeId.value);

      async function fetchData() {
        routeData.value = await getRouteInfo(routeId.value);
      }
      fetchData();

      function goBack() {
        router.back();
      }
      return { componentsList, Component, routeId, routeData, currentKey, goBack };
    },
  });
</script>

<style></style>
