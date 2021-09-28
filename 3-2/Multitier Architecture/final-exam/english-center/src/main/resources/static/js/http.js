export default class Http {
  headers = {
    "Content-Type": "application/json",
  };

  async getData(url = "", params = {}) {
    if (params) {
      url += "?";
      for (const props in params) {
        if (`${params[props]}`) url += `${props}=${params[props]}&`;
      }
      url = url.substring(0, url.length - 1);
      console.log(url);
    }
    const response = await fetch(url);
    return response.json();
  }

  postData(url = "", data = {}, params = {}) {
    if (params) {
      url += "?";
      for (const props in params) {
        if (`${params[props]}`) url += `${props}=${params[props]}&`;
      }
      url = url.substring(0, url.length - 1);
      console.log(url);
    }
    const response = fetch(url, {
      headers: this.headers,
      method: "POST",
      body: JSON.stringify(data),
    });
    return response;
  }
}

export const Router = {
  EXAMINATION: "/examination",
  ADD_ROOM: "/add-room",
  ADD_EXAMINEE: "/add-examinee",
  ORDER_ROOM: "/order-room",
  EXAMINEES: "/examinees",
  SET_RESULT: "/set-result",
  FORM_RESULT: "/result",
  ROOM: "/room",
};
