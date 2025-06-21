// import http from './Request'

export default {
  sendMessage(chatData) {
    // 【核心修正】: 对应后端的修改，请求路径变为 /api1/chat
    const requestUrl = '/api1/chat';

    console.log('发送聊天消息到代理地址:', requestUrl);

    return fetch(requestUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(chatData)
    })
    .then(response => {
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text) });
      }
      return response;
    });
  },

  handleStreamResponse(response, onChunk, onComplete, onError) {
    const reader = response.body.getReader();
    const decoder = new TextDecoder();
    let buffer = '';

    function processStream() {
      reader.read().then(({ done, value }) => {
        if (done) {
          if (buffer.trim()) onChunk(buffer);
          onComplete();
          return;
        }
        buffer += decoder.decode(value, { stream: true });
        const chunks = buffer.split('\n');
        buffer = chunks.pop() || '';
        chunks.forEach(chunk => {
          if (chunk.trim()) onChunk(chunk);
        });
        processStream();
      }).catch(onError);
    }
    processStream();
  }
}
