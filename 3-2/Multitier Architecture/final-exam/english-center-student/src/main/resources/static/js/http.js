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
  REGISTER: "/register",
  ROOM: "/room",
  FIND_ROOM: "/find-room",
  EXAMINEE_RESULT: "/examinee-result",
  ANALYSIS_ROOM: "/analysis-room",
  ANALYSIS_EXAMINEE: "/analysis-examinee",
  FORM_RESULT: "/result",
};
