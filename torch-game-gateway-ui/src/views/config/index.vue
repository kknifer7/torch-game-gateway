<template>
  <PageWrapper title="配置选项">
    <div class="flex flex-wrap gap-4">
      <CollapseContainer title="限流" :canExpan="true" class="text-center mb-6 item">
        <BasicForm @register="registerRateLimit" />
      </CollapseContainer>
    </div>
  </PageWrapper>
</template>
<script lang="ts" setup>
  import { CollapseContainer } from '/@/components/Container/index';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { PageWrapper } from '/@/components/Page';
  import { rateLimitSchemas } from './data';

  const [registerRateLimit, { validate, setProps, setFieldsValue }] = useForm({
    schemas: rateLimitSchemas,
    labelWidth: 60,
    submitButtonOptions: {
      text: '保存',
    },
    actionColOptions: {
      span: 24,
    },
    showResetButton: false,
    submitFunc: handleSubmit,
  });

  // 从后端获取数据,然后显示到页面上
  async function fetchData() {
    setFieldsValue({});
  }
  fetchData();

  async function handleSubmit() {
    try {
      let values = await validate();
      setProps({
        submitButtonOptions: {
          loading: true,
        },
      });

      console.log(values);
      // 设置的api
      // rechargeCardApi(values)
      //   .then(() => {})
      //   .finally(() => {});

      setProps({
        submitButtonOptions: {
          loading: false,
        },
      });
    } catch (error) {}
  }
</script>
<style scoped>
  .item {
    width: 30%;
  }
</style>
