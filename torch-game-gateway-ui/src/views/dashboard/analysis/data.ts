import { getAnalysis } from '/@/api/system/serve/index';
import { onBeforeMount, ref } from 'vue';

export interface GrowCardItem {
  icon: string;
  title: string;
  value: number;
  color: string;
  path?: string;
}

export function useDashboard() {
  const growCardList = ref<GrowCardItem[]>([]);

  onBeforeMount(() => {
    getAnalysis().then((data) => {
      const { gatewayCount, routeCount, serviceCount, callLogCount } = data;
      growCardList.value = [
        {
          title: '网关数',
          icon: 'cluster|svg',
          value: gatewayCount,
          color: 'blue',
          path: '/gateway/cluster/monitor',
        },
        {
          title: '服务数',
          icon: 'service|svg',
          value: serviceCount,
          color: 'blue',
          path: '/gateway/serviceList',
        },
        {
          title: '路由数',
          icon: 'route|svg',
          value: routeCount,
          color: 'blue',
          path: '/gateway/routeList',
        },
        {
          title: '调用数',
          icon: 'call-log|svg',
          value: callLogCount,
          color: 'blue',
          path: '/gateway/call-log',
        },
      ];
    });
  });

  return { growCardList };
}

export const growCardList: GrowCardItem[] = [
  {
    title: '网关数',
    icon: 'total-sales|svg',
    value: 20000,
    color: 'blue',
  },
  {
    title: '服务数',
    icon: 'download-count|svg',
    value: 8000,
    color: 'orange',
  },
  {
    title: '路由数',
    icon: 'transaction|svg',
    value: 5000,
    color: 'purple',
  },
  {
    title: '访问数',
    icon: 'visit-count|svg',
    value: 2000,
    color: 'green',
  },
];
