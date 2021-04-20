import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from '../service/authenticate.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private loginservice:AuthenticateService, private router: Router) { }

  ngOnInit(): void {
    this.loginservice.logout();
    this.router.navigate(['login']);
  }

}
