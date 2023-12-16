import {Component, Input} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {
  @Input() model;

  constructor(public activeModal: NgbActiveModal) {
  }

  // onSelect(value: any){
  //   this.selectedValue= value
  //   console.log(this.selectedValue);
  //   this.eventEmit.emit(this.selectedValue);
  // }

  toString(value: any) {
    return JSON.stringify(value);
  }
}
