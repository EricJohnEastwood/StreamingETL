import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from '../service/authenticate.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username:string = "";
  password:string = "";
  isvalidLogin: boolean = false;

  constructor(private loginservice:AuthenticateService, private router: Router) { }

  ngOnInit(): void {
  }

  checkLogin(){
    if(this.loginservice.authenticate(this.username, this.password))
    {
      this.router.navigate(['']);
      this.isvalidLogin = true;
    }
    else
    {
      this.isvalidLogin = false;
    }
  }

}
