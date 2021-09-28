import Http, { Router } from "./http.js";

class Utils {
  constructor() {
    this.http = new Http();
  }
  certificateName = ["A2", "B1"];

  genders = [
    { code: "F", name: "Female" },
    { code: "M", name: "Male" },
  ];
  // load certificate
  loadCertificates = () =>
    this.certificateName.map(
      (certName) => "<option value=" + certName + ">" + certName + "</option>"
    );

  // load gender
  loadGender = () =>
    this.genders.map(
      (gender) =>
        "<option value=" + gender.code + ">" + gender.name + "</option>"
    );

  async loadExamination() {
    const data = await this.http.getData(Router.REGISTER + "/json");
    console.log(data);
    return data.data.map(
      (examination) =>
        "<option value=" + examination.id + ">" + examination.name + "</option>"
    );
  }

  // Collect data from dynamic table
  collectDataFromTable(tableId, validator) {
    let items = [];
    $(tableId + " tr.data").each(function (index, element) {
      let object = {};
      $(this)
        .find("input[type=text], select")
        .map(function () {
          object[$(this).attr("name")] = $(this).val();
        });

      if (typeof validator == "function") validator(object);

      items.push(object);
    });
    items.pop();
    return items;
  }
}

const UTILS = new Utils();

export default UTILS;
