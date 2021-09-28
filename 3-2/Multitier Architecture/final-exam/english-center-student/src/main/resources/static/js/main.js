import { Router } from "./http.js";
import registerReady from "./register.js";
import roomReady from "./room.js";
import examineeResultReady from "./examinee-result.js";
import findRoomReady from "./find-room.js";

$(document).ready(async function () {
  const path = window.location.pathname;
  console.log(path);
  // Load modules listener
  switch (path) {
    case Router.EXAMINEE_RESULT:
      await examineeResultReady();
      break;
    case Router.ROOM:
      await roomReady();
      break;
    case Router.REGISTER:
      await registerReady();
      break;
    case Router.ANALYSIS_EXAMINEE:
      break;
    case Router.ANALYSIS_ROOM:
      break;
    case Router.FIND_ROOM:
      await findRoomReady();
      break;
    default:
      break;
  }
});
