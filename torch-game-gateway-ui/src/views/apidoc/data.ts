const domain = `https://link.xiaoxin.vip`;

export const columns = [
  {
    title: '参数',
    dataIndex: 'param',
    key: 'param',
    width: 200,
    slots: { customRender: 'param' },
  },
  {
    title: '类型',
    dataIndex: 'type',
    width: 200,
    key: 'type',
  },
  {
    title: '描述',
    key: 'desc',
    dataIndex: 'desc',
  },
];

export const data = [
  {
    key: '1',
    title: '登录',
    method: 'POST',
    link: domain + '/v1/user/login',
    params: [
      {
        param: 'username',
        type: 'string',
        desc: '用户账号',
      },
      {
        param: 'password',
        type: 'string',
        desc: '用户密码',
      },
    ],
    response: `{"code":0,"msg":"登录成功","data":{"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsInJvbGVzIjpbInN1cGVyX2FkbWluIl0sImlhdCI6MTYzMTIzMjk3MywiZXhwIjoxNjMxMzE5MzczfQ.AOWsBbSZVGPH1Toa7A97JjZ4xTPIbuq5DGfkON76E"}}`,
  },
  {
    key: '2',
    title: '获取商品列表',
    method: 'GET',
    link: domain + '/v1/goods',
    response: `{"code":0,"msg":"获取商品列表成功","data":{"total":0,"items":[]}}`,
  },
  {
    key: '3',
    title: '新增链接',
    method: 'POST',
    link: domain + '/v1/link',
    params: [
      {
        param: 'softId',
        type: 'string',
        desc: '商品ID',
      },
      {
        param: 'count',
        type: 'number',
        desc: '生成数据 默认为1',
      },
    ],
    response: `{"code":0,"msg":"生成链接成功","data":{"linkList":[{"no":"a5614630-11d3-11ec-b92d-dd3c34683112","status":1,"link":domain+"/charge.html?no=a5614630-11d3-11ec-b92d-dd3c34683112"}],"cost":1}}`,
  },
  {
    key: '4',
    title: '查看链接状态',
    method: 'GET',
    link: domain + '/v1/link/get?no=a5614630-11d3-11ec-b92d-dd3c34683112',
    params: [
      {
        param: 'no',
        type: 'string',
        desc: '链接ID',
      },
    ],
    response: `{"code":200,"msg":"获取成功","data":{"id":375,"no":"a5614630-11d3-11ec-b92d-dd3c34683149","price":0.25,"phone":null,"code":null,"status":1,"isSend":0,"remark":null,"status":true,"createdTime":"1629212734009"}}`,
  },
  {
    key: '5',
    title: '直冲',
    method: 'GET',
    link: domain + '/v1/link/rechargeZC?no=bb8be4e0-11d0-11ec-bb7a-237821eaac12&phone=152xxxx5678',
    params: [
      {
        param: 'no',
        type: 'string',
        desc: '链接编号 例:bb8be4e0-11d0-11ec-bb7a-237821eaac12',
      },
      {
        param: 'phone',
        type: 'string',
        desc: '账号/手机号',
      },
    ],
    response: `{"code":200,"msg":"正在充值中,请稍后查看结果","data":{"id":369,"no":"89792db0-11ac-11ec-bb7a-237821eaac12"}}`,
  },
  {
    key: '6',
    title: '接码-发送验证码',
    method: 'GET',
    link: domain + '/v1/link/sendCode?no=bb8be4e0-11d0-11ec-bb7a-237821eaac12&phone=152xxxx5678',
    params: [
      {
        param: 'no',
        type: 'string',
        desc: '链接编号 例:bb8be4e0-11d0-11ec-bb7a-237821eaac12',
      },
      {
        param: 'phone',
        type: 'string',
        desc: '账号/手机号',
      },
    ],
    response: `{"code":200,"msg":"发送验证码成功","data":{"id":369,"no":"89792db0-11ac-11ec-bb7a-237821eaac12"}}`,
  },
  {
    key: '7',
    title: '接码-填写验证码(充值)',
    method: 'GET',
    link:
      domain +
      '/v1/link/rechargeJM?no=bb8be4e0-11d0-11ec-bb7a-237821eaac12&phone=152xxxx5678&code=1234',
    params: [
      {
        param: 'no',
        type: 'string',
        desc: '链接编号 例:bb8be4e0-11d0-11ec-bb7a-237821eaac12',
      },
      {
        param: 'phone',
        type: 'string',
        desc: '账号/手机号',
      },
      {
        param: 'code',
        type: 'string',
        desc: '验证码',
      },
    ],
    response: `{"code":200,"msg":"正在充值中,请稍后查看结果","data":{"id":369,"no":"89792db0-11ac-11ec-bb7a-237821eaac12"}}`,
  },
];
