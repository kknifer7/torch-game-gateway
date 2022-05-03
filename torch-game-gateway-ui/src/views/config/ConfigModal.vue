<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="getTitle" @ok="handleSubmit">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { formSchema } from './config.data';
  import { addConfig, updateConfig } from '/@/api/config';

  export default defineComponent({
    name: 'ConfigModal',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const isUpdate = ref(true);
      const rowId = ref('');

      const [registerForm, { setFieldsValue, resetFields, validate, updateSchema }] = useForm({
        labelWidth: 80,
        schemas: formSchema,
        showActionButtonGroup: false,
        actionColOptions: {
          span: 23,
        },
      });

      const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
        resetFields();
        setModalProps({ confirmLoading: false });
        isUpdate.value = !!data?.isUpdate;
        if (unref(isUpdate)) {
          rowId.value = data.record.id;
          setFieldsValue(data.record);

          const key = data.record.kee;
          if (['fusing_timeunit', 'limiting_time_unit'].includes(key)) {
            updateSchema([
              {
                field: 'val',
                component: 'Select',
                componentProps: {
                  options: [
                    { label: 'MILLISECONDS', value: 'MILLISECONDS' },
                    { label: 'SECONDS', value: 'SECONDS' },
                    { label: 'MINUTES', value: 'MINUTES' },
                    { label: 'HOURS', value: 'HOURS' },
                  ],
                },
                colProps: { span: 24 },
              },
            ]);
          } else if (['enable_fusing_on_limiting'].includes(key)) {
            updateSchema([
              {
                field: 'val',
                component: 'Select',
                componentProps: {
                  options: [
                    { label: 'true', value: 'true' },
                    { label: 'false', value: 'false' },
                  ],
                },
                colProps: { span: 24 },
              },
            ]);
          } else {
            updateSchema([
              {
                field: 'val',
                component: 'Input',
              },
            ]);
          }
        } else {
          updateSchema([
            {
              field: 'val',
              component: 'Input',
            },
          ]);
        }
      });

      const getTitle = computed(() => (!unref(isUpdate) ? '新增配置' : '编辑配置'));

      async function handleSubmit() {
        try {
          const values = await validate();
          setModalProps({ confirmLoading: true });

          const data = {
            ...values,
          };

          if (!unref(isUpdate)) {
            await addConfig(data);
          } else {
            data.id = rowId.value;
            await updateConfig(data);
          }

          closeModal();
          emit('success', { isUpdate: unref(isUpdate), values: { ...values, id: rowId.value } });
        } finally {
          setModalProps({ confirmLoading: false });
        }
      }

      return { registerModal, registerForm, getTitle, handleSubmit };
    },
  });
</script>
