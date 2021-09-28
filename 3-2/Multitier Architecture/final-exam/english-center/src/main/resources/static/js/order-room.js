import Http, { Router } from "./http.js";

class OrderRoom {
  constructor() {
    this.http = new Http();
  }
  async loadRoomByExamination(examinationId) {
    const params = {
      examinationId: examinationId,
    };
    let rs = await this.http.getData(Router.ROOM + "/json", params);
    return rs.data.map(
      (room) => "<option value=" + room.id + ">" + room.name + "</option>"
    );
  }
}

export default async function orderRoomReady() {
  const http = new Http();
  let data = await http.getData("/test");
  console.log("order room test", data);

  const ORDER_ROOM = new OrderRoom();

  //   handle room select

  $(".select-examination").change(async function handleSelectExamination(e) {
    e.preventDefault();
    console.log("clicked");
    const exId = $(".select-examination").val();
    $(".select-room").html(await ORDER_ROOM.loadRoomByExamination(exId));
  });

  const exId = $(".select-examination").val();
  $(".select-room").html(await ORDER_ROOM.loadRoomByExamination(exId));

  //   handle order room
  $("#order-room").click(async function (e) {
    e.preventDefault();
    const exId = $(".select-examination").val();
    window.location.href = `${
      Router.ORDER_ROOM
    }/?orderExaminationId=${exId}&roomSize=${20}`;
  });
}
