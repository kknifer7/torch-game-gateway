<template>
  <PageWrapper title="关于">
    <template #headerContent>
      <div class="flex justify-between items-center">
        <span class="flex-1">
          本项目是“厦门市火炬高新区挑战赛 - 微服务网关开发”演示项目，均为本参赛队伍自主设计实现
        </span>
      </div>
    </template>

    <!-- <Description @register="infoRegister" class="enter-y" /> -->

    <CollapseContainer class="enter-y" title="演示视频 ">
      <video controls="true">
        <source id="mp4" :src="introVideo" type="video/mp4" />
      </video>
    </CollapseContainer>

    <CollapseContainer class="my-4 enter-y" title="模块介绍">
      <div class="text-base">
        <MarkdownViewer :value="introText" />
      </div>
    </CollapseContainer>
  </PageWrapper>
</template>
<script lang="ts" setup>
  import { h } from 'vue';
  import { Tag } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { CollapseContainer } from '/@/components/Container/index';
  import { Description, DescItem, useDescription } from '/@/components/Description/index';
  import { MarkdownViewer } from '/@/components/Markdown';

  import introText from './模块介绍.md?raw';
  const commonTagRender = (color: string) => (curVal) => h(Tag, { color }, () => curVal);

  const introVideo = 'https://img.kuizuo.cn/演示视频.mp4';
  const infoSchema: DescItem[] = [
    {
      label: 'Java版本',
      field: 'version',
      render: commonTagRender('blue'),
    },
    {
      label: '技术栈',
      field: 'skill',
    },
  ];

  const infoData = {
    version: 11,
    skill: '',
  };

  const [infoRegister] = useDescription({
    title: '项目信息',
    data: infoData,
    schema: infoSchema,
    column: 1,
    collapseOptions: {
      canExpand: true,
    },
  });
</script>

<style scoped>
  :deep(h2) {
    font-size: x-large;
    font-weight: bold;
  }

  :deep(ul) {
    list-style: auto;
    padding-left: 40px;
  }
</style>
