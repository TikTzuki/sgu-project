import { Router } from "./http.js";

export default async function findRoomReady() {
  $("#btnFindRoom").on("click", async function () {
    const params = {
      name: $('input[name="name"]').val(),
      phone: $("input[name=phone]").val(),
    };

    let url = Router.FIND_ROOM;

    if (params) {
      url += "?";
      for (const props in params) {
        if (`${params[props]}`) url += `${props}=${params[props]}&`;
      }
      url = url.substring(0, url.length - 1);
      console.log(url);
    }
    window.location.href = url;
  });
}
