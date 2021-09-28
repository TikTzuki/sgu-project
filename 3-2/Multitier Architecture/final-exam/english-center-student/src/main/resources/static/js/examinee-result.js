import { Router } from "./http.js";
import UTILS from "./utils.js";

export default async function examineeResultReady() {
  $("#btnGetResult").on("click", async function () {
    const examineeId = $("#examineeId").val();
    if (examineeId)
      window.location.href = `${Router.EXAMINEE_RESULT}?examineeId=${examineeId}`;
  });
}
