import {Component, OnInit} from '@angular/core';

interface navItem {
  name: string;
  link: any;
}

@Component({
  selector: 'account-nav',
  templateUrl: './account-nav.component.html',
  styleUrls: ['./account-nav.component.scss']
})
export class AccountNavComponent implements OnInit {
  navList: navItem[] = [
    {name: 'Address list', link: 'address'},
    {name: 'Profile', link: 'profile'},
    {name: 'Orders', link: 'orders'}
  ];
  selectedLink = window.location.pathname.substring(window.location.pathname.lastIndexOf('/') + 1, window.location.pathname.length);

  constructor() {
  }

  ngOnInit(): void {

  }

  onClick(link) {
    this.selectedLink = link;
  }
}
