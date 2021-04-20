import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../service/authenticate.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public loginService:AuthenticateService) { }

  ngOnInit(): void {
  }

}
