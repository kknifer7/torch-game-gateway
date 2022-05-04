<template>
  <div class="sys-server-stat-container">
    <Card v-for="item in clusterList" class="stat-card" :title="item.name" :key="item.id">
      <template #extra><Badge status="processing" :color="getStatusColor(item.status)" /></template>
      <Descriptions :column="1" :label-style="{ width: '50%' }" :content-style="{ width: '50%' }">
        <Descriptions.Item label="主机端口">{{ item.host + ':' + item.port }}</Descriptions.Item>
        <Descriptions.Item label="备用集群">
          <Tag color="processing" size="small">{{
            item.backup ? '是' : '否'
          }}</Tag></Descriptions.Item
        >
        <Descriptions.Item label="CPU负载">
          <Progress :percent="Math.floor(item.cpuSys * 100)" :stroke-color="customProgressColor" />
        </Descriptions.Item>
        <Descriptions.Item label="核心数量">{{ item.cpuCores }}</Descriptions.Item>
      </Descriptions>
      <div class="disk-info">
        <Descriptions class="disk-info--item" :column="1">
          <Descriptions.Item label="总内存">{{
            formatSizeUnits(item.totalMemory)
          }}</Descriptions.Item>
          <Descriptions.Item label="已用内存">{{
            formatSizeUnits(item.totalMemory - item.freeMemory)
          }}</Descriptions.Item>
          <Descriptions.Item label="可用内存">{{
            formatSizeUnits(item.freeMemory)
          }}</Descriptions.Item>
        </Descriptions>
        <div class="disk-info--item">
          <Progress
            type="dashboard"
            :percent="Math.floor((item.freeMemory / item.totalMemory) * 100)"
            :width="100"
            :stroke-color="customProgressColor"
          />
        </div>
      </div>
      <a-button type="success" @click="handleGetLimitList(item.host + ':' + item.port)">
        限流器列表
      </a-button>
    </Card>
  </div>
</template>

<script lang="ts" setup name="ServeMonit">
  import { ref, onMounted, onBeforeUnmount } from 'vue';
  import { Card, Descriptions, Tag, Badge } from 'ant-design-vue';
  import Progress from '/@/components/Progress/index.vue';
  import { formatSizeUnits } from '/@/utils';
  import { getServeStat } from '/@/api/system/serve';
  import { useGo } from '/@/hooks/web/usePage';

  const go = useGo();

  let intervalId: NodeJS.Timer;

  const clusterList = ref<any>([]);

  const refresh = async () => {
    clusterList.value = await getServeStat();
  };
  refresh();

  const customProgressColor = (percentage) => {
    if (percentage < 30) {
      return '#5cb87a';
    } else if (percentage < 70) {
      return '#e6a23c';
    } else {
      return '#f53f3f';
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case false:
        return '#d9d9d9';
      case true:
        return '#52c41a';
    }
  };

  onMounted(() => {
    intervalId = setInterval(refresh, 10000);
  });
  onBeforeUnmount(() => {
    clearInterval(intervalId);
  });

  const handleGetLimitList = (domain) => {
    go('/gateway/limit/' + domain);
  };
</script>

<style lang="less" scoped>
  .sys-server-stat-container {
    padding: 20px;
    column-count: 2;
    column-gap: 10px;

    .stat-card {
      margin-bottom: 10px;
      break-inside: avoid;
      transform: translateZ(0);

      .disk-info {
        width: 100%;
        display: flex;
        flex-direction: row;

        &--item {
          width: 50%;
        }
      }
    }
  }
</style>
