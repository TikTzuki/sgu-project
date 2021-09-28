import { Router } from "./http.js";
import examineeReady from "./examinee.js";
import orderRoomReady from "./order-room.js";

$(document).ready(async function () {
  const path = window.location.pathname;
  console.log(path);
  // Load modules listener
  switch (path) {
    case Router.ADD_EXAMINEE:
      examineeReady();
      break;
    case Router.ADD_ROOM:
      break;
    case Router.EXAMINATION:
      break;
    case Router.EXAMINEES:
      break;
    case Router.FORM_RESULT:
      break;
    case Router.ORDER_ROOM:
      await orderRoomReady();
      break;
    default:
      break;
  }
});
