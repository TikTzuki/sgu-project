import UTILS from "./utils.js";
import Http, { Router } from "./http.js";

class Examinee {
  constructor(current_row) {
    this.current_row = current_row;
  }

  generateNewRow() {
    return (
      "<td>" +
      (this.current_row + 1) +
      "</td>" +
      "<td> <input type='text' name='name' placeholder='name' class='form-control' /></td>" +
      "<td> <input type='text' name='phone' placeholder='phone' class='form-control' /></td>" +
      "<td> <select name='gender' class='select-gender' aria-label='select-certificate'>" +
      UTILS.loadGender() +
      "</select></td >" +
      "<td> <input type='text' name='identity' placeholder='identity' class='form-control' /></td>" +
      "<td><select name='certificateId' class='select-certificate' aria-label='select-certificate'>" +
      UTILS.loadCertificates() +
      "</select></td>"
    );
  }
}

export default async function examineeReady() {
  const http = new Http();
  let data = await http.getData("/test");
  console.log("examinee test", data);

  const EXAMINEE = new Examinee(1);

  $(".select-gender").append(UTILS.loadGender());

  $(".select-certificate").append(UTILS.loadCertificates());

  // Handle dynamic examinee table
  $("#add_row").click(function () {
    $("#addr" + EXAMINEE.current_row).html(EXAMINEE.generateNewRow());
    $("#examinees_table").append(
      '<tr id="addr' + (EXAMINEE.current_row + 1) + '" class="data"></tr>'
    );
    EXAMINEE.current_row++;
  });

  $("#delete_row").click(function () {
    if (EXAMINEE.current_row > 1) {
      $("#addr" + (EXAMINEE.current_row - 1)).html("");
      EXAMINEE.current_row--;
    }
  });

  $("#create_examinee").click(function (e) {
    e.preventDefault();
    const examineesValidator = (data) => {
      for (const property in data) {
        if (!data[property]) {
          throw `field required ${property}`;
        }
      }
    };

    try {
      const data = UTILS.collectDataFromTable(
        "#examinees_table",
        examineesValidator
      );
      const params = {
        examinationId: $("#examinationId").val(),
      };
      http
        .postData(Router.EXAMINEE_RESULT, data, params)
        .then((res) => res.json())
        .then((data) => alert("create success"))
        .catch((e) => {
          alert(e);
        });
    } catch (e) {
      alert(e);
    }
  });
}
