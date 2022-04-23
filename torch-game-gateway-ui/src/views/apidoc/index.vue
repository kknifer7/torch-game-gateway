<template>
  <div class="m-4">
    <a-alert
      message="使用说明"
      description="登录后，将token的值，设置到协议头的x-token上后再请求其他接口，如x-token: eyJhbGciOiJIUzI1NiIsInR..."
      type="error"
    />
    <a-collapse v-model:activeKey="activeKey">
      <a-collapse-panel :header="item.title" v-for="item of data" :key="item.key">
        <div class="mb-2 text-gray-500 text-base font-semibold font-sans">请求链接</div>
        <div class="mb-2">
          <a-tag
            :color="item.method == 'POST' ? 'green' : 'blue'"
            style="font-size: 20px; line-height: 24px"
          >
            {{ item.method }}
          </a-tag>
          <a-tag color="" style="font-size: 20px; line-height: 24px">
            {{ item.link }}
          </a-tag>
          <CopyOutlined @click="handleCopy(item.link)" />
        </div>

        <div v-if="item.params" class="mb-2 text-gray-500 text-base font-semibold font-sans"
          >请求参数</div
        >

        <a-table
          :columns="columns"
          v-if="item.params"
          :data-source="item.params"
          size="small"
          :pagination="false"
        >
          <template #param="{ text: param }">
            <span>
              <a-tag color="geekblue">
                {{ param }}
              </a-tag>
            </span>
          </template>
        </a-table>

        <div class="mt-2 text-gray-500 text-base font-semibold font-sans">响应文本</div>
        <div class="break-words">{{ item.response }}</div>
      </a-collapse-panel>
    </a-collapse>
  </div>
</template>
<script lang="ts">
  import { defineComponent, ref, watch, unref } from 'vue';
  import { Alert, Collapse, Table, Tag } from 'ant-design-vue';
  import { CopyOutlined } from '@ant-design/icons-vue';

  import { useCopyToClipboard } from '/@/hooks/web/useCopyToClipboard';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { columns, data } from './data';

  export default defineComponent({
    components: {
      [Collapse.name]: Collapse,
      [Collapse.Panel.name]: Collapse.Panel,
      [Table.name]: Table,
      [Tag.name]: Tag,
      [Alert.name]: Alert,
      CopyOutlined: CopyOutlined,
    },
    setup() {
      const { clipboardRef, copiedRef } = useCopyToClipboard();
      const { createMessage } = useMessage();

      const activeKey = ref([]);

      watch(activeKey, (val) => {
        console.log(val);
      });

      function handleCopy(value) {
        clipboardRef.value = value;
        if (unref(copiedRef)) {
          createMessage.success('复制成功！');
        }
      }

      return {
        activeKey,
        data,
        columns,
        handleCopy,
      };
    },
  });
</script>
