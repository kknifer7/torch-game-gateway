<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="getTitle" @ok="handleSubmit">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { userFormSchema } from './user.data';
  import { addUser, updateUser } from '/@/api/system/user';

  export default defineComponent({
    name: 'UserModal',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const isUpdate = ref(true);
      const rowId = ref('');

      const [registerForm, { setFieldsValue, updateSchema, resetFields, validate }] = useForm({
        labelWidth: 100,
        schemas: userFormSchema,
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
          updateSchema([
            { field: 'username', componentProps: { disabled: true } },
            { field: 'pwd', required: false, defaultValue: '' },
          ]);
          setFieldsValue({
            ...data.record,
            services: data.record?.services.map((r) => r.id),
            pwd: null,
          });
        } else {
          updateSchema([
            { field: 'username', componentProps: { disabled: false } },
            {
              field: 'password',
              required: true,
              defaultValue: 'a123456',
              componentProps: { placeholder: '请输入' },
            },
          ]);
        }
      });

      const getTitle = computed(() => (!unref(isUpdate) ? '新增账号' : '编辑账号'));

      async function handleSubmit() {
        try {
          const values = await validate();
          setModalProps({ confirmLoading: true });

          const data = {
            ...values,
            id: rowId.value,
            services: values.services?.map((r) => ({ id: r })),
          };
          if (!unref(isUpdate)) {
            await addUser(data);
          } else {
            await updateUser(data);
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
