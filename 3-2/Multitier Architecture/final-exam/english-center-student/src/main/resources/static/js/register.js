import UTILS from "./utils.js";

export default async function registerReady() {
  $(".select-gender").html(UTILS.loadGender());

  $(".select-certificate").html(UTILS.loadCertificates());
}
