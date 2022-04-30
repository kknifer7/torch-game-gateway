<template>
  <BasicForm @register="register">
    <template #formFooter>
      <a-space :size="10" direction="vertical" class="w-full">
        <a-form-item label="响应结果">
          <a-textarea v-model:value="response" :rows="4" class="w-full" />
        </a-form-item>
      </a-space>
    </template>
  </BasicForm>
</template>
<script lang="ts" setup name="RouteTest">
  import { BasicForm, useForm } from '/@/components/Form';
  import { FormItem } from 'ant-design-vue';
  import { ref } from 'vue';
  import { schemas } from './data';
  import { useMessage } from '/@/hooks/web/useMessage';

  const AFormItem = FormItem;

  const { createMessage } = useMessage();
  const [register, { validate, setProps }] = useForm({
    schemas: schemas,
    labelWidth: 100,
    actionColOptions: {
      offset: 2,
      span: 12,
    },
    submitButtonOptions: {
      text: '提交',
    },
    submitFunc: customSubmitFunc,
  });

  const response = ref('');

  async function customSubmitFunc() {
    try {
      await validate();
      setProps({
        submitButtonOptions: {
          loading: true,
        },
      });
      setTimeout(() => {
        setProps({
          submitButtonOptions: {
            loading: false,
          },
        });
        createMessage.success('提交成功！');
      }, 2000);
    } catch (error) {}
  }
</script>
<style>
  .ant-form-item-label {
    width: 100px;
  }
</style>
