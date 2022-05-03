<template>
  <BasicModal v-bind="$attrs" @register="registerModal" title="限流器" @ok="handleSubmit">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts" setup>
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { limitFormSchema } from './user.data';
  import { userLimit } from '/@/api/system/user';

  const rowId = ref('');

  const [registerForm, { setFieldsValue, updateSchema, resetFields, validate }] = useForm({
    labelWidth: 100,
    schemas: limitFormSchema,
    showActionButtonGroup: false,
    actionColOptions: {
      span: 23,
    },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    resetFields();
    setModalProps({ confirmLoading: false });

    rowId.value = data.record.id;
    setFieldsValue({
      ...data.record,
    });
  });

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      const data = {
        ...values,
        userId: rowId.value,
      };
      userLimit(data);

      closeModal();
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }
</script>
