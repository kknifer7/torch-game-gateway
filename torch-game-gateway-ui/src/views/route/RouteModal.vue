<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="getTitle" @ok="handleSubmit">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { formSchema } from './route.data';
  import { addRoute, updateRoute } from '/@/api/route';
  import { getServiceInfo } from '/@/api/service';

  export default defineComponent({
    name: 'RouteModal',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const isUpdate = ref(true);
      const rowId = ref('');

      const [registerForm, { setFieldsValue, resetFields, validate }] = useForm({
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

          if (data.record.serviceName) {
            const serviceData = await getServiceInfo(data.record.serviceName);
            setFieldsValue({
              ...data.record,
              service: serviceData.id,
            });
          } else {
            setFieldsValue({
              ...data.record,
            });
          }
        }
      });

      const getTitle = computed(() => (!unref(isUpdate) ? '新增路由' : '编辑路由'));

      async function handleSubmit() {
        try {
          const values = await validate();
          setModalProps({ confirmLoading: true });

          const data = {
            ...values,
            service: { id: values.service },
          };

          if (!unref(isUpdate)) {
            await addRoute(data);
          } else {
            data.id = rowId.value;
            await updateRoute(data);
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
